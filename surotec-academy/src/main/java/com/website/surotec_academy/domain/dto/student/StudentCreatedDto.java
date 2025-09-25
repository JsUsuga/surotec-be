package com.website.surotec_academy.domain.dto.student;

import lombok.Builder;

@Builder
public record StudentCreatedDto(Long idStudent, Long idUser) {}
