package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.entity.*;
import com.website.surotec_academy.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class EmployeeRoleServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private EmployeeRoleRepository employeeRoleRepository;

    @InjectMocks
    private EmployeeRoleServiceImpl employeeRoleService;

    private EmployeeEntity employee;
    private RolesEntity role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un UserEntity simulado
        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setFirstName("Gabriela");
        user.setLastName("Montilla");
        user.setUsername("gmontilla");
        user.setPassword("123456");
        user.setEmail("gmontilla@example.com");

        // Crear un empleado con ese usuario
        employee = new EmployeeEntity();
        employee.setId(1L);
        employee.setPosition("Developer");
        employee.setArea("IT");
        employee.setUser(user);

        // Crear un rol simulado
        role = new RolesEntity();
        role.setId(2L);
        role.setName("ADMIN");
        role.setDescription("Administrador del sistema");
    }

    @Test
    void assignRoleToEmployee_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(rolesRepository.findById(2L)).thenReturn(Optional.of(role));
        when(employeeRoleRepository.existsById(any(EmployeeRoleId.class))).thenReturn(false);

        employeeRoleService.assignRoleToEmployee(1L, 2L);

        verify(employeeRepository).findById(1L);
        verify(rolesRepository).findById(2L);
        verify(employeeRoleRepository).save(any(EmployeeRoleEntity.class));
    }

    @Test
    void assignRoleToEmployee_AlreadyExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(rolesRepository.findById(2L)).thenReturn(Optional.of(role));
        when(employeeRoleRepository.existsById(any(EmployeeRoleId.class))).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeRoleService.assignRoleToEmployee(1L, 2L));

        assertEquals("Role is already assigned to this employee.", exception.getMessage());
    }

    @Test
    void assignRoleToEmployee_EmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeRoleService.assignRoleToEmployee(1L, 2L));

        assertEquals("Employee not found with id: 1", exception.getMessage());
    }

    @Test
    void assignRoleToEmployee_RoleNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(rolesRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeRoleService.assignRoleToEmployee(1L, 2L));

        assertEquals("Role not found with id: 2", exception.getMessage());
    }

    @Test
    void removeRoleFromEmployee_Success() {
        when(employeeRoleRepository.existsById(any(EmployeeRoleId.class))).thenReturn(true);

        employeeRoleService.removeRoleFromEmployee(1L, 2L);

        verify(employeeRoleRepository).deleteById(any(EmployeeRoleId.class));
    }

    @Test
    void removeRoleFromEmployee_NotFound() {
        when(employeeRoleRepository.existsById(any(EmployeeRoleId.class))).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeRoleService.removeRoleFromEmployee(1L, 2L));

        assertEquals("This role assignment does not exist.", exception.getMessage());
    }
}
