package com.website.surotec_academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String position;

    @Column(nullable = false, length = 50)
    private String area;

    @CreationTimestamp
    @Column(name = "hire_date", updatable = false)
    private LocalDateTime hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_employee"))
    private UserEntity user;


}