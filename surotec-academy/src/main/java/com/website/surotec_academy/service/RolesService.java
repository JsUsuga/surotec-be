package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.roles.RolesDto;
import java.util.List;

public interface RolesService { // Nombre de la interfaz cambiado

    List<RolesDto> getAllRoles();

    RolesDto getRoleById(Long id); // El método sigue en singular porque devuelve un solo rol

    RolesDto createRoles(RolesDto rolesDto); // El parámetro ahora es RolesDto

    RolesDto updateRoles(Long id, RolesDto rolesDto);

    void deleteRoles(Long id);
}