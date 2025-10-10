package com.website.surotec_academy.repository;

import com.website.surotec_academy.entity.EmployeeRoleEntity;
import com.website.surotec_academy.entity.EmployeeRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRoleEntity, EmployeeRoleId> {
}