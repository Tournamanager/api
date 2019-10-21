package com.fontys.api.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fontys.api.entities.User;
import com.fontys.api.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserQuery implements GraphQLQueryResolver
{
    private final UserService userService;

    public UserQuery(UserService userService) { this.userService = userService; }

    public List<User> users(int count) { return this.userService.getAllUsers(); }

    public Optional<User> user(Long id) { return this.userService.getUser(id); }
}
