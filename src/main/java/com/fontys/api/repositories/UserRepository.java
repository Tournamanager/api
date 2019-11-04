package com.fontys.api.repositories;

import com.fontys.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findUserByUuid(String uuid);
}
