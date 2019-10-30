package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;

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
