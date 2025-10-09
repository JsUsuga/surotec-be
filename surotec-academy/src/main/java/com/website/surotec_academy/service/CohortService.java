package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.cohort.CohortDto;

import java.util.List;
import java.util.Optional;

public interface CohortService {
    List<CohortDto> getAllCohorts();
    Optional<CohortDto> getCohortById(Long id);
    CohortDto createCohort(CohortDto cohortDto);
    CohortDto updateCohort(Long id, CohortDto cohortDto);
    void deleteCohort(Long id);
}
