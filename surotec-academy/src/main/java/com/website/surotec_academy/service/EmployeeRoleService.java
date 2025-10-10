package com.website.surotec_academy.service;

public interface EmployeeRoleService {
    void assignRoleToEmployee(Long employeeId, Long roleId);
    void removeRoleFromEmployee(Long employeeId, Long roleId);
}