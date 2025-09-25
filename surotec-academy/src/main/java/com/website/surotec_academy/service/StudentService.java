package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.student.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();
    List<StudentDto> getStudentsByStatus(String status);
    StudentDto getStudentById(Long id);
    StudentDto createStudent(StudentDto studentDto);
    StudentDto updateStudent(Long Id, StudentDto studentDto);
    void deleteStudent(Long id);

}
