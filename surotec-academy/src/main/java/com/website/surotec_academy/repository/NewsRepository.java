package com.website.surotec_academy.repository;


import com.website.surotec_academy.entity.NewsEntity;
import com.website.surotec_academy.enums.NewsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {

    // Buscar todas las noticias creadas por un empleado
    List<NewsEntity> findByEmployeeId(Integer employeeId);

    // Buscar todas las noticias con un determinado estado (DRAFT, PUBLISHED, ARCHIVED)
    List<NewsEntity> findByStatus(NewsStatus status);
}
