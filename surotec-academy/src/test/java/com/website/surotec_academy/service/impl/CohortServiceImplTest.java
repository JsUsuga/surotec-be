package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.cohort.CohortDto;
import com.website.surotec_academy.entity.CohortEntity;
import com.website.surotec_academy.enums.SemesterEnum;
import com.website.surotec_academy.repository.CohortRepository;
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

class CohortServiceImplTest {

    @Mock
    private CohortRepository cohortRepository;

    @InjectMocks
    private CohortServiceImpl cohortServiceImpl;

    private CohortEntity cohortEntity;
    private CohortDto cohortDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cohortEntity = new CohortEntity();
        cohortEntity.setId(1L);
        cohortEntity.setYear(2025);
        cohortEntity.setSemester(SemesterEnum.FIRST);
        cohortEntity.setDescription("Cohorte de prueba");
        cohortEntity.setDateCreate(LocalDateTime.now());
        cohortEntity.setDateUpdate(LocalDateTime.now());

        cohortDto = CohortDto.builder()
                .id(1L)
                .year(2025)
                .semester(SemesterEnum.FIRST)
                .description("Cohorte de prueba")
                .build();
    }

    @Test
    void getAllCohorts() {
        when(cohortRepository.findAll()).thenReturn(List.of(cohortEntity));

        List<CohortDto> result = cohortServiceImpl.getAllCohorts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2025, result.get(0).year());
        assertEquals(SemesterEnum.FIRST, result.get(0).semester());
        verify(cohortRepository, times(1)).findAll();
    }

    @Test
    void getCohortById() {
        when(cohortRepository.findById(1L)).thenReturn(Optional.of(cohortEntity));

        Optional<CohortDto> result = cohortServiceImpl.getCohortById(1L);

        assertTrue(result.isPresent());
        assertEquals(2025, result.get().year());
        assertEquals(SemesterEnum.FIRST, result.get().semester());
        verify(cohortRepository, times(1)).findById(1L);
    }

    @Test
    void createCohort() {
        when(cohortRepository.save(any(CohortEntity.class))).thenReturn(cohortEntity);

        CohortDto result = cohortServiceImpl.createCohort(cohortDto);

        assertNotNull(result);
        assertEquals(2025, result.year());
        assertEquals(SemesterEnum.FIRST, result.semester());
        verify(cohortRepository, times(1)).save(any(CohortEntity.class));
    }

    @Test
    void updateCohort() {
        when(cohortRepository.findById(1L)).thenReturn(Optional.of(cohortEntity));
        when(cohortRepository.save(any(CohortEntity.class))).thenReturn(cohortEntity);

        CohortDto result = cohortServiceImpl.updateCohort(1L, cohortDto);

        assertNotNull(result);
        assertEquals(2025, result.year());
        assertEquals(SemesterEnum.FIRST, result.semester());
        verify(cohortRepository, times(1)).save(any(CohortEntity.class));
    }

    @Test
    void updateCohort_notFound() {
        when(cohortRepository.findById(2L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cohortServiceImpl.updateCohort(2L, cohortDto));

        assertEquals("Cohort not found with id: 2", exception.getMessage());
        verify(cohortRepository, times(1)).findById(2L);
        verify(cohortRepository, never()).save(any());
    }

    @Test
    void deleteCohort() {
        when(cohortRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cohortRepository).deleteById(1L);

        cohortServiceImpl.deleteCohort(1L);

        verify(cohortRepository, times(1)).existsById(1L);
        verify(cohortRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCohort_notFound() {
        when(cohortRepository.existsById(2L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cohortServiceImpl.deleteCohort(2L));

        assertEquals("Cohort not found with id: 2", exception.getMessage());
        verify(cohortRepository, times(1)).existsById(2L);
        verify(cohortRepository, never()).deleteById(any());
    }
}
