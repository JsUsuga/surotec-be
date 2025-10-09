package com.website.surotec_academy.repository;

import com.website.surotec_academy.entity.AcademyProjectEntity;
import com.website.surotec_academy.enums.AcademyProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademyProjectRepository extends JpaRepository<AcademyProjectEntity, Long> {

    List<AcademyProjectEntity> findByEmployee_Id(Long employeeId);

    List<AcademyProjectEntity> findByStatus(AcademyProjectStatus status);

    List<AcademyProjectEntity> findByTitleContainingIgnoreCase(String title);
}