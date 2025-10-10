package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.student.StudentDto;
import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.entity.StudentEntity;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.StudentStatus;
import com.website.surotec_academy.enums.UserStatus;
import com.website.surotec_academy.repository.StudentRepository;
import com.website.surotec_academy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    private StudentEntity studentEntity;
    private StudentDto studentDto;
    private UserEntity userEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setUsername("johndoe");
        userEntity.setPassword("password123");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setStatus(UserStatus.ACTIVE);

        userDto = UserDto.builder()
                .idUser(1L)
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .password("password123")
                .email("john.doe@example.com")
                .status(UserStatus.ACTIVE)
                .build();

        studentEntity = new StudentEntity();
        studentEntity.setId(1L);
        studentEntity.setStatus(StudentStatus.ACTIVE);
        studentEntity.setUser(userEntity);

        studentDto = StudentDto.builder()
                .idStudent(1L)
                .status(StudentStatus.ACTIVE)
                .userDto(userDto)
                .build();
    }

    @Test
    void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(studentEntity));

        List<StudentDto> result = studentServiceImpl.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentsByStatus() {
        when(studentRepository.findByStatus(StudentStatus.ACTIVE)).thenReturn(List.of(studentEntity));

        List<StudentDto> result = studentServiceImpl.getStudentsByStatus("ACTIVE");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findByStatus(StudentStatus.ACTIVE);
    }

    @Test
    void getStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));

        StudentDto result = studentServiceImpl.getStudentById(1L);

        assertNotNull(result);
        assertEquals(studentEntity.getUser().getUsername(), result.userDto().username());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void createStudent() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

        StudentDto result = studentServiceImpl.createStudent(studentDto);

        assertNotNull(result);
        assertEquals(studentEntity.getUser().getUsername(), result.userDto().username());
        verify(studentRepository, times(1)).save(any(StudentEntity.class));
    }

    @Test
    void updateStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

        StudentDto result = studentServiceImpl.updateStudent(1L, studentDto);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(any(StudentEntity.class));
    }

    @Test
    void deleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));

        studentServiceImpl.deleteStudent(1L);

        assertEquals(StudentStatus.INACTIVE, studentEntity.getStatus());
        verify(studentRepository, times(1)).save(studentEntity);
    }
}
