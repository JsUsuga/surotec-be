package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.employee.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
}
