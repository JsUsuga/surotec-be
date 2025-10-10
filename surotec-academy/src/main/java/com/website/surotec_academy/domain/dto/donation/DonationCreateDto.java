package com.website.surotec_academy.domain.dto.donation;

public record DonationCreateDto(
        String description,
        Integer amount,
        Long idUser
) {}