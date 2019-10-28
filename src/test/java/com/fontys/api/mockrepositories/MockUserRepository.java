package com.fontys.api.mockrepositories;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;

public class MockUserRepository extends MockJPARepository<User, Long> implements UserRepository
{
}
