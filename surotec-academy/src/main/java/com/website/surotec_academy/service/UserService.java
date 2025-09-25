package com.website.surotec_academy.service;

import com.website.surotec_academy.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> getAllUsers();
    Void createUser(UserEntity UserDto);
    Void updateUser(UserEntity UserDto);
    Void deleteUser(int id);


}
