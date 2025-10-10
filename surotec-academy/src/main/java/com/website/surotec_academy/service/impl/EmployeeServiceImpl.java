package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.employee.EmployeeDto;
import com.website.surotec_academy.domain.mapper.EmployeeMapper;
import com.website.surotec_academy.domain.mapper.UserMapper;
import com.website.surotec_academy.entity.EmployeeEntity;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.repository.EmployeeRepository;
import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.EmployeeService;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        log.info("EmployeeServiceImpl initialized with EmployeeRepository and UserRepository");
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        log.info("Fetching all employees");
        try {
            List<EmployeeDto> employees = EmployeeMapper.toDtoList(employeeRepository.findAll());
            log.info("Found {} employees", employees.size());
            return employees;
        } catch (Exception e) {
            log.error("Error retrieving employees", e);
            throw new RuntimeException("Error retrieving employees: " + e.getMessage(), e);
        }
    }

    //    @Override
    //    public List<EmployeeDto> getStudentsByStatus(String status) {
    //        try {
    //            List<StudentEntity> entities = studentRepository.findByStatus(StudentStatus.valueOf(status));
    //            return StudentMapper.toDtoList(entities);
    //        } catch (Exception e) {
    //            throw new RuntimeException("Error getting students by status: " + e.getMessage(), e);
    //        }
    //    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with id {}", id);
        try {
            if (id == null || id <= 0) {
                log.warn("Invalid employee ID: {}", id);
                throw new IllegalArgumentException("Invalid employee ID");
            }
            EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
            if (employee == null) {
                log.warn("Employee not found with id {}", id);
            } else {
                log.info("Employee found: {} {}", employee.getUser().getFirstName(), employee.getUser().getLastName());
            }
            return EmployeeMapper.toDto(employee);
        } catch (Exception e) {
            log.error("Error retrieving employee with id {}", id, e);
            throw new RuntimeException("Error retrieving employee: " + e.getMessage(), e);
        }
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.info("Creating employee for user: {} {}", employeeDto.userDto().firstName(), employeeDto.userDto().lastName());
        try {
            EmployeeEntity entity = EmployeeMapper.toEntity(employeeDto);

            Long userId = employeeDto.userDto().idUser();
            UserEntity user;
            if (userId != null && userRepository.existsById(userId)) {
                user = userRepository.findById(userId).orElseThrow();
                log.info("Existing user linked with id {}", userId);
            } else {
                user = UserMapper.toEntity(employeeDto.userDto());
                user.setId(null);
                LocalDateTime now = LocalDateTime.now();
                user.setDateCreate(now);
                user.setDateUpdate(now);
                user = userRepository.save(user);
                log.info("New user created with id {}", user.getId());
            }
            entity.setUser(user);

            LocalDateTime now = LocalDateTime.now();
            entity.setId(null);
            entity.setHireDate(now);

            EmployeeEntity saved = employeeRepository.save(entity);
            log.info("Employee created with id {}", saved.getId());
            return EmployeeMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error creating employee: {} {}", employeeDto.userDto().firstName(), employeeDto.userDto().lastName(), e);
            throw new RuntimeException("Error creating employee: " + e.getMessage(), e);
        }
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with id {}: {} {}", id, employeeDto.userDto().firstName(), employeeDto.userDto().lastName());
        try {
            EmployeeEntity existingEmployee = employeeRepository.findById(id).orElse(null);
            if (existingEmployee == null) {
                log.warn("Employee not found with id {}", id);
                throw new RuntimeException("Employee not found with id: " + id);
            }
            EmployeeMapper.updateEntityFromDto(existingEmployee, employeeDto);
            EmployeeEntity updatedEmployee = employeeRepository.save(existingEmployee);
            log.info("Employee updated with id {}", updatedEmployee.getId());
            return EmployeeMapper.toDto(updatedEmployee);
        } catch (Exception e) {
            log.error("Error updating employee with id {}: {} {}", id, employeeDto.userDto().firstName(), employeeDto.userDto().lastName(), e);
            throw new RuntimeException("Error updating employee: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting (inactivating) employee with id {}", id);
        try {
            EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
            if (employee == null) {
                log.warn("Employee not found with id {}", id);
                throw new RuntimeException("Employee not found with id: " + id);
            }
            employee.setHireDate(LocalDateTime.now());
            employeeRepository.save(employee);
            log.info("Employee hire date updated to mark as inactive for id {}", id);
        } catch (Exception e) {
            log.error("Error deleting employee with id {}", id, e);
            throw new RuntimeException("Error deleting employee: " + e.getMessage(), e);
        }
    }
}
