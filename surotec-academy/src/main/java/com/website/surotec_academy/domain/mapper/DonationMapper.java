package com.website.surotec_academy.domain.mapper;

import com.website.surotec_academy.domain.dto.donation.DonationDto;
import com.website.surotec_academy.entity.DonationEntity;
import org.springframework.stereotype.Component;

@Component
public class DonationMapper {

    public DonationDto toDonationDto(DonationEntity entity) {
        if (entity == null) {
            return null;
        }

        return new DonationDto(
                entity.getId(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                entity.getUser() != null ? entity.getUser().getUsername() : null
        );
    }
}