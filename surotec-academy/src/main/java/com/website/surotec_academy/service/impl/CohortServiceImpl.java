package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.cohort.CohortDto;
import com.website.surotec_academy.domain.mapper.CohortMapper;
import com.website.surotec_academy.entity.CohortEntity;
import com.website.surotec_academy.repository.CohortRepository;
import com.website.surotec_academy.service.CohortService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CohortServiceImpl implements CohortService {

    private static final Logger log = LoggerFactory.getLogger(CohortServiceImpl.class);

    private final CohortRepository cohortRepository;

    // ✅ Solo inyectamos el repository (el mapper se usa estáticamente)
    public CohortServiceImpl(CohortRepository cohortRepository) {
        this.cohortRepository = cohortRepository;
        log.info("CohortServiceImpl initialized with CohortRepository");
    }

    @Override
    public List<CohortDto> getAllCohorts() {
        log.info("Fetching all cohorts");
        try {
            List<CohortDto> cohorts = cohortRepository.findAll()
                    .stream()
                    .map(CohortMapper::toDto)
                    .collect(Collectors.toList());
            log.info("Found {} cohorts", cohorts.size());
            return cohorts;
        } catch (Exception e) {
            log.error("Error retrieving cohorts", e);
            throw new RuntimeException("Error retrieving cohorts: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<CohortDto> getCohortById(Long id) {
        log.info("Fetching cohort with id {}", id);
        try {
            Optional<CohortDto> cohortOpt = cohortRepository.findById(id)
                    .map(CohortMapper::toDto);
            if (cohortOpt.isEmpty()) {
                log.warn("Cohort not found with id {}", id);
            } else {
                log.info("Cohort found with id {}", id);
            }
            return cohortOpt;
        } catch (Exception e) {
            log.error("Error retrieving cohort with id {}", id, e);
            throw new RuntimeException("Error retrieving cohort: " + e.getMessage(), e);
        }
    }

    @Override
    public CohortDto createCohort(CohortDto cohortDto) {
        log.info("Creating cohort for year {} semester {}", cohortDto.year(), cohortDto.semester());
        try {
            CohortEntity entity = CohortMapper.toEntity(cohortDto);
            CohortEntity saved = cohortRepository.save(entity);
            log.info("Cohort created with id {}", saved.getId());
            return CohortMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error creating cohort for year {} semester {}", cohortDto.year(), cohortDto.semester(), e);
            throw new RuntimeException("Error creating cohort: " + e.getMessage(), e);
        }
    }

    @Override
    public CohortDto updateCohort(Long id, CohortDto cohortDto) {
        log.info("Updating cohort with id {}", id);
        try {
            CohortEntity existing = cohortRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Cohort not found with id {}", id);
                        return new EntityNotFoundException("Cohort not found with id: " + id);
                    });

            // ✅ Actualizamos solo los campos editables
            existing.setYear(cohortDto.year());
            existing.setSemester(cohortDto.semester());
            existing.setDescription(cohortDto.description());

            CohortEntity updated = cohortRepository.save(existing);
            log.info("Cohort updated with id {}", updated.getId());
            return CohortMapper.toDto(updated);
        } catch (Exception e) {
            log.error("Error updating cohort with id {}", id, e);
            throw new RuntimeException("Error updating cohort: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCohort(Long id) {
        log.info("Deleting cohort with id {}", id);
        try {
            if (!cohortRepository.existsById(id)) {
                log.warn("Cohort not found with id {}", id);
                throw new EntityNotFoundException("Cohort not found with id: " + id);
            }
            cohortRepository.deleteById(id);
            log.info("Cohort deleted with id {}", id);
        } catch (Exception e) {
            log.error("Error deleting cohort with id {}", id, e);
            throw new RuntimeException("Error deleting cohort: " + e.getMessage(), e);
        }
    }
}
