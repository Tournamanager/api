package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Match
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER)
    private Team teamHome;

    @OneToMany(fetch = FetchType.EAGER)
    private Team teamAway;

    @OneToMany(fetch = FetchType.EAGER)
    private Team winner;

    private Date date;

    public Match(Team teamHome, Team teamAway, Date date)
    {
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.date = date;
    }
}
