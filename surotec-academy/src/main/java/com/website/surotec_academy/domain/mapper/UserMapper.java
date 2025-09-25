package com.website.surotec_academy.domain.mapper;

import com.website.surotec_academy.domain.dto.request.request.UserDto;
import com.website.surotec_academy.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(UserEntity entity) {
        if (entity == null) return null;
        return new UserDto(
                entity.getId(),
                entity.getDocumentType(),
                entity.getDocumentNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getPassword(),
                entity.getDateCreate(),
                entity.getDateUpdate()
        );
    }

    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(dto.id());
        entity.setDocumentType(dto.documentType());
        entity.setDocumentNumber(dto.documentNumber());
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setStatus(dto.status());
        entity.setDateCreate(dto.dateCreate());
        entity.setDateUpdate(dto.dateUpdate());

        return entity;
    }

    public static List<UserDto> toDtoList(List<UserEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<UserEntity> toEntityList(List<UserDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
    
}



