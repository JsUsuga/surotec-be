package com.website.surotec_academy.domain.mapper;


import com.website.surotec_academy.domain.dto.request.request.StudentDto;
import com.website.surotec_academy.entity.StudentEntity;
import com.website.surotec_academy.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentDto toDto(StudentEntity entity) {
        if (entity == null) return null;
        return new StudentDto(
                entity.getId(),
                entity.getUser().getFirstName(),
                entity.getUser().getLastName(),
                entity.getUser().getUsername(),
                entity.getUser().getEmail(),
                entity.getStatus(),
                entity.getDateCreate(),
                entity.getDateUpdate()
        );
    }

    public static StudentEntity toEntity(StudentDto dto) {
        if (dto == null) return null;
        UserEntity userEntity = new UserEntity(dto.firstName(), dto.lastName(), dto.username(),"" , dto.email() );
        StudentEntity entity = new StudentEntity();
        entity.setId(dto.id());
        entity.setUser(userEntity);
        entity.setStatus(dto.status());
        entity.setDateCreate(dto.dateCreate());
        entity.setDateUpdate(dto.dateUpdate());
        return entity;
    }

    public static List<StudentDto> toDtoList(List<StudentEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<StudentEntity> toEntityList(List<StudentDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(StudentMapper::toEntity)
                .collect(Collectors.toList());
    }
}
