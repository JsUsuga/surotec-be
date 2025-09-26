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

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        try {
            return StudentMapper.toDtoList(studentRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving students: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StudentDto> getStudentsByStatus(String status) {
        try {
            List<StudentEntity> entities = studentRepository.findByStatus(StudentStatus.valueOf(status));
            return StudentMapper.toDtoList(entities);
        } catch (Exception e) {
            throw new RuntimeException("Error getting students by status: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentDto getStudentById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid student ID");
            }
            return StudentMapper.toDto(studentRepository.findById(id).orElse(null));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving student: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        try {
            StudentEntity entity = StudentMapper.toEntity(studentDto);

            Long userId = studentDto.userDto().idUser();
            UserEntity user;
            if (userId != null && userRepository.existsById(userId)) {
                user = userRepository.findById(userId).orElseThrow();
            } else {
                user = UserMapper.toEntity(studentDto.userDto());
                user.setId(null);
                user.setDateCreate(LocalDateTime.now());
                user.setDateUpdate(LocalDateTime.now());
                user = userRepository.save(user);
            }
            entity.setUser(user);

            LocalDateTime now = LocalDateTime.now();
            entity.setId(null);
            entity.setDateCreate(now);
            entity.setDateUpdate(now);

            StudentEntity saved = studentRepository.save(entity);
            return StudentMapper.toDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error creating student: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        try {
            StudentEntity existingStudent = studentRepository.findById(id).orElse(null);
            if (existingStudent == null) {
                throw new RuntimeException("Student not found with id: " + id);
            }
            StudentMapper.updateEntityFromDto(existingStudent, studentDto);
            StudentEntity updatedStudent = studentRepository.save(existingStudent);
            return StudentMapper.toDto(updatedStudent);
        } catch (Exception e) {
            throw new RuntimeException("Error updating student: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteStudent(Long id) {
        try {
            StudentEntity student = studentRepository.findById(id).orElse(null);
            if (student == null) {
                throw new RuntimeException("Student not found with id: " + id);
            }
            student.setStatus(StudentStatus.INACTIVE);
            student.setDateUpdate(LocalDateTime.now());
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting student: " + e.getMessage(), e);
        }
    }

}
