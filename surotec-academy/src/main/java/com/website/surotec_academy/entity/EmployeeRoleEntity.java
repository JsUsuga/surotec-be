package com.website.surotec_academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRoleEntity {

    @EmbeddedId
    private EmployeeRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId") // Vincula con el campo 'employeeId' de la clave
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId") // Vincula con el campo 'roleId' de la clave
    @JoinColumn(name = "role_id")
    private RolesEntity role;
}