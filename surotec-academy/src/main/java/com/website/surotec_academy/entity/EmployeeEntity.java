package com.website.surotec_academy.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class EmployeeEntity extends UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, length = 50)
    private String position;
    @Column (nullable = false, length = 50)
    private String area;
    @Column (nullable = false, length = 50)
    private LocalDateTime dateCreate;
    @Column (nullable = false, length = 50)
    private LocalDateTime dateUpdate;;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_employee"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee", referencedColumnName = "id", nullable = false)
    private EmployeeEntity employee;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Long id, String position, String area, LocalDateTime dateCreate,
                            LocalDateTime dateUpdate, UserEntity user, EmployeeEntity employee) {
            this.id = id;
            this.position = position;
            this.area = area;
            this.dateCreate = dateCreate;
            this.dateUpdate = dateUpdate;
            this.user = user;
            this.employee = employee;
        }
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    @Override
    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    @Override
    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
