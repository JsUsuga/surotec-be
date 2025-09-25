package com.website.surotec_academy.entity;


import com.website.surotec_academy.enums.StudentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "student")
public class StudentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus status = StudentStatus.ACTIVE;
    @Column (name = "date_create")
    private LocalDateTime dateCreate;
    @Column (name = "date_update")
    private LocalDateTime dateUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    public StudentEntity() {
    }

    public StudentEntity(Long id, StudentStatus status, LocalDateTime dateCreate, LocalDateTime dateUpdate,  UserEntity user) {
        this.id = id;
        this.status = status;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
        this.user  = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //Método toString
    @Override
    public String toString() {
        return super.toString() + " Student{" +
                "status=" + status +
                '}';
    }
}
