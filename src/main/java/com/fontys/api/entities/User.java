package com.fontys.api.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User
{
    @GeneratedValue @Id
    private Integer id;

    public User() {}
}
