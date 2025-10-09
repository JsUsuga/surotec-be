package com.website.surotec_academy.domain.dto.employee;

import com.website.surotec_academy.domain.dto.user.UserDto;
import lombok.Builder;
import java.time.LocalDateTime;


@Builder

public record EmployeeDto(
        Long idEmployee,
        String position,
        String area,
        LocalDateTime hireDate,
        UserDto userDto
) {}