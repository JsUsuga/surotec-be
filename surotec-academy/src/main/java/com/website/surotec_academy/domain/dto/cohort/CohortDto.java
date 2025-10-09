package com.website.surotec_academy.domain.dto.cohort;

import com.website.surotec_academy.enums.SemesterEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record CohortDto(
        Long id,

        @NotNull(message = "El año no puede ser nulo")
        @Min(value = 2000, message = "El año debe ser mayor o igual a 2000")
        Integer year,

        @NotNull(message = "El semestre no puede ser nulo")
        SemesterEnum semester,

        @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
        String description
) {}
