package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.AcademyProject.AcademyProjectCreatedDto;
import com.website.surotec_academy.domain.dto.AcademyProject.AcademyProjectDto;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademyProjectServiceImpl implements AcademyProjectService {

    private final AcademyProjectRepository academyProjectRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AcademyProjectCreatedDto createProject(AcademyProjectDto dto) {

        // Validar que el empleado exista
        EmployeeEntity employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Empleado no encontrado con ID: " + dto.employeeId()
                ));

        // Convertir el DTO a entidad
        AcademyProjectEntity entity = AcademyProjectMapper.toEntity(dto, employee);

        // Si no se define estado, aplicar DRAFT
        if (entity.getStatus() == null) {
            entity.setStatus(AcademyProjectStatus.DRAFT);
        }

        AcademyProjectEntity saved = academyProjectRepository.save(entity);

        return AcademyProjectMapper.toCreatedDto(saved);
    }

    @Override
    public List<AcademyProjectDto> getAllProjects() {
        return academyProjectRepository.findAll()
                .stream()
                .map(AcademyProjectMapper::toDto)
                .toList();
    }

    @Override
    public AcademyProjectDto getProjectById(Long id) {
        AcademyProjectEntity entity = academyProjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con ID: " + id));
        return AcademyProjectMapper.toDto(entity);
    }

    @Override
    public AcademyProjectDto updateProject(Long id, AcademyProjectDto dto) {
        AcademyProjectEntity existing = academyProjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con ID: " + id));

        existing.setTitle(dto.title());
        existing.setDescription(dto.description());
        existing.setImageUrl(dto.imageUrl());
        existing.setCaption(dto.caption());
        existing.setStatus(dto.status());

        AcademyProjectEntity updated = academyProjectRepository.save(existing);
        return AcademyProjectMapper.toDto(updated);
    }

    @Override
    public void deleteProject(Long id) {
        if (!academyProjectRepository.existsById(id)) {
            throw new EntityNotFoundException("Proyecto no encontrado con ID: " + id);
        }
        academyProjectRepository.deleteById(id);
    }
}
