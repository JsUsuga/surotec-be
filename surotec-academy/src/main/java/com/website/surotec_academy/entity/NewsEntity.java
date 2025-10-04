package com.website.surotec_academy.entity;


import com.website.surotec_academy.enums.NewsStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class NewsEntity {

    //Columnas en la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "publish_date", updatable = false)
    private LocalDateTime publishDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NewsStatus status = NewsStatus.DRAFT;

    //Getters y Setters
    public int getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDateTime publishDate) { this.publishDate = publishDate; }

    public NewsStatus getStatus() { return status; }
    public void setStatus(NewsStatus status) { this.status = status; }

}
