package com.website.surotec_academy.domain.mapper;


import com.website.surotec_academy.domain.dto.AcademyProjectDto;
import com.website.surotec_academy.domain.dto.AcademyProjectCreatedDto;
import com.website.surotec_academy.entity.AcademyProjectEntity;
import com.website.surotec_academy.entity.EmployeeEntity;

import java.time.LocalDateTime;

public class AcademyProjectMapper {

    // Entity → DTO
    public static AcademyProjectDto toDto(AcademyProjectEntity entity) {
        if (entity == null) return null;

        return AcademyProjectDto.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployee() != null ? entity.getEmployee().getId() : null)
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .caption(entity.getCaption())
                .publishDate(entity.getPublishDate())
                .status(entity.getStatus())
                .build();
    }

    // DTO → Entity
    public static AcademyProjectEntity toEntity(AcademyProjectDto dto, EmployeeEntity employee) {
        if (dto == null) return null;

        AcademyProjectEntity entity = new AcademyProjectEntity();
        entity.setId(dto.id());
        entity.setEmployee(employee);
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setImageUrl(dto.imageUrl());
        entity.setCaption(dto.caption());
        entity.setPublishDate(dto.publishDate() != null ? dto.publishDate() : LocalDateTime.now());
        entity.setStatus(dto.status());

        return entity;
    }

    public static AcademyProjectEntity toEntity(AcademyProjectDto dto) {
        return toEntity(dto, null);
    }


    // Entity → CreatedDto (cuando se crea un nuevo proyecto)
    public static AcademyProjectCreatedDto toCreatedDto(AcademyProjectEntity entity) {
        if (entity == null) return null;

        return AcademyProjectCreatedDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }
}
