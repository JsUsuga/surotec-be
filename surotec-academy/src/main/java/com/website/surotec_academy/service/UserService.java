package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> getAllUsers();
    UserDto createUser(UserDto UserDto);
    UserDto updateUser(Long id, UserDto UserDto);
    void deleteUser(Long id);
    UserEntity getUserById(Long id);
    List<UserDto> getUsersByStatus(String status);
}
