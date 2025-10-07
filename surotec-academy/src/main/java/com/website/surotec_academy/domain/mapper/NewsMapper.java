package com.website.surotec_academy.domain.mapper;

import com.website.surotec_academy.domain.dto.NewsCreatedDto;
import com.website.surotec_academy.domain.dto.NewsDto;
import com.website.surotec_academy.entity.NewsEntity;

public class NewsMapper {

    //De entidad a DTO
    public static NewsDto toDto(NewsEntity entity) {
        if (entity == null) return null;

        return NewsDto.builder()
                .idNews(entity.getId())
                .employeeId(entity.getEmployeeId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .publishDate(entity.getPublishDate())
                .urlImage(entity.getImagesUrl())
                .status(entity.getStatus())
                .build();
    }

    //DTO a Entity
    public static NewsEntity toEntity(NewsDto dto) {
        if (dto == null) return null;

        NewsEntity entity = new NewsEntity();
        entity.setId(dto.idNews());
        entity.setEmployeeId(dto.employeeId());
        entity.setTitle(dto.title());
        entity.setContent(dto.content());
        entity.setPublishDate(dto.publishDate());
        entity.setImagesUrl(dto.urlImage());
        entity.setStatus(dto.status());
        return entity;
    }

    //Entity a NewsCreatedDto - cuando se crea una noticia
    public static NewsCreatedDto toCreatedDto(NewsEntity entity) {
        if (entity == null) return null;

        return NewsCreatedDto.builder()
                .idNews(entity.getId())
                .build();
    }
}
