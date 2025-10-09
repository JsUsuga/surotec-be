package com.website.surotec_academy.domain.dto;

import lombok.Builder;
@Builder
public record AcademyProjectCreatedDto(
        Long id,
        String title
) {}
