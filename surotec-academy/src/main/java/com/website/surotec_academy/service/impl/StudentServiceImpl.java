package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.request.request.StudentDto;
import com.website.surotec_academy.domain.mapper.StudentMapper;
import com.website.surotec_academy.entity.StudentEntity;
import com.website.surotec_academy.repository.StudentRepository;
import com.website.surotec_academy.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentEntity> studentEntities = studentRepository.findAll();
        List<StudentDto> studentDtos = StudentMapper.toDtoList(studentEntities);
        return studentDtos;
    }

    @Override
    public List<StudentDto> getStudentsByStatus(Boolean status) {
        return List.of();
    }

    @Override
    public Void createStudent(StudentDto studentDto) {
        return null;
    }

    @Override
    public Void updateStudent(StudentDto studentDto) {
        return null;
    }

    @Override
    public Void deleteStudent(int id) {
        return null;
    }
}
