package com.website.surotec_academy.domain.dto.donation;

public record DonationDto(
        Long id,
        String description,
        Double amount,
        Long idUser,
        String userName
) {}