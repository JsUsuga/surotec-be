package com.website.surotec_academy.domain.dto.donation;

import lombok.Builder;

@Builder
public record DonationCreatedDto(
        Long idDonation,
        Long idUser
) {}
