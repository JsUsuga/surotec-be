package com.website.surotec_academy.domain.dto.donation;

public record DonationDto(
        Long id,
        String description,
        Integer amount,
        Long idUser,
        String userName
) {}