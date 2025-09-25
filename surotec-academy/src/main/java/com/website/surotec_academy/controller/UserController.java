package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.request.request.UserDto;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtener un usuario por su ID", description = "Devuelve los detalles de un usuario específico basado en su identificador único.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getAllUsers();
    }


    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema a partir de los datos enviados en formato JSON."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserDto addUser(
            @RequestBody(
                required = true
            )
            UserDto userDto
    ) {
       return userService.createUser(userDto);
    }

    @PutMapping
    Void updateUser() {
        return null;
    }

    @DeleteMapping
    Void deleteUser() {
        return null;
    }

    // @GetMapping("/status")
    // List<UserDto> getUserByStatus();
}
