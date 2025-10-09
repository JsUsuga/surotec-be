package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.cohort.CohortDto;
import com.website.surotec_academy.domain.mapper.CohortMapper;
import com.website.surotec_academy.entity.CohortEntity;
import com.website.surotec_academy.repository.CohortRepository;
import com.website.surotec_academy.service.CohortService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CohortServiceImpl implements CohortService {

    private final CohortRepository cohortRepository;

    // ✅ Solo inyectamos el repository (el mapper se usa estáticamente)
    public CohortServiceImpl(CohortRepository cohortRepository) {
        this.cohortRepository = cohortRepository;
    }

    @Override
    public List<CohortDto> getAllCohorts() {
        return cohortRepository.findAll()
                .stream()
                .map(CohortMapper::toDto) // usa mapper estático
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CohortDto> getCohortById(Long id) {
        return cohortRepository.findById(id)
                .map(CohortMapper::toDto);
    }

    @Override
    public CohortDto createCohort(CohortDto cohortDto) {
        CohortEntity entity = CohortMapper.toEntity(cohortDto);
        CohortEntity saved = cohortRepository.save(entity);
        return CohortMapper.toDto(saved);
    }

    @Override
    public CohortDto updateCohort(Long id, CohortDto cohortDto) {
        CohortEntity existing = cohortRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cohort not found with id: " + id));

        // ✅ Actualizamos solo los campos editables
        existing.setYear(cohortDto.year());
        existing.setSemester(cohortDto.semester());
        existing.setDescription(cohortDto.description());

        CohortEntity updated = cohortRepository.save(existing);
        return CohortMapper.toDto(updated);
    }

    @Override
    public void deleteCohort(Long id) {
        if (!cohortRepository.existsById(id)) {
            throw new EntityNotFoundException("Cohort not found with id: " + id);
        }
        cohortRepository.deleteById(id);
    }
}
