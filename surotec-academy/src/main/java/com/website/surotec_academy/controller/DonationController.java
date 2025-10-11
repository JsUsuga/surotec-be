package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.donation.DonationCreateDto;
import com.website.surotec_academy.domain.dto.donation.DonationCreatedDto;
import com.website.surotec_academy.domain.dto.donation.DonationDto;
import com.website.surotec_academy.domain.mapper.DonationMapper;
import com.website.surotec_academy.entity.DonationEntity;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.DonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
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

    @Operation(summary = "Obtener todas las donaciones", description = "Devuelve una lista de todas las donaciones registradas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = DonationDto.class)))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DonationDto>> getAllDonations() {
        List<DonationEntity> donations = donationService.getAllDonations();
        List<DonationDto> dtoList = donations.stream()
                .map(donationMapper::toDonationDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }


    @Operation(summary = "Obtener una donación por ID", description = "Devuelve los detalles de una donación específica.")
    @ApiResponse(responseCode = "200", description = "Donación encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = DonationDto.class)))
    @ApiResponse(responseCode = "404", description = "Donación no encontrada")
    @GetMapping(value = "/{idDonation}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonationDto> getDonationById(@PathVariable("idDonation") Long id) {
        return donationService.getDonationById(id)
                .map(donation -> ResponseEntity.ok(donationMapper.toDonationDto(donation)))
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Crear una donación", description = "Crea una nueva donación asociada a un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Donación creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DonationCreatedDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonationCreatedDto> createDonation(@RequestBody DonationCreateDto createDto) {
        UserEntity user = userRepository.findById(createDto.idUser())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DonationEntity donation = new DonationEntity();
        donation.setDescription(createDto.description());
        donation.setAmount(createDto.amount());
        donation.setUser(user);
        donation.setId(null);

        DonationEntity saved = donationService.createDonation(donation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DonationCreatedDto
                        .builder()
                        .idDonation(saved.getId())
                        .idUser(user.getId())
                        .build());
    }


    @Operation(summary = "Eliminar una donación", description = "Elimina una donación existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Donación eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Donación no encontrada", content = @Content)
    })
    @DeleteMapping("/{idDonation}")
    public ResponseEntity<Void> deleteDonation(@PathVariable("idDonation") Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}
