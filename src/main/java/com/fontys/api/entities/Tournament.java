package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tournament
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String description;
    @OneToOne
    private User owner;
    private int numberOfTeams;
    @ManyToMany
    private List<Team> teams;

    @OneToMany(mappedBy = "tournament")
    private List<Match> matches;

    @OneToOne
    private CompetitionSchedule schedule;

    private String method = "competition";

    public Tournament(String name, String description, User owner, Integer numberOfTeams, List<Team> teams,
                      List<Match> matches)
    {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.numberOfTeams = numberOfTeams;
        this.teams = teams;
        this.matches = matches;
    }

    public Tournament(String name, String description, User owner, Integer numberOfTeams)
    {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.numberOfTeams = numberOfTeams;
        this.matches = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

    public Tournament(Integer id, String name, String description, User owner, Integer numberOfTeams)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.numberOfTeams = numberOfTeams;
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public Tournament(Integer id, String name, String description, User owner, Integer numberOfTeams, List<Team> teams,
                      List<Match> matches)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.numberOfTeams = numberOfTeams;
        this.teams = teams;
        this.matches = matches;
    }
}
