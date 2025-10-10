package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.entity.*;
import com.website.surotec_academy.repository.*;
import com.website.surotec_academy.service.EmployeeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;
    private final EmployeeRoleRepository employeeRoleRepository;

    @Override
    public void assignRoleToEmployee(Long employeeId, Long roleId) {
        // Busca las entidades principales o lanza un error si no existen
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        RolesEntity role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        // Crea la clave compuesta para la nueva relación
        EmployeeRoleId assignmentId = new EmployeeRoleId(employeeId, roleId);

        // Verifica si la asignación ya existe
        if (employeeRoleRepository.existsById(assignmentId)) {
            throw new RuntimeException("Role is already assigned to this employee.");
        }

        // Crea y guarda la nueva entidad de relación
        EmployeeRoleEntity newAssignment = new EmployeeRoleEntity(assignmentId, employee, role);
        employeeRoleRepository.save(newAssignment);
    }

    @Override
    public void removeRoleFromEmployee(Long employeeId, Long roleId) {
        EmployeeRoleId assignmentId = new EmployeeRoleId(employeeId, roleId);

        // Verifica que la asignación exista antes de borrarla
        if (!employeeRoleRepository.existsById(assignmentId)) {
            throw new RuntimeException("This role assignment does not exist.");
        }

        employeeRoleRepository.deleteById(assignmentId);
    }
}