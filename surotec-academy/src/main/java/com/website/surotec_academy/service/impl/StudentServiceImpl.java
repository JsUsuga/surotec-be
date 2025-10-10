package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.student.StudentDto;
import com.website.surotec_academy.domain.mapper.StudentMapper;
import com.website.surotec_academy.domain.mapper.UserMapper;
import com.website.surotec_academy.entity.StudentEntity;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.StudentStatus;
import com.website.surotec_academy.repository.StudentRepository;
import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.StudentService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        log.info("StudentServiceImpl initialized with StudentRepository and UserRepository");
    }

    @Override
    public List<StudentDto> getAllStudents() {
        log.info("Fetching all students");
        try {
            List<StudentDto> students = StudentMapper.toDtoList(studentRepository.findAll());
            log.info("Found {} students", students.size());
            return students;
        } catch (Exception e) {
            log.error("Error retrieving students", e);
            throw new RuntimeException("Error retrieving students: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StudentDto> getStudentsByStatus(String status) {
        log.info("Fetching students with status {}", status);
        try {
            List<StudentEntity> entities = studentRepository.findByStatus(StudentStatus.valueOf(status));
            log.info("Found {} students with status {}", entities.size(), status);
            return StudentMapper.toDtoList(entities);
        } catch (Exception e) {
            log.error("Error getting students by status {}", status, e);
            throw new RuntimeException("Error getting students by status: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentDto getStudentById(Long id) {
        log.info("Fetching student with id {}", id);
        try {
            if (id == null || id <= 0) {
                log.warn("Invalid student ID: {}", id);
                throw new IllegalArgumentException("Invalid student ID");
            }
            StudentEntity student = studentRepository.findById(id).orElse(null);
            if (student == null) {
                log.warn("Student not found with id {}", id);
            } else {
                log.info("Student found: {} {}", student.getUser().getFirstName(), student.getUser().getLastName());
            }
            return StudentMapper.toDto(student);
        } catch (Exception e) {
            log.error("Error retrieving student with id {}", id, e);
            throw new RuntimeException("Error retrieving student: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        log.info("Creating student for user: {} {}", studentDto.userDto().firstName(), studentDto.userDto().lastName());
        try {
            StudentEntity entity = StudentMapper.toEntity(studentDto);

            Long userId = studentDto.userDto().idUser();
            UserEntity user;
            if (userId != null && userRepository.existsById(userId)) {
                user = userRepository.findById(userId).orElseThrow();
                log.info("Existing user linked with id {}", userId);
            } else {
                user = UserMapper.toEntity(studentDto.userDto());
                user.setId(null);
                LocalDateTime now = LocalDateTime.now();
                user.setDateCreate(now);
                user.setDateUpdate(now);
                user = userRepository.save(user);
                log.info("New user created with id {}", user.getId());
            }
            entity.setUser(user);

            LocalDateTime now = LocalDateTime.now();
            entity.setId(null);
            entity.setDateCreate(now);
            entity.setDateUpdate(now);

            StudentEntity saved = studentRepository.save(entity);
            log.info("Student created with id {}", saved.getId());
            return StudentMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error creating student: {} {}", studentDto.userDto().firstName(), studentDto.userDto().lastName(), e);
            throw new RuntimeException("Error creating student: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        log.info("Updating student with id {}: {} {}", id, studentDto.userDto().firstName(), studentDto.userDto().lastName());
        try {
            StudentEntity existingStudent = studentRepository.findById(id).orElse(null);
            if (existingStudent == null) {
                log.warn("Student not found with id {}", id);
                throw new RuntimeException("Student not found with id: " + id);
            }
            StudentMapper.updateEntityFromDto(existingStudent, studentDto);
            StudentEntity updatedStudent = studentRepository.save(existingStudent);
            log.info("Student updated with id {}", updatedStudent.getId());
            return StudentMapper.toDto(updatedStudent);
        } catch (Exception e) {
            log.error("Error updating student with id {}: {} {}", id, studentDto.userDto().firstName(), studentDto.userDto().lastName(), e);
            throw new RuntimeException("Error updating student: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteStudent(Long id) {
        log.info("Deleting (inactivating) student with id {}", id);
        try {
            StudentEntity student = studentRepository.findById(id).orElse(null);
            if (student == null) {
                log.warn("Student not found with id {}", id);
                throw new RuntimeException("Student not found with id: " + id);
            }
            student.setStatus(StudentStatus.INACTIVE);
            student.setDateUpdate(LocalDateTime.now());
            studentRepository.save(student);
            log.info("Student set to INACTIVE with id {}", id);
        } catch (Exception e) {
            log.error("Error deleting student with id {}", id, e);
            throw new RuntimeException("Error deleting student: " + e.getMessage(), e);
        }
    }
}
