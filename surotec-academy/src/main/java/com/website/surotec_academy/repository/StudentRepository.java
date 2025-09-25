package com.website.surotec_academy.repository;

import com.website.surotec_academy.classification.StudentStatus;
import com.website.surotec_academy.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    List<StudentEntity> findByStatus(StudentStatus status);


}
