package com.website.surotec_academy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "donation")
public class DonationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    public DonationEntity() {
    }

    public DonationEntity(Long id, String description, Integer amount, UserEntity user) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return super.toString() + " Donation{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }


}
