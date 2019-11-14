package com.fontys.api.service;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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

    @Test
    void updateUserValid() {
        User userOld = new User(1, "U1");
        User userNew = new User(1, "User1");

        when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.of(userOld));
        when(userRepositoryMock.save(any(User.class))).thenReturn(userNew);

        User newUserActual = null;
        try
        {
            newUserActual = this.userService.updateUser(userOld.getId(), userNew.getUuid());
        }
        catch (InvalidAttributeValueException e)
        {
            fail();
        }

        assertEquals(userNew, newUserActual);
        Mockito.verify(userRepositoryMock, times(1)).findById(userOld.getId());
        Mockito.verify(userRepositoryMock, times(1)).save(userNew);
    }

    @Test
    void updateUserInValidUserId() {
        User userOld = new User(1, "U1");
        User userNew = new User(1, "User1");

        when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
        when(userRepositoryMock.save(any(User.class))).thenReturn(userNew);

        User newUserActual = null;
        try
        {
            newUserActual = this.userService.updateUser(2, userNew.getUuid());
            fail();
        }
        catch (InvalidAttributeValueException e)
        {
            assertEquals("The user wasn't found!", e.getMessage());
        }

        Mockito.verify(userRepositoryMock, times(1)).findById(2);
        Mockito.verify(userRepositoryMock, times(0)).save(userNew);
    }
}
