package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.employee.EmployeeDto;
import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.entity.EmployeeEntity;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.UserStatus;
import com.website.surotec_academy.repository.EmployeeRepository;
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

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private EmployeeEntity employeeEntity;
    private EmployeeDto employeeDto;
    private UserEntity userEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Jane");
        userEntity.setLastName("Doe");
        userEntity.setUsername("janedoe");
        userEntity.setPassword("password123");
        userEntity.setEmail("jane.doe@example.com");
        userEntity.setStatus(UserStatus.ACTIVE);

        userDto = UserDto.builder()
                .idUser(1L)
                .firstName("Jane")
                .lastName("Doe")
                .username("janedoe")
                .password("password123")
                .email("jane.doe@example.com")
                .status(UserStatus.ACTIVE)
                .build();

        employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1L);
        employeeEntity.setUser(userEntity);

        employeeDto = EmployeeDto.builder()
                .idEmployee(1L)
                .userDto(userDto)
                .build();
    }

    @Test
    void getAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employeeEntity));

        List<EmployeeDto> result = employeeServiceImpl.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));

        EmployeeDto result = employeeServiceImpl.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(employeeEntity.getUser().getUsername(), result.userDto().username());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void createEmployee() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        EmployeeDto result = employeeServiceImpl.createEmployee(employeeDto);

        assertNotNull(result);
        assertEquals(employeeEntity.getUser().getUsername(), result.userDto().username());
        verify(employeeRepository, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void updateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        EmployeeDto result = employeeServiceImpl.updateEmployee(1L, employeeDto);

        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void deleteEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));

        employeeServiceImpl.deleteEmployee(1L);

        verify(employeeRepository, times(1)).save(employeeEntity);
    }
}
