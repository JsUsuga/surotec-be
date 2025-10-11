package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.academyproject.AcademyProjectCreatedDto;
import com.website.surotec_academy.domain.dto.academyproject.AcademyProjectDto;
import com.website.surotec_academy.domain.mapper.AcademyProjectMapper;
import com.website.surotec_academy.entity.AcademyProjectEntity;
import com.website.surotec_academy.entity.EmployeeEntity;
import com.website.surotec_academy.enums.AcademyProjectStatus;
import com.website.surotec_academy.repository.AcademyProjectRepository;
import com.website.surotec_academy.repository.EmployeeRepository;
import com.website.surotec_academy.service.AcademyProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademyProjectServiceImpl implements AcademyProjectService {

    private static final Logger log = LoggerFactory.getLogger(AcademyProjectServiceImpl.class);

    private final AcademyProjectRepository academyProjectRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AcademyProjectCreatedDto createProject(AcademyProjectDto dto) {
        log.info("Creating academy project with title: '{}', employeeId: {}", dto.title(), dto.employeeId());
        try {
            // Validar que el empleado exista
            EmployeeEntity employee = employeeRepository.findById(dto.employeeId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Empleado no encontrado con ID: " + dto.employeeId()
                    ));

            // Convertir el DTO a entidad (usando el mapper)
            AcademyProjectEntity entity = AcademyProjectMapper.toEntity(dto, employee);

            // Verificar el estado del proyecto
            // Si viene nulo o vacío, establecer por defecto DRAFT
            if (entity.getStatus() == null) {
                log.warn("Status not provided, setting default value to DRAFT");
                entity.setStatus(AcademyProjectStatus.DRAFT);
            }

            // Guardar el proyecto en la base de datos
            AcademyProjectEntity saved = academyProjectRepository.save(entity);
            entity.setPublishDate(LocalDateTime.now());
            log.info("Academy project created successfully with id: {}", saved.getId());

            // Retornar un DTO con el ID del nuevo registro
            return AcademyProjectMapper.toCreatedDto(saved);

        } catch (EntityNotFoundException e) {
            log.warn("Employee not found while creating project: {}", dto.employeeId());
            throw e;
        } catch (Exception e) {
            log.error("Error creating academy project: '{}'", dto.title(), e);
            throw new RuntimeException("Error creating academy project: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AcademyProjectDto> getAllProjects() {
        log.info("Fetching all academy projects");
        try {
            List<AcademyProjectDto> projects = academyProjectRepository.findAll()
                    .stream()
                    .map(AcademyProjectMapper::toDto)
                    .toList();
            log.info("Found {} academy projects", projects.size());
            return projects;
        } catch (Exception e) {
            log.error("Error fetching all academy projects", e);
            throw new RuntimeException("Error fetching academy projects: " + e.getMessage(), e);
        }
    }

    @Override
    public AcademyProjectDto getProjectById(Long id) {
        log.info("Fetching academy project with id: {}", id);
        try {
            AcademyProjectEntity entity = academyProjectRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Academy project not found with id: {}", id);
                        return new EntityNotFoundException("Proyecto no encontrado con ID: " + id);
                    });
            log.info("Academy project found with id: {}", id);
            return AcademyProjectMapper.toDto(entity);
        } catch (Exception e) {
            log.error("Error fetching academy project with id: {}", id, e);
            throw new RuntimeException("Error fetching academy project: " + e.getMessage(), e);
        }
    }

    @Override
    public AcademyProjectDto updateProject(Long id, AcademyProjectDto dto) {
        log.info("Updating academy project with id: {}, new title: '{}', status: {}", id, dto.title(), dto.status());
        try {
            AcademyProjectEntity existing = academyProjectRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Academy project not found for update with id: {}", id);
                        return new EntityNotFoundException("Proyecto no encontrado con ID: " + id);
                    });

            existing.setTitle(dto.title());
            existing.setDescription(dto.description());
            existing.setImageUrl(dto.imageUrl());
            existing.setCaption(dto.caption());
            existing.setStatus(dto.status());

            AcademyProjectEntity updated = academyProjectRepository.save(existing);
            log.info("Academy project updated successfully with id: {}", id);
            return AcademyProjectMapper.toDto(updated);

        } catch (Exception e) {
            log.error("Error updating academy project with id: {}", id, e);
            throw new RuntimeException("Error updating academy project: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteProject(Long id) {
        log.info("Deleting academy project with id: {}", id);
        try {
            if (!academyProjectRepository.existsById(id)) {
                log.warn("Attempted to delete non-existent academy project with id: {}", id);
                throw new EntityNotFoundException("Proyecto no encontrado con ID: " + id);
            }
            academyProjectRepository.deleteById(id);
            log.info("Academy project deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting academy project with id: {}", id, e);
            throw new RuntimeException("Error deleting academy project: " + e.getMessage(), e);
        }
    }
}
