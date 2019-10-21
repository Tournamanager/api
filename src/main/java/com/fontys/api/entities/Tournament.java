package com.fontys.api.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Tournament
{
    @GeneratedValue @Id
    private Long id;
    private String name;
    private String description;
    @OneToOne
    private User user;
    private int numberOfTeams;

    public Tournament() {}

    public Tournament(String name, String description, User user, int numberOfTeams)
    {
        this.name = name;
        this.description = description;
        this.user = user;
        this.numberOfTeams = numberOfTeams;
    }

    public Tournament(long id, String name, String description, User user, int numberOfTeams)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.numberOfTeams = numberOfTeams;
    }
}
