package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.user.UserCreatedDto;
import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.website.surotec_academy.domain.mapper.UserMapper;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtener un usuarios ", description = "Devuelve los usuario.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un usuario por su ID", description = "Devuelve los detalles de un usuario específico basado en su identificador único.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping(value = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserById(@PathVariable("idUser") Long id) {
        return UserMapper.toDto(userService.getUserById(id));
    }

    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema a partir de los datos enviados en formato JSON."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreatedDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserCreatedDto> addUser(
            @RequestBody(
                required = true
            )
            UserDto userDto
    ) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserCreatedDto
                        .builder()
                        .idUser(createdUser.idUser()).build());
    }

    @Operation(
            summary = "Actualizar un usuario existente",
            description = "Actualiza los datos de un usuario existente basado en el ID y la información proporcionada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreatedDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping(
            value = "/{idUser}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserCreatedDto> updateUser(
            @PathVariable("idUser") Long id,
            @RequestBody UserDto userDto
    ) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserCreatedDto
                        .builder()
                        .idUser(updatedUser.idUser())
                        .build());
    }

    @Operation(
            summary = "Eliminar un usuario por su ID",
            description = "Elimina un usuario existente basado en su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteUser(@PathVariable("idUser") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener usuarios por estado",
            description = "Devuelve una lista de usuarios filtrados por su estado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron usuarios con ese estado", content = @Content)
    })
    @GetMapping("/status")
    public List<UserDto> getUserByStatus(@RequestParam("status") String status) {
        return userService.getUsersByStatus(status);
    }
}
