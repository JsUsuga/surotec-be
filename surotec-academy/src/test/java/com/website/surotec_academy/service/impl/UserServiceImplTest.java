package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.user.UserDto;
import com.website.surotec_academy.entity.UserEntity;
import com.website.surotec_academy.enums.DocumentType;
import com.website.surotec_academy.enums.UserStatus;
import com.website.surotec_academy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserEntity userEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity(
                1L,
                DocumentType.CC,
                "123456789",
                "John",
                "Doe",
                "johndoe",
                LocalDate.of(1999, 6, 1),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "password123",
                "john.doe@example.com",
                UserStatus.ACTIVE
        );

        userDto = UserDto.builder()
                .idUser(1L)
                .documentType("CC")
                .documentNumber("123456789")
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .age(25)
                .email("john.doe@example.com")
                .status(UserStatus.ACTIVE)
                .password("password123")
                .dateCreate(LocalDateTime.now())
                .dateUpdate(LocalDateTime.now())
                .build();
    }

    @Test
    void getAllUsers_success() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserEntity> result = userServiceImpl.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserEntity result = userServiceImpl.getUserById(1L);

        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUser_success() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto result = userServiceImpl.createUser(userDto);

        assertNotNull(result);
        assertEquals("John", result.firstName());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void updateUser_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto updatedDto = UserDto.builder()
                .idUser(1L)
                .documentType("CC")
                .documentNumber("123456789")
                .firstName("Johnny")
                .lastName("Doe")
                .username("johndoe")
                .age(25)
                .email("johnny.doe@example.com")
                .status(UserStatus.ACTIVE)
                .password("newpass123")
                .build();

        UserDto result = userServiceImpl.updateUser(1L, updatedDto);

        assertNotNull(result);
        assertEquals("Johnny", result.firstName());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void deleteUser_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        userServiceImpl.deleteUser(1L);

        assertEquals(UserStatus.INACTIVE, userEntity.getStatus());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void getUsersByStatus_success() {
        when(userRepository.findByStatus(UserStatus.ACTIVE)).thenReturn(List.of(userEntity));

        List<UserDto> result = userServiceImpl.getUsersByStatus("ACTIVE");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).firstName());
        verify(userRepository, times(1)).findByStatus(UserStatus.ACTIVE);
    }
}
