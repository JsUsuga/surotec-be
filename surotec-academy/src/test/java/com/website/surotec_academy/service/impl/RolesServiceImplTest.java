package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.roles.RolesDto;
import com.website.surotec_academy.entity.RolesEntity;
import com.website.surotec_academy.repository.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RolesServiceImplTest {

    @Mock
    private RolesRepository rolesRepository;

    @InjectMocks
    private RolesServiceImpl rolesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 🧪 Test getAllRoles()
    @Test
    void getAllRoles_shouldReturnListOfRoles() {
        RolesEntity role1 = new RolesEntity(1L, "Admin", "Administrador del sistema");
        RolesEntity role2 = new RolesEntity(2L, "User", "Usuario regular");

        when(rolesRepository.findAll()).thenReturn(List.of(role1, role2));

        var result = rolesService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("Admin", result.get(0).name());
        verify(rolesRepository).findAll();
    }

    // 🧪 Test getRoleById()
    @Test
    void getRoleById_shouldReturnRole_whenExists() {
        RolesEntity entity = new RolesEntity(1L, "Manager", "Gestor de proyectos");
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(entity));

        RolesDto result = rolesService.getRoleById(1L);

        assertNotNull(result);
        assertEquals("Manager", result.name());
        verify(rolesRepository).findById(1L);
    }

    @Test
    void getRoleById_shouldThrow_whenRoleNotFound() {
        when(rolesRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> rolesService.getRoleById(99L));
        assertTrue(ex.getMessage().contains("Role not found"));
        verify(rolesRepository).findById(99L);
    }

    // 🧪 Test createRoles()
    @Test
    void createRoles_shouldSaveAndReturnRole() {
        RolesDto dto = RolesDto.builder()
                .name("Editor")
                .description("Edita contenidos")
                .build();

        RolesEntity savedEntity = new RolesEntity(1L, "Editor", "Edita contenidos");

        when(rolesRepository.save(any(RolesEntity.class))).thenReturn(savedEntity);

        RolesDto result = rolesService.createRoles(dto);

        assertNotNull(result);
        assertEquals("Editor", result.name());
        verify(rolesRepository).save(any(RolesEntity.class));
    }

    // 🧪 Test updateRoles()
    @Test
    void updateRoles_shouldUpdateExistingRole() {
        RolesEntity existing = new RolesEntity(1L, "User", "Usuario básico");

        RolesDto updateDto = RolesDto.builder()
                .name("Advanced User")
                .description("Usuario avanzado")
                .build();

        when(rolesRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(rolesRepository.save(any(RolesEntity.class))).thenReturn(existing);

        RolesDto result = rolesService.updateRoles(1L, updateDto);

        assertNotNull(result);
        verify(rolesRepository).findById(1L);
        verify(rolesRepository).save(existing);
    }

    @Test
    void updateRoles_shouldThrow_whenNotFound() {
        when(rolesRepository.findById(99L)).thenReturn(Optional.empty());

        RolesDto dto = RolesDto.builder().name("Ghost").description("No existe").build();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> rolesService.updateRoles(99L, dto));
        assertTrue(ex.getMessage().contains("Role not found"));
        verify(rolesRepository).findById(99L);
    }

    // 🧪 Test deleteRoles()
    @Test
    void deleteRoles_shouldDeleteExistingRole() {
        when(rolesRepository.existsById(1L)).thenReturn(true);
        doNothing().when(rolesRepository).deleteById(1L);

        rolesService.deleteRoles(1L);

        verify(rolesRepository).deleteById(1L);
    }

    @Test
    void deleteRoles_shouldThrow_whenRoleNotExists() {
        when(rolesRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> rolesService.deleteRoles(1L));
        assertTrue(ex.getMessage().contains("Role not found"));
        verify(rolesRepository).existsById(1L);
    }
}
