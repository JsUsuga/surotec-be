package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.request.request.UserDto;
import com.website.surotec_academy.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> getAllUsers();
    UserDto createUser(UserDto UserDto);
    void updateUser(UserDto UserDto);
    void deleteUser(int id);


}
