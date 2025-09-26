package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.domain.mapper.UserMapper;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.UserStatus;
import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        }
    }

    @Override
    public UserEntity getUserById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid user ID");
            }
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            UserEntity entity = UserMapper.toEntity(userDto);

            LocalDateTime now = LocalDateTime.now();

            entity.setId(null);
            entity.setDateCreate(now);
            entity.setDateUpdate(now);

            UserEntity saved = userRepository.save(entity);
            return UserMapper.toDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        try {
            UserEntity existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                throw new RuntimeException("User not found with idUser: " + id);
            }
            UserMapper.updateEntityFromDto(existingUser, userDto);
            UserEntity updatedUser = userRepository.save(existingUser);
            return UserMapper.toDto(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            UserEntity user = userRepository.findById(id).orElse(null);
            if (user == null) {
                throw new RuntimeException("User not found with idUser: " + id);
            }
            user.setStatus(UserStatus.INACTIVE);
            user.setDateUpdate(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UserDto> getUsersByStatus(String status) {
        try {
            List<UserEntity> entities = userRepository.findByStatus(UserStatus.valueOf(status));
            return UserMapper.toDtoList(entities);
        } catch (Exception e) {
            throw new RuntimeException("Error getting users by status: " + e.getMessage(), e);
        }
    }
}
