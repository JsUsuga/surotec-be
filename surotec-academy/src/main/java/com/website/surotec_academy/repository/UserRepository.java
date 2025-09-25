package com.website.surotec_academy.repository;

import com.website.surotec_academy.entity.StudentEntity;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    List<UserEntity> findByStatus(UserStatus status);
}
