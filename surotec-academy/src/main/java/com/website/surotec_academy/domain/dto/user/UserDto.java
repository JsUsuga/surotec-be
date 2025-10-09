package com.website.surotec_academy.domain.dto.user;

import com.website.surotec_academy.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserDto(
        Long idUser,
        @NotNull
        String documentType,
        @NotNull
        @jakarta.validation.constraints.Size(max = 50)
        String documentNumber,
        @NotNull
        @jakarta.validation.constraints.Size(max = 50)
        String firstName,
        @NotNull
        @jakarta.validation.constraints.Size(max = 60)
        String lastName,
        @NotNull
        @jakarta.validation.constraints.Size(max = 30)
        String username,
        int age,
        @NotNull
        @Email
        @jakarta.validation.constraints.Size(max = 100)
        String email,
        @NotNull
        UserStatus status,
        @NotNull
        @jakarta.validation.constraints.Size(max = 50)
        String password,
        LocalDateTime dateCreate,
        LocalDateTime dateUpdate
) {}
