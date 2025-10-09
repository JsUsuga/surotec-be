package com.website.surotec_academy.domain.mapper;

import com.website.surotec_academy.domain.dto.cohort.CohortDto;
import com.website.surotec_academy.entity.CohortEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CohortMapper {

    public static CohortDto toDto(CohortEntity entity) {
        if (entity == null) return null;

        return CohortDto.builder()
                .id(entity.getId())
                .year(entity.getYear())
                .semester(entity.getSemester())
                .description(entity.getDescription())
                .build();
    }

    public static CohortEntity toEntity(CohortDto dto) {
        if (dto == null) return null;

        CohortEntity entity = new CohortEntity();
        entity.setId(dto.id());
        entity.setYear(dto.year());
        entity.setSemester(dto.semester());
        entity.setDescription(dto.description());
        return entity;
    }
}
