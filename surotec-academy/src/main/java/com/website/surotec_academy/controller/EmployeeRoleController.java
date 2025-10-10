package com.website.surotec_academy.controller;

import com.website.surotec_academy.service.EmployeeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees/{employeeId}/roles")
@RequiredArgsConstructor
public class EmployeeRoleController {

    private final EmployeeRoleService employeeRoleService;

    @PostMapping("/{roleId}")
    public ResponseEntity<String> assignRole(
            @PathVariable Long employeeId,
            @PathVariable Long roleId) {
        employeeRoleService.assignRoleToEmployee(employeeId, roleId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role assigned successfully.");
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> removeRole(
            @PathVariable Long employeeId,
            @PathVariable Long roleId) {
        employeeRoleService.removeRoleFromEmployee(employeeId, roleId);
        return ResponseEntity.noContent().build();
    }
}