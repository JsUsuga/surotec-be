package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.domain.mapper.UserMapper;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.UserStatus;
import com.website.surotec_academy.repository.UserRepository;
import com.website.surotec_academy.service.UserService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("UserServiceImpl initialized with UserRepository");
    }

    @Override
    public List<UserEntity> getAllUsers() {
        log.info("Fetching all users from the database");
        try {
            List<UserEntity> users = userRepository.findAll();
            log.info("Found {} users", users.size());
            return users;
        } catch (Exception e) {
            log.error("Error retrieving users", e);
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        }
    }

    @Override
    public UserEntity getUserById(Long id) {
        log.info("Fetching user with id {}", id);
        try {
            if (id == null || id <= 0) {
                log.warn("Invalid user ID: {}", id);
                throw new IllegalArgumentException("Invalid user ID");
            }
            UserEntity user = userRepository.findById(id).orElse(null);
            if (user == null) {
                log.warn("User not found with id {}", id);
            } else {
                log.info("User found: {} {}", user.getFirstName(), user.getLastName());
            }
            return user;
        } catch (Exception e) {
            log.error("Error retrieving user with id {}", id, e);
            throw new RuntimeException("Error retrieving user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Creating new user: {} {}, email: {}",
                userDto.firstName(), userDto.lastName(), userDto.email());
        try {
            UserEntity entity = UserMapper.toEntity(userDto);
            LocalDateTime now = LocalDateTime.now();
            entity.setId(null);
            entity.setDateCreate(now);
            entity.setDateUpdate(now);

            UserEntity saved = userRepository.save(entity);
            log.info("User created with id {}", saved.getId());
            return UserMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error creating user: {} {}", userDto.firstName(), userDto.lastName(), e);
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        log.info("Updating user with id {}: {} {}, status: {}",
                id, userDto.firstName(), userDto.lastName(), userDto.status());
        try {
            UserEntity existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                log.warn("User not found with id {}", id);
                throw new RuntimeException("User not found with idUser: " + id);
            }
            UserMapper.updateEntityFromDto(existingUser, userDto);
            UserEntity updatedUser = userRepository.save(existingUser);
            log.info("User updated with id {}", updatedUser.getId());
            return UserMapper.toDto(updatedUser);
        } catch (Exception e) {
            log.error("Error updating user with id {}: {} {}",
                    id, userDto.firstName(), userDto.lastName(), e);
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting (inactivating) user with id {}", id);
        try {
            UserEntity user = userRepository.findById(id).orElse(null);
            if (user == null) {
                log.warn("User not found with id {}", id);
                throw new RuntimeException("User not found with idUser: " + id);
            }
            user.setStatus(UserStatus.INACTIVE);
            user.setDateUpdate(LocalDateTime.now());
            userRepository.save(user);
            log.info("User set to INACTIVE: {}", id);
        } catch (Exception e) {
            log.error("Error deleting user with id {}", id, e);
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<UserDto> getUsersByStatus(String status) {
        log.info("Fetching users with status {}", status);
        try {
            List<UserEntity> entities = userRepository.findByStatus(UserStatus.valueOf(status));
            log.info("Found {} users with status {}", entities.size(), status);
            return UserMapper.toDtoList(entities);
        } catch (Exception e) {
            log.error("Error getting users by status {}", status, e);
            throw new RuntimeException("Error getting users by status: " + e.getMessage(), e);
        }
    }
}
