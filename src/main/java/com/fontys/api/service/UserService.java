package com.fontys.api.service;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User createUser(String uuid) { return userRepository.save(new User(uuid)); }

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
    public Optional<User> getUser(Integer id) { return userRepository.findById(id); }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUuid(String uuid)
    {
        return userRepository.findByUuid(uuid);
    }

    @Transactional
    public User updateUser(Integer id, String uuid)
    {
        return null;
    }
}
