package com.veezy.todoapp.service;

import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.model.User;
import com.veezy.todoapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1).build();
    }

    @Test
    void getAlreadyExistingUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        User foundUser = userService.getUser(1);
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void getNonExistingUser() {
        when(userRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userService.getUser(2));
    }
}