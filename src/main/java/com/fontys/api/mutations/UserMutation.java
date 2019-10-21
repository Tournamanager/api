package com.fontys.api.mutations;

import com.fontys.api.entities.User;
import com.fontys.api.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserMutation
{
    private final UserService userService;

    public UserMutation(UserService userService) { this.userService = userService; }

    public User createUser() { return userService.createUser(); }
}
