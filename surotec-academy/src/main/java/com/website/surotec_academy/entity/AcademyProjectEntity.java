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

    public AcademyProjectEntity() {}

    // 🔹 Constructor completo (ya corregido)
    public AcademyProjectEntity(Long id, EmployeeEntity employee, String title, String description,
                                String imageUrl, String caption, LocalDateTime publishDate,
                                AcademyProjectStatus status) {
        this.id = id;
        this.employee = employee;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.publishDate = publishDate;
        this.status = status;
    }

    // 🔹 Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EmployeeEntity getEmployee() { return employee; }
    public void setEmployee(EmployeeEntity employee) { this.employee = employee; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public LocalDateTime getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDateTime publishDate) { this.publishDate = publishDate; }

    public AcademyProjectStatus getStatus() { return status; }
    public void setStatus(AcademyProjectStatus status) { this.status = status; }
}

