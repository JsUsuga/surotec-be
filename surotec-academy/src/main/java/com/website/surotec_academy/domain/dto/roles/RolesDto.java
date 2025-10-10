package com.website.surotec_academy.domain.dto.roles; // Cambiado a 'roles'

import lombok.Builder;

@Builder
public record RolesDto(
        Long id,
        String name,
        String description
) {}