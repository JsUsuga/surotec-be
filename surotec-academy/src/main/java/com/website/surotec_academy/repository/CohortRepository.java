package com.website.surotec_academy.repository;

import com.website.surotec_academy.entity.CohortEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortRepository extends JpaRepository<CohortEntity, Long> {
}
