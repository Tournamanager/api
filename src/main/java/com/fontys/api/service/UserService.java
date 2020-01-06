package com.fontys.api.service;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    private final UserRepository userRepository;

    UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String uuid) {
        if (userRepository.findByUuid(uuid).isEmpty()) {
            return userRepository.save(new User(uuid));
        }
        return null;
    }

    @Transactional
    public String deleteUser(String uuid) {
        if(!uuid.equals("")) {
            Optional<User> user = userRepository.findByUuid(uuid);
            if (user.isPresent()) {
                user.get().setUuid("");
                User u = userRepository.save(user.get());
                return "User " + u.getId() + " deleted";
            }
        }
        return "User does not exist";
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<User> getUser(@Nullable Integer id, @Nullable String uuid) {
        if (id != null)
            return userRepository.findById(id);
        if (uuid != null)
            return userRepository.findByUuid(uuid);
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUuid(String uuid)
    {
        return userRepository.findByUuid(uuid);
    }
}
