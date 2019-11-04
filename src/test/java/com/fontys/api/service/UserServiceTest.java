package com.fontys.api.service;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        userService = new UserService(userRepositoryMock);
    }

    @Test
    void createUserShouldReturnUser() {
        User u = new User("UUID1");
        when(userRepositoryMock.save(any(User.class))).thenReturn(u);
        assertEquals(u,userService.createUser("UUID1"));
        verify(userRepositoryMock, times(1)).save(u);
    }
}
