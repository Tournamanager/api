package com.fontys.api.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fontys.api.entities.User;
import com.fontys.api.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserMutation implements GraphQLMutationResolver
{
    private final UserService userService;

    public UserMutation(UserService userService) { this.userService = userService; }

    public User createUser(long id) { return userService.createUser(); }
}
