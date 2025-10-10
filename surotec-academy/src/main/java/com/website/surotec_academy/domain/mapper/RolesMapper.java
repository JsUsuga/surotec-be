package com.website.surotec_academy.domain.mapper;

import com.website.surotec_academy.domain.dto.roles.RolesDto;
import com.website.surotec_academy.entity.RolesEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RolesMapper {

    /**
     * Convierte una RolesEntity a un RolesDto.
     */
    public static RolesDto toDto(RolesEntity entity) {
        if (entity == null) return null;
        // Usamos el constructor del record directamente, ya que es más simple que el builder para este caso
        return new RolesDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    /**
     * Convierte un RolesDto a una RolesEntity.
     */
    public static RolesEntity toEntity(RolesDto dto) {
        if (dto == null) return null;
        RolesEntity entity = new RolesEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        return entity;
    }

    /**
     * Convierte una lista de RolesEntity a una lista de RolesDto.
     */
    public static List<RolesDto> toDtoList(List<RolesEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(RolesMapper::toDto).toList();
    }

    /**
     * Convierte una lista de RolesDto a una lista de RolesEntity.
     */
    public static List<RolesEntity> toEntityList(List<RolesDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(RolesMapper::toEntity).toList();
    }

    /**
     * Actualiza una entidad existente con los datos de un DTO.
     * Es útil para las operaciones de actualización en el servicio.
     */
    public static void updateEntityFromDto(RolesEntity entity, RolesDto dto) {
        if (dto == null || entity == null) return;

        // Solo actualiza los campos si no son nulos en el DTO
        if (dto.name() != null) {
            entity.setName(dto.name());
        }
        if (dto.description() != null) {
            entity.setDescription(dto.description());
        }
    }
}