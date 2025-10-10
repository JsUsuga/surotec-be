package com.website.surotec_academy.domain.dto.AcademyProject;

import com.website.surotec_academy.enums.AcademyProjectStatus;
import lombok.Builder;

@Builder
public record AcademyProjectCreatedDto(
        Long id,
        String title,
        AcademyProjectStatus status
) {}