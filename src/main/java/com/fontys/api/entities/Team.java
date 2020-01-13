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
@DiscriminatorValue("Team")
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;

    @ManyToMany
    private List<User> users;

    @ManyToMany
    private List<Tournament> tournaments;

    public Team(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.tournaments = new ArrayList<>();
    }

    public Team(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
        this.tournaments = new ArrayList<>();
    }

    public Team(Integer id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
}
