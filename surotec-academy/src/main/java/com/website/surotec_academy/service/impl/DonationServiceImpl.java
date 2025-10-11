package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.entity.DonationEntity;
import com.website.surotec_academy.repository.DonationRepository;
import com.website.surotec_academy.service.DonationService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private static final Logger log = LoggerFactory.getLogger(DonationServiceImpl.class);

    private final DonationRepository donationRepository;

    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
        log.info("DonationServiceImpl initialized with DonationRepository");
    }

    @Override
    public DonationEntity createDonation(DonationEntity donation) {
        log.info("Creating new donation for userId {}: amount {}", donation.getId(), donation.getAmount());
        try {
            DonationEntity saved = donationRepository.save(donation);
            log.info("Donation created with id {}", saved.getId());
            return saved;
        } catch (Exception e) {
            log.error("Error creating donation for userId {}: amount {}", donation.getId(), donation.getAmount(), e);
            throw new RuntimeException("Error creating donation: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DonationEntity> getAllDonations() {
        log.info("Fetching all donations");
        try {
            List<DonationEntity> donations = donationRepository.findAll();
            log.info("Found {} donations", donations.size());
            return donations;
        } catch (Exception e) {
            log.error("Error fetching all donations", e);
            throw new RuntimeException("Error fetching donations: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<DonationEntity> getDonationById(Long id) {
        log.info("Fetching donation with id {}", id);
        try {
            Optional<DonationEntity> donation = donationRepository.findById(id);
            if (donation.isEmpty()) {
                log.warn("Donation not found with id {}", id);
            } else {
                log.info("Donation found with id {}", id);
            }
            return donation;
        } catch (Exception e) {
            log.error("Error fetching donation with id {}", id, e);
            throw new RuntimeException("Error fetching donation: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteDonation(Long id) {
        log.info("Deleting donation with id {}", id);
        try {
            if (!donationRepository.existsById(id)) {
                log.warn("Donation not found with id {}", id);
                throw new RuntimeException("Donation not found with id: " + id);
            }
            donationRepository.deleteById(id);
            log.info("Donation deleted with id {}", id);
        } catch (Exception e) {
            log.error("Error deleting donation with id {}", id, e);
            throw new RuntimeException("Error deleting donation: " + e.getMessage(), e);
        }
    }
}
