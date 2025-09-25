package com.website.surotec_academy.domain.mapper;


import com.website.surotec_academy.domain.dto.student.StudentDto;
import com.website.surotec_academy.entity.StudentEntity;
import com.website.surotec_academy.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentMapper {

    public static StudentDto toDto(StudentEntity entity) {
        if (entity == null) return null;
        return  StudentDto
                .builder()
                .idStudent(entity.getId())
                .userDto(UserMapper.toDto(entity.getUser()))
                .status(entity.getStatus())
                .dateCreate(entity.getDateCreate())
                .dateUpdate(entity.getDateUpdate())
                .build();
    }

    public static StudentEntity toEntity(StudentDto dto) {
        if (dto == null) return null;
        UserEntity userEntity = new UserEntity(UserMapper.toEntity(dto.userDto()));
        StudentEntity entity = new StudentEntity();
        entity.setId(dto.idStudent());
        entity.setUser(userEntity);
        entity.setStatus(dto.status());
        entity.setDateCreate(dto.dateCreate());
        entity.setDateUpdate(dto.dateUpdate());
        return entity;
    }

    public static List<StudentDto> toDtoList(List<StudentEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(StudentMapper::toDto).toList();
    }

    public static List<StudentEntity> toEntityList(List<StudentDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(StudentMapper::toEntity).toList();
    }

    public static void updateEntityFromDto(StudentEntity entity, StudentDto dto) {
        if (dto == null || entity == null) return;
        if (dto.userDto() != null && (entity.getUser() == null ||
                !entity.getUser().getId().equals(dto.userDto().idUser()))) {
            entity.setUser(UserMapper.toEntity(dto.userDto()));
        }
        entity.setStatus(dto.status());
        entity.setDateUpdate(dto.dateUpdate());
    }
}
