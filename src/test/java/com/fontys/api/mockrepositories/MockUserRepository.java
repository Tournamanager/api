package com.fontys.api.mockrepositories;

import com.fontys.api.entities.User;
import com.fontys.api.repositories.UserRepository;

public class MockUserRepository extends MockJPARepository<User, Integer> implements UserRepository
{
    private User getUserByUUIDReturnValue = null;

    public void setGetUserByUUIDReturnValue(User getUserByUUIDReturnValue)
    {
        this.getUserByUUIDReturnValue = getUserByUUIDReturnValue;
    }

    public String getUserByUUIDCalledWithParameter = null;

    public String getGetUserByUUIDCalledWithParameter()
    {
        return this.getUserByUUIDCalledWithParameter;
    }

    @Override
    public User getUserByUUID(String UUID)
    {
        this.getUserByUUIDCalledWithParameter = UUID;
        return this.getUserByUUIDReturnValue;
    }
}
