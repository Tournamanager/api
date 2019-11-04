package com.fontys.api.service;

import com.fontys.api.mockrepositories.MockUserRepository2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new MockUserRepository2());
    }

    @Test
    void createUserShouldReturnUserUUID() {
        assertEquals("unique",userService.createUser("unique").getUUID());
    }

    @Test
    void createUserShouldReturnOneUser() {
        userService.createUser("unique");
        assertEquals(1,userService.getAllUsers().size());
    }
}
