package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.AcademyProjectCreatedDto;
import com.website.surotec_academy.domain.dto.AcademyProjectDto;
import com.website.surotec_academy.entity.AcademyProjectEntity;
import com.website.surotec_academy.entity.EmployeeEntity;
import com.website.surotec_academy.enums.AcademyProjectStatus;
import com.website.surotec_academy.repository.AcademyProjectRepository;
import com.website.surotec_academy.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AcademyProjectServiceImplTest {

    @Mock
    private AcademyProjectRepository academyProjectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AcademyProjectServiceImpl academyProjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProject() {
        // Arrange
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(1L);

        AcademyProjectDto dto = AcademyProjectDto.builder()
                .employeeId(1L)
                .title("Proyecto de prueba")
                .description("Descripción de prueba")
                .imageUrl("https://example.com/img.png")
                .caption("Proyecto ejemplo")
                .status(AcademyProjectStatus.PUBLISHED)
                .build();

        AcademyProjectEntity savedEntity = new AcademyProjectEntity();
        savedEntity.setId(1L);
        savedEntity.setEmployee(employee);
        savedEntity.setTitle(dto.title());
        savedEntity.setDescription(dto.description());
        savedEntity.setImageUrl(dto.imageUrl());
        savedEntity.setCaption(dto.caption());
        savedEntity.setStatus(dto.status());
        savedEntity.setPublishDate(LocalDateTime.now());

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(academyProjectRepository.save(any(AcademyProjectEntity.class))).thenReturn(savedEntity);

        // Act
        AcademyProjectCreatedDto created = academyProjectService.createProject(dto);

        // Assert
        assertNotNull(created);
        assertEquals(1L, created.id());
        verify(employeeRepository).findById(1L);
        verify(academyProjectRepository).save(any(AcademyProjectEntity.class));
    }

    @Test
    void getProjectById() {
        // Arrange
        AcademyProjectEntity entity = new AcademyProjectEntity();
        entity.setId(1L);
        entity.setTitle("Proyecto encontrado");

        when(academyProjectRepository.findById(1L)).thenReturn(Optional.of(entity));

        // Act
        var result = academyProjectService.getProjectById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Proyecto encontrado", result.title());
        verify(academyProjectRepository).findById(1L);
    }

    @Test
    void getAllProjects() {
        // Arrange
        AcademyProjectEntity project1 = new AcademyProjectEntity();
        project1.setId(1L);
        project1.setTitle("Proyecto 1");

        AcademyProjectEntity project2 = new AcademyProjectEntity();
        project2.setId(2L);
        project2.setTitle("Proyecto 2");

        when(academyProjectRepository.findAll()).thenReturn(List.of(project1, project2));

        // Act
        var result = academyProjectService.getAllProjects();

        // Assert
        assertEquals(2, result.size());
        verify(academyProjectRepository).findAll();
    }

    @Test
    void updateProject() {
        // Arrange
        AcademyProjectEntity existing = new AcademyProjectEntity();
        existing.setId(1L);
        existing.setTitle("Título viejo");

        AcademyProjectDto dto = AcademyProjectDto.builder()
                .title("Título nuevo")
                .description("Actualizado")
                .imageUrl("https://example.com/new.png")
                .caption("Nuevo caption")
                .status(AcademyProjectStatus.PUBLISHED)
                .build();

        when(academyProjectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(academyProjectRepository.save(any(AcademyProjectEntity.class))).thenReturn(existing);

        // Act
        var updated = academyProjectService.updateProject(1L, dto);

        // Assert
        assertNotNull(updated);
        assertEquals("Título nuevo", updated.title());
        verify(academyProjectRepository).findById(1L);
        verify(academyProjectRepository).save(existing);
    }

    @Test
    void deleteProject() {
        // Arrange
        when(academyProjectRepository.existsById(1L)).thenReturn(true);
        doNothing().when(academyProjectRepository).deleteById(1L);

        // Act
        academyProjectService.deleteProject(1L);

        // Assert
        verify(academyProjectRepository).deleteById(1L);
    }

    @Test
    void deleteProject_NotFound() {
        // Arrange
        when(academyProjectRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> academyProjectService.deleteProject(1L));
    }
}
