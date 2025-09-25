package com.website.surotec_academy.controller;

import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService UserService;

    public UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @Operation(summary = "Obtener un usuario por su ID", description = "Devuelve los detalles de un usuario específico basado en su identificador único.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente", content = @Content(schema = @Schema(implementation = UserEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping
    public List<UserEntity> getUsers() {
        return UserService.getAllUsers();
    }


    @PostMapping
    public Void addUser() {
        return null;
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
