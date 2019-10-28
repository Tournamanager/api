package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
}
