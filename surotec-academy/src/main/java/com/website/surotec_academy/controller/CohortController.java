package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.cohort.CohortDto;
import com.website.surotec_academy.service.CohortService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cohorts")
@RequiredArgsConstructor
@Tag(name = "Cohort Controller", description = "Operaciones relacionadas con las cohortes académicas")
public class CohortController {

    private final CohortService cohortService;

    @Operation(summary = "Obtener todas las cohortes")
    @GetMapping
    public ResponseEntity<List<CohortDto>> getAllCohorts() {
        return ResponseEntity.ok(cohortService.getAllCohorts());
    }

    @Operation(summary = "Obtener una cohorte por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CohortDto> getCohortById(@PathVariable Long id) {
        return cohortService.getCohortById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Crear una nueva cohorte")
    @PostMapping
    public ResponseEntity<CohortDto> createCohort(@Valid @RequestBody CohortDto cohortDto) {
        return ResponseEntity.ok(cohortService.createCohort(cohortDto));
    }

    @Operation(summary = "Actualizar una cohorte existente")
    @PutMapping("/{id}")
    public ResponseEntity<CohortDto> updateCohort(@PathVariable Long id, @Valid @RequestBody CohortDto cohortDto) {
        return ResponseEntity.ok(cohortService.updateCohort(id, cohortDto));
    }

    @Operation(summary = "Eliminar una cohorte por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCohort(@PathVariable Long id) {
        cohortService.deleteCohort(id);
        return ResponseEntity.noContent().build();
    }
}
