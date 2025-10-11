package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.entity.DonationEntity;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.repository.DonationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DonationServiceImplTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private DonationServiceImpl donationService;

    private DonationEntity donationEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Usuario de prueba
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Gabriela");
        userEntity.setLastName("Montilla");
        userEntity.setUsername("gmontilla");
        userEntity.setEmail("gabriela@example.com");

        // Donación de prueba
        donationEntity = new DonationEntity();
        donationEntity.setId(1L);
        donationEntity.setUser(userEntity);
        donationEntity.setAmount(100);
        donationEntity.setDescription("Donación de prueba");
    }

    @Test
    void createDonation() {
        when(donationRepository.save(any(DonationEntity.class))).thenReturn(donationEntity);

        DonationEntity result = donationService.createDonation(donationEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Gabriela", result.getUser().getFirstName());
        assertEquals(100, result.getAmount());
        verify(donationRepository).save(donationEntity);
    }

    @Test
    void getAllDonations() {
        when(donationRepository.findAll()).thenReturn(List.of(donationEntity));

        List<DonationEntity> result = donationService.getAllDonations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Donación de prueba", result.get(0).getDescription());
        verify(donationRepository).findAll();
    }

    @Test
    void getDonationById() {
        when(donationRepository.findById(1L)).thenReturn(Optional.of(donationEntity));

        Optional<DonationEntity> result = donationService.getDonationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("gmontilla", result.get().getUser().getUsername());
        verify(donationRepository).findById(1L);
    }

    @Test
    void deleteDonation() {
        doNothing().when(donationRepository).deleteById(1L);

        donationService.deleteDonation(1L);

        verify(donationRepository).deleteById(1L);
    }
}
