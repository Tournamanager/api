package com.fontys.api.service;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp()
    {
        userRepositoryMock = mock(UserRepository.class);
        userService = new UserService(userRepositoryMock);
    }

    @Test
    void createUserShouldReturnUser() {
        User u = new User("UUID");
        when(userRepositoryMock.save(any(User.class))).thenReturn(u);
        assertEquals(u,userService.createUser("UUID"));
        verify(userRepositoryMock, times(1)).save(u);
    }

    @Test
    void deleteTeamShouldReturnDeletedString() {
        User uWithUuid = new User(1,"UUID");
        Mockito.when(userRepositoryMock.findByUuid(Mockito.any(String.class))).thenReturn(Optional.of(uWithUuid));
        User uNoUuid = new User(1,"");
        Mockito.when(userRepositoryMock.save(Mockito.any(User.class))).thenReturn(uNoUuid);
        assertEquals("User " + uWithUuid.getId() + " deleted", userService.deleteUser(uWithUuid.getUuid()));
        Mockito.verify(userRepositoryMock, Mockito.times(1)).findByUuid("UUID");
        Mockito.verify(userRepositoryMock, Mockito.times(1)).save(uNoUuid);
    }

    @Test
    void deleteTeamShouldReturnErrorString() {
        assertEquals("User does not exist",userService.deleteUser(""));
    }
}
