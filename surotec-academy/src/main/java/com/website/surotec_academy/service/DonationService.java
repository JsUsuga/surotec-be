package com.website.surotec_academy.service;

import com.website.surotec_academy.entity.DonationEntity;

import java.util.List;
import java.util.Optional;

public interface DonationService {

    DonationEntity createDonation(DonationEntity donation);

    List<DonationEntity> getAllDonations();

    Optional<DonationEntity> getDonationById(Long id);

    void deleteDonation(Long id);
}