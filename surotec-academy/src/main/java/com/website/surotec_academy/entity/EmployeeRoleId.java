package com.website.surotec_academy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRoleId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "role_id")
    private Long roleId;
}