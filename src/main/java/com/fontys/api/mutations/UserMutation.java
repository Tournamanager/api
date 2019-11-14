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

    public User createUser(String uuid) { return userService.createUser(uuid); }

    public String deleteUser(String uuid) {
        return userService.deleteUser(uuid);
    }

    public User updateUser(Integer id, String uuid) { return userService.updateUser(id, uuid); }
}
