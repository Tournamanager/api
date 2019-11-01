package com.fontys.api.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Tournament
{
    @GeneratedValue @Id
    private Integer id;
    private String name;
    private String description;
    @OneToOne
    private User user;
    private int numberOfTeams;


    public Tournament(String name, String description, User user, int numberOfTeams)
    {
        this.name = name;
        this.description = description;
        this.user = user;
        this.numberOfTeams = numberOfTeams;
    }

    public Tournament(Integer id, String name, String description, User user, int numberOfTeams)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.numberOfTeams = numberOfTeams;
    }
}
