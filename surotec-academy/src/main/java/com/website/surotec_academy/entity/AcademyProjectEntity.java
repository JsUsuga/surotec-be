package com.website.surotec_academy.entity;

import com.website.surotec_academy.enums.AcademyProjectStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "academy_project")

public class AcademyProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(length = 200)
    private String caption;

    @Column(name = "publish_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime publishDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AcademyProjectStatus status = AcademyProjectStatus.DRAFT;
}

