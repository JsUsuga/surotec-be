package com.website.surotec_academy.domain.dto.employee;
import lombok.Builder;

@Builder
public record EmployeeCreatedDto(Long idEmployee, Long idUser) {}
