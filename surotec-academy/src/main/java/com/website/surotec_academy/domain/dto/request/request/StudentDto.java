package com.website.surotec_academy.domain.dto.request.request;

import com.website.surotec_academy.classification.StudentStatus;

public record StudentDto(
        Integer id,
        String firstName,
        String lastName,
        String username,
        String email,
        StudentStatus status,
        String dateCreate,
        String dateUpdate
) {}