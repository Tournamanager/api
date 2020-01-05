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

    @ManyToOne
    private Team teamHome;

    @ManyToOne
    private Team teamAway;

    @ManyToOne
    private Team winner;

    private Date date;

    private Integer homeScore;
    private Integer awayScore;

    @ManyToOne
    private Tournament tournament;

    public Match(Team teamHome, Team teamAway, Date date, Tournament tournament)
    {
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.date = date;
        this.tournament = tournament;
    }
}
