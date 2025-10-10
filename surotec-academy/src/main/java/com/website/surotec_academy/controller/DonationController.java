package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.donation.DonationCreateDto;
import com.website.surotec_academy.domain.dto.donation.DonationDto;
import com.website.surotec_academy.domain.mapper.DonationMapper;
import com.website.surotec_academy.entity.DonationEntity;
import com.website.surotec_academy.entity.UserEntity;

import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService donationService;
    private final UserRepository userRepository;
    private final DonationMapper donationMapper;

    public DonationController(DonationService donationService,
                              UserRepository userRepository,
                              DonationMapper donationMapper) {
        this.donationService = donationService;
        this.userRepository = userRepository;
        this.donationMapper = donationMapper;
    }

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(@RequestBody DonationCreateDto createDto) {
        UserEntity user = userRepository.findById(createDto.idUser())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DonationEntity donation = new DonationEntity();
        donation.setDescription(createDto.description());
        donation.setAmount(createDto.amount());
        donation.setUser(user);

        donation.setId(null); // aseguramos que sea nuevo registro

        DonationEntity saved = donationService.createDonation(donation);
        return ResponseEntity.ok(donationMapper.toDonationDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<DonationEntity>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationEntity> getDonationById(@PathVariable Long id) {
        return donationService.getDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}