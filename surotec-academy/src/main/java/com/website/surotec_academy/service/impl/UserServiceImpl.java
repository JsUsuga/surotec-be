package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository UserRepository;
    public UserServiceImpl(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> UserEntitys = UserRepository.findAll();
        return UserEntitys;
    }

    @Override
    public Void createUser(UserEntity UserEntity) {
        return null;
    }

    @Override
    public Void updateUser(UserEntity UserEntity) {
        return null;
    }

    @Override
    public Void deleteUser(int id) {
        return null;
    }
}
