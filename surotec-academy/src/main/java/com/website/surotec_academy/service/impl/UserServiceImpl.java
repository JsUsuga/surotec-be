package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.request.request.UserDto;
import com.website.surotec_academy.domain.mapper.UserMapper;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> UserEntitys = userRepository.findAll();
        return UserEntitys;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity entity = UserMapper.toEntity(userDto);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        entity.setDateCreate(now);
        entity.setDateUpdate(now);

        UserEntity saved = userRepository.save(entity);
       return UserMapper.toDto(saved);
    }

    @Override
    public void updateUser(UserDto userDto) {}

    @Override
    public void deleteUser(int id) {}
}
