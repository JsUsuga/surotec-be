package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.AcademyProject.AcademyProjectCreatedDto;
import com.website.surotec_academy.domain.dto.AcademyProject.AcademyProjectDto;

import java.util.List;

public interface AcademyProjectService {

    // Crear un nuevo proyecto
    AcademyProjectCreatedDto createProject(AcademyProjectDto dto);

    // Obtener todos los proyectos
    List<AcademyProjectDto> getAllProjects();

    // Obtener un proyecto por su ID
    AcademyProjectDto getProjectById(Long id);

    // Actualizar un proyecto existente
    AcademyProjectDto updateProject(Long id, AcademyProjectDto dto);

    // Eliminar un proyecto
    void deleteProject(Long id);
}