package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.entity.DonationEntity;
import com.website.surotec_academy.repository.DonationRepository;
import com.website.surotec_academy.service.DonationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public DonationEntity createDonation(DonationEntity donation) {
        return donationRepository.save(donation);
    }

    @Override
    public List<DonationEntity> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Optional<DonationEntity> getDonationById(Long id) {
        return donationRepository.findById(id);
    }

    @Override
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}