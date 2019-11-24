package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Match
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private Team teamHome;

    @OneToMany(fetch = FetchType.EAGER)
    private Team teamAway;

    @OneToMany(fetch = FetchType.EAGER)
    private Team winner;

    private Date date;

    public Match(String name)
    {
        this.name = name;
    }

    public Match(String name, Team teamHome, Team teamAway)
    {
        this.name = name;
        this.teamHome = teamHome;
        this.teamAway = teamAway;
    }

    public Match(Team teamHome, Team teamAway, Date date)
    {
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.date = date;
    }
}
