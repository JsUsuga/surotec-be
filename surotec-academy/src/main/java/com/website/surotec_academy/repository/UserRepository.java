package com.website.surotec_academy.repository;

import com.website.surotec_academy.entity.StudentEntity;
import com.website.surotec_academy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {



}
