package com.website.surotec_academy.domain.mapper;

import com.website.surotec_academy.domain.dto.employee.EmployeeDto;
import com.website.surotec_academy.entity.EmployeeEntity;
import com.website.surotec_academy.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMapper {

    public static EmployeeDto toDto(EmployeeEntity entity) {
        if (entity == null) return null;
        return  EmployeeDto
                .builder()
                .idEmployee(entity.getId())
                .position(entity.getPosition())
                .area(entity.getArea())
                .userDto(UserMapper.toDto(entity.getUser()))
                .hireDate(entity.getHireDate())
                .build();
    }

    public static EmployeeEntity toEntity(EmployeeDto dto) {
        if (dto == null) return null;
        UserEntity userEntity = new UserEntity(UserMapper.toEntity(dto.userDto()));
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(dto.idEmployee());
        entity.setPosition(dto.position());
        entity.setArea(dto.area());
        entity.setUser(userEntity);
        entity.setHireDate(dto.hireDate());

        return entity;
    }

    public static List<EmployeeDto> toDtoList(List<EmployeeEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(EmployeeMapper::toDto).toList();
    }

    public static List<EmployeeEntity> toEntityList(List<EmployeeDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(EmployeeMapper::toEntity).toList();
    }

    public static void updateEntityFromDto(EmployeeEntity entity, EmployeeDto dto) {
        if (dto == null || entity == null) return;
        if (dto.userDto() != null && (entity.getUser() == null ||
                !entity.getUser().getId().equals(dto.userDto().idUser()))) {
            entity.setUser(UserMapper.toEntity(dto.userDto()));
        }
        entity.setHireDate(dto.hireDate());
    }
}