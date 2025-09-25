package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.request.request.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();
    List<StudentDto> getStudentsByStatus(Boolean status);
    Void createStudent(StudentDto studentDto);
    Void updateStudent(StudentDto studentDto);
    Void deleteStudent(int id);

}
