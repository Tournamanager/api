package com.fontys.api.service;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String UUID) { return userRepository.save(new User(UUID)); }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<User> getUser(Integer id) { return userRepository.findById(id); }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUUID(String UUID)
    {
        //ToDo: Write option to get user by UUID
        return Optional.empty();
    }
}
