package com.website.surotec_academy.controller;

import com.website.surotec_academy.domain.dto.roles.RolesDto;
import com.website.surotec_academy.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor // Inyección de dependencias por constructor con Lombok
public class RolesController {

    private final RolesService rolesService;

    /**
     * Endpoint para obtener todos los roles.
     * @return Una lista de todos los roles y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<RolesDto>> getAllRoles() {
        List<RolesDto> roles = rolesService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    /**
     * Endpoint para obtener un rol por su ID.
     * @param id El ID del rol a buscar.
     * @return El rol encontrado y un estado HTTP 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<RolesDto> getRoleById(@PathVariable Long id) {
        RolesDto role = rolesService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    /**
     * Endpoint para crear un nuevo rol.
     * @param rolesDto El DTO con la información del rol a crear.
     * @return El rol recién creado y un estado HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<RolesDto> createRoles(@RequestBody RolesDto rolesDto) {
        RolesDto createdRole = rolesService.createRoles(rolesDto);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar un rol existente.
     * @param id El ID del rol a actualizar.
     * @param rolesDto El DTO con los nuevos datos para el rol.
     * @return El rol actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<RolesDto> updateRole(@PathVariable Long id, @RequestBody RolesDto rolesDto) {
        RolesDto updatedRole = rolesService.updateRoles(id, rolesDto);
        return ResponseEntity.ok(updatedRole);
    }

    /**
     * Endpoint para eliminar un rol por su ID.
     * @param id El ID del rol a eliminar.
     * @return Una respuesta sin contenido y un estado HTTP 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        rolesService.deleteRoles(id);
        return ResponseEntity.noContent().build();
    }
}