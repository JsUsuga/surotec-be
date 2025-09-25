package com.website.surotec_academy.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "student")
public class StudentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer status;
    @Column (name = "date_create")
    private String dateCreate;
    @Column (name = "date_update")
    private String dateUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    public StudentEntity() {
    }
    public StudentEntity(Integer id, Integer idUser, Integer status, String dateCreate, String dateUpdate) {
        this.id = id;
        this.status = status;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
    }



    // Getters y setters


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    public String getDateUpdate() {
        return dateUpdate;
    }


    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }


    public String getDateCreate() {
        return dateCreate;
    }


    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
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