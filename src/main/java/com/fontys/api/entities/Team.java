package com.fontys.api.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Team {

    @GeneratedValue @Id
    private Integer id;

    private String name;

    private List<User> users;

    public Team() {}

    public Team(String name) {
        this.name = name;
        this.users = new ArrayList<>();
    }

    public Team(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
    }
}
