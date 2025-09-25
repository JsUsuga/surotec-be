package com.website.surotec_academy.domain.dto.request.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        Integer id,
        String documentType,
        String documentNumber,
        @NotNull
        String firstName,
        String lastName,
        String username,
        @Email
        String email,
        Integer status,
        String password,
        String dateCreate,
        String dateUpdate
        ) {}