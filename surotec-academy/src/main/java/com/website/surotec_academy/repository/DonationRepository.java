package com.website.surotec_academy.repository;

import com.website.surotec_academy.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    List<DonationEntity> findByUserId(Long userId);
}