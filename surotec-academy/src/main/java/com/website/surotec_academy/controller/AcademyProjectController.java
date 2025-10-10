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
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class AcademyProjectController {

    private final AcademyProjectService academyProjectService;

    @PostMapping
    public ResponseEntity<AcademyProjectCreatedDto> create(@RequestBody @Valid AcademyProjectDto dto) {
        return ResponseEntity.ok(academyProjectService.createProject(dto));
    }

    @GetMapping
    public ResponseEntity<List<AcademyProjectDto>> getAll() {
        return ResponseEntity.ok(academyProjectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademyProjectDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(academyProjectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademyProjectDto> update(@PathVariable Long id, @RequestBody AcademyProjectDto dto) {
        return ResponseEntity.ok(academyProjectService.updateProject(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        academyProjectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}