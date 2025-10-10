package com.website.surotec_academy.domain.dto.AcademyProject;

import com.website.surotec_academy.enums.AcademyProjectStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AcademyProjectDto(
        Long id,
        @NotNull
        Long employeeId,

        @NotNull
        @Size(max = 200)
        String title,

        @NotNull
        String description,

        @Size(max = 255)
        String imageUrl,

        @Size(max = 200)
        String caption,

        LocalDateTime publishDate,

        @NotNull
        AcademyProjectStatus status
) {}
