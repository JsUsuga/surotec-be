package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.roles.RolesDto;
import com.website.surotec_academy.domain.mapper.RolesMapper;
import com.website.surotec_academy.entity.RolesEntity;
import com.website.surotec_academy.repository.RolesRepository; // -> Importante: tu repositorio también debe llamarse así
import com.website.surotec_academy.service.RolesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService { // Nombres de clase e interfaz actualizados

    private final RolesRepository rolesRepository; // El repositorio ahora se llama rolesRepository

    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<RolesDto> getAllRoles() {
        try {
            return RolesMapper.toDtoList(rolesRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving roles: " + e.getMessage(), e);
        }
    }

    @Override
    public RolesDto getRoleById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid role ID");
            }
            RolesEntity rolesEntity = rolesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
            return RolesMapper.toDto(rolesEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving role: " + e.getMessage(), e);
        }
    }

    @Override
    public RolesDto createRoles(RolesDto rolesDto) {
        try {
            RolesEntity entity = RolesMapper.toEntity(rolesDto);
            entity.setId(null);

            RolesEntity saved = rolesRepository.save(entity);
            return RolesMapper.toDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error creating role: " + e.getMessage(), e);
        }
    }

    @Override
    public RolesDto updateRoles(Long id, RolesDto rolesDto) {
        try {
            RolesEntity existingRole = rolesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

            RolesMapper.updateEntityFromDto(existingRole, rolesDto);

            RolesEntity updatedRole = rolesRepository.save(existingRole);
            return RolesMapper.toDto(updatedRole);
        } catch (Exception e) {
            throw new RuntimeException("Error updating role: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteRoles(Long id) {
        try {
            if (!rolesRepository.existsById(id)) {
                throw new RuntimeException("Role not found with id: " + id);
            }
            rolesRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting role: " + e.getMessage(), e);
        }
    }
}