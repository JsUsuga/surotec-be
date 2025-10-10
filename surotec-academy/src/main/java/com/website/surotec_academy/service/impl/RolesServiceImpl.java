package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.roles.RolesDto;
import com.website.surotec_academy.domain.mapper.RolesMapper;
import com.website.surotec_academy.entity.RolesEntity;
import com.website.surotec_academy.repository.RolesRepository; // -> Importante: tu repositorio también debe llamarse así
import com.website.surotec_academy.service.RolesService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService { // Nombres de clase e interfaz actualizados

    private static final Logger log = LoggerFactory.getLogger(RolesServiceImpl.class);

    private final RolesRepository rolesRepository; // El repositorio ahora se llama rolesRepository

    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
        log.info("RolesServiceImpl initialized with RolesRepository");
    }

    @Override
    public List<RolesDto> getAllRoles() {
        log.info("Fetching all roles from the database");
        try {
            List<RolesDto> roles = RolesMapper.toDtoList(rolesRepository.findAll());
            log.info("Found {} roles in total", roles.size());
            return roles;
        } catch (Exception e) {
            log.error("Error retrieving all roles", e);
            throw new RuntimeException("Error retrieving roles: " + e.getMessage(), e);
        }
    }

    @Override
    public RolesDto getRoleById(Long id) {
        log.info("Fetching role with id: {}", id);
        try {
            if (id == null || id <= 0) {
                log.warn("Invalid role ID provided: {}", id);
                throw new IllegalArgumentException("Invalid role ID");
            }

            RolesEntity rolesEntity = rolesRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Role not found with id: {}", id);
                        return new RuntimeException("Role not found with id: " + id);
                    });

            log.info("Role found successfully with id: {}", id);
            return RolesMapper.toDto(rolesEntity);
        } catch (Exception e) {
            log.error("Error retrieving role with id: {}", id, e);
            throw new RuntimeException("Error retrieving role: " + e.getMessage(), e);
        }
    }

    @Override
    public RolesDto createRoles(RolesDto rolesDto) {
        log.info("Creating new role: {}", rolesDto.name());
        try {
            RolesEntity entity = RolesMapper.toEntity(rolesDto);
            entity.setId(null);

            RolesEntity saved = rolesRepository.save(entity);
            log.info("Role created successfully with id: {}", saved.getId());
            return RolesMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error creating role: {}", rolesDto.name(), e);
            throw new RuntimeException("Error creating role: " + e.getMessage(), e);
        }
    }

    @Override
    public RolesDto updateRoles(Long id, RolesDto rolesDto) {
        log.info("Updating role with id: {}, new name: {}", id, rolesDto.name());
        try {
            RolesEntity existingRole = rolesRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Role not found for update with id: {}", id);
                        return new RuntimeException("Role not found with id: " + id);
                    });

            RolesMapper.updateEntityFromDto(existingRole, rolesDto);

            RolesEntity updatedRole = rolesRepository.save(existingRole);
            log.info("Role updated successfully with id: {}", updatedRole.getId());
            return RolesMapper.toDto(updatedRole);
        } catch (Exception e) {
            log.error("Error updating role with id: {}", id, e);
            throw new RuntimeException("Error updating role: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteRoles(Long id) {
        log.info("Deleting role with id: {}", id);
        try {
            if (!rolesRepository.existsById(id)) {
                log.warn("Attempted to delete non-existent role with id: {}", id);
                throw new RuntimeException("Role not found with id: " + id);
            }
            rolesRepository.deleteById(id);
            log.info("Role deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting role with id: {}", id, e);
            throw new RuntimeException("Error deleting role: " + e.getMessage(), e);
        }
    }
}
