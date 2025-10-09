package com.website.surotec_academy.entity;

import com.website.surotec_academy.enums.SemesterEnum;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cohort")
public class CohortEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SemesterEnum semester = SemesterEnum.FIRST;

    @Column(length = 200)
    private String description;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    public CohortEntity() {
    }

    public CohortEntity(Long id, Integer year, SemesterEnum semester, String description,
                        LocalDateTime dateCreate, LocalDateTime dateUpdate) {
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.description = description;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SemesterEnum getSemester() {
        return semester;
    }

    public void setSemester(SemesterEnum semester) {
        this.semester = semester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Cohort{" +
                "id=" + id +
                ", year=" + year +
                ", semester=" + semester +
                ", description='" + description + '\'' +
                '}';
    }
}
