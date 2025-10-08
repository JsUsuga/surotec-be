package com.website.surotec_academy.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String position;

    @Column(nullable = false, length = 50)
    private String area;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_employee"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee", referencedColumnName = "id", nullable = true)
    private EmployeeEntity manager; // se cambió employee a manager para evitar confusión

    public EmployeeEntity() {
    }

    public EmployeeEntity(Long id, String position, String area,
                          LocalDateTime dateCreate, LocalDateTime dateUpdate,
                          UserEntity user, EmployeeEntity manager) {
        this.id = id;
        this.position = position;
        this.area = area;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
        this.user = user;
        this.manager = manager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public EmployeeEntity getManager() {
        return manager;
    }

    public void setManager(EmployeeEntity manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return super.toString() + " Employee{" +
                "position='" + position + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}