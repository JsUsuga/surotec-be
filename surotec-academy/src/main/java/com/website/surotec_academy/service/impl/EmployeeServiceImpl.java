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

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        try {
            return EmployeeMapper.toDtoList(employeeRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving students: " + e.getMessage(), e);
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
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid employee ID");
            }
            return EmployeeMapper.toDto(employeeRepository.findById(id).orElse(null));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving employee: " + e.getMessage(), e);
        }
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        try {
            EmployeeEntity entity = EmployeeMapper.toEntity(employeeDto);

            Long userId = employeeDto.userDto().idUser();
            UserEntity user;
            if (userId != null && userRepository.existsById(userId)) {
                user = userRepository.findById(userId).orElseThrow();
            } else {
                user = UserMapper.toEntity(employeeDto.userDto());
                user.setId(null);
                user.setDateCreate(LocalDateTime.now());
                user.setDateUpdate(LocalDateTime.now());
                user = userRepository.save(user);
            }
            entity.setUser(user);

            LocalDateTime now = LocalDateTime.now();
            entity.setId(null);
            entity.setHireDate(now);

            EmployeeEntity saved = employeeRepository.save(entity);
            return EmployeeMapper.toDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error creating employee: " + e.getMessage(), e);
        }
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        try {
            EmployeeEntity existingEmployee = employeeRepository.findById(id).orElse(null);
            if (existingEmployee == null) {
                throw new RuntimeException("Employee not found with id: " + id);
            }
            EmployeeMapper.updateEntityFromDto(existingEmployee, employeeDto);
            EmployeeEntity updatedEmployee = employeeRepository.save(existingEmployee);
            return EmployeeMapper.toDto(updatedEmployee);
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        try {
            EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
            if (employee == null) {
                throw new RuntimeException("Employee not found with id: " + id);
            }
            employee.setHireDate(LocalDateTime.now());
            employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting student: " + e.getMessage(), e);
        }
    }

}

