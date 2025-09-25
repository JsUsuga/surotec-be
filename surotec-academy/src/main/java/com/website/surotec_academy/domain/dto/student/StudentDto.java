package com.website.surotec_academy.domain.dto.student;

import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.enums.StudentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StudentDto(
        Long idStudent,
        StudentStatus status,
        LocalDateTime dateCreate,
        LocalDateTime dateUpdate,
        UserDto userDto
) {}
