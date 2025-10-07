package com.website.surotec_academy.repository;

import com.website.surotec_academy.enums.EmStatus;
import com.website.surotec_academy.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<StudentEntity, Long> {

    List<EmployeeEntity> findByStatus(EmployeeStatus status);


}
