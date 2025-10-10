package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.entity.*;
import com.website.surotec_academy.repository.*;
import com.website.surotec_academy.service.EmployeeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRoleServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;
    private final EmployeeRoleRepository employeeRoleRepository;

    @Override
    public void assignRoleToEmployee(Long employeeId, Long roleId) {
        log.info("Assigning role with id: {} to employee with id: {}", roleId, employeeId);

        try {
            // Busca las entidades principales o lanza un error si no existen
            EmployeeEntity employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> {
                        log.warn("Employee not found with id: {}", employeeId);
                        return new RuntimeException("Employee not found with id: " + employeeId);
                    });

            RolesEntity role = rolesRepository.findById(roleId)
                    .orElseThrow(() -> {
                        log.warn("Role not found with id: {}", roleId);
                        return new RuntimeException("Role not found with id: " + roleId);
                    });

            // Crea la clave compuesta para la nueva relación
            EmployeeRoleId assignmentId = new EmployeeRoleId(employeeId, roleId);

            // Verifica si la asignación ya existe
            if (employeeRoleRepository.existsById(assignmentId)) {
                log.warn("Attempted to assign a role already assigned to employee with id: {}", employeeId);
                throw new RuntimeException("Role is already assigned to this employee.");
            }

            // Crea y guarda la nueva entidad de relación
            EmployeeRoleEntity newAssignment = new EmployeeRoleEntity(assignmentId, employee, role);
            employeeRoleRepository.save(newAssignment);

            log.info("Role with id: {} successfully assigned to employee with id: {}", roleId, employeeId);

        } catch (Exception e) {
            log.error("Error assigning role with id: {} to employee with id: {}", roleId, employeeId, e);
            throw new RuntimeException("Error assigning role to employee: " + e.getMessage(), e);
        }
    }

    @Override
    public void removeRoleFromEmployee(Long employeeId, Long roleId) {
        log.info("Removing role with id: {} from employee with id: {}", roleId, employeeId);

        try {
            EmployeeRoleId assignmentId = new EmployeeRoleId(employeeId, roleId);

            // Verifica que la asignación exista antes de borrarla
            if (!employeeRoleRepository.existsById(assignmentId)) {
                log.warn("Attempted to remove a non-existent role assignment: employeeId={}, roleId={}", employeeId, roleId);
                throw new RuntimeException("This role assignment does not exist.");
            }

            employeeRoleRepository.deleteById(assignmentId);
            log.info("Role with id: {} successfully removed from employee with id: {}", roleId, employeeId);

        } catch (Exception e) {
            log.error("Error removing role with id: {} from employee with id: {}", roleId, employeeId, e);
            throw new RuntimeException("Error removing role from employee: " + e.getMessage(), e);
        }
    }
}