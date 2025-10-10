package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.AcademyProjectCreatedDto;
import com.website.surotec_academy.domain.dto.AcademyProjectDto;
import com.website.surotec_academy.service.AcademyProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academy-projects")
@RequiredArgsConstructor

public class AcademyProjectController {
    private final AcademyProjectService academyProjectService;

    @PostMapping
    public ResponseEntity<AcademyProjectCreatedDto> createProject(@Valid @RequestBody AcademyProjectDto dto) {
        AcademyProjectCreatedDto created = academyProjectService.createProject(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<AcademyProjectDto>> getAllProjects() {
        List<AcademyProjectDto> projects = academyProjectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademyProjectDto> getProjectById(@PathVariable Long id) {
        AcademyProjectDto project = academyProjectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        academyProjectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
