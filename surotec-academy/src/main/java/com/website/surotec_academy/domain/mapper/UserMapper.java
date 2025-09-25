package com.website.surotec_academy.domain.mapper;

import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.DocumentType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto toDto(UserEntity entity) {
        if (entity == null) return null;
        return  UserDto.builder()
                .idUser(entity.getId())
                .documentType(entity.getDocumentType().name())
                .documentNumber(entity.getDocumentNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .status(entity.getStatus())
                .dateCreate(entity.getDateCreate())
                .dateUpdate(entity.getDateUpdate())
                .build();
    }

    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(dto.idUser());
        entity.setDocumentType(
                dto.documentType() != null
                        ? DocumentType.valueOf(dto.documentType())
                        : null
        );        entity.setDocumentNumber(dto.documentNumber());
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setUsername(dto.username());
        entity.setAge(dto.age());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setStatus(dto.status());
        entity.setDateCreate(dto.dateCreate());
        entity.setDateUpdate(dto.dateUpdate());

        return entity;
    }

    public static List<UserDto> toDtoList(List<UserEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream()
                .map(UserMapper::toDto).toList();
    }

    public static List<UserEntity> toEntityList(List<UserDto> dtos) {
        if (dtos == null){
            return List.of();
        }
        return dtos.stream()
                .map(UserMapper::toEntity).toList();
    }

    public static void updateEntityFromDto(UserEntity entity, UserDto dto) {
        entity.setDocumentType(
                dto.documentType() != null
                        ? DocumentType.valueOf(dto.documentType())
                        : null
        );        entity.setDocumentNumber(dto.documentNumber());
        // Only update documentNumber if different
        if (!entity.getDocumentNumber().equals(dto.documentNumber())) {
            entity.setDocumentNumber(dto.documentNumber());
        }
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setUsername(dto.username());
        entity.setAge(dto.age());
        entity.setEmail(dto.email());
        entity.setStatus(dto.status());
        entity.setPassword(dto.password());
        entity.setDateUpdate(LocalDateTime.now());
        // Do not update id or dateCreate
    }
}
