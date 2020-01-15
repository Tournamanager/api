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

    @OneToOne(fetch = FetchType.EAGER)
    private Team teamHome;

    @OneToOne(fetch = FetchType.EAGER)
    private Team teamAway;

    @OneToOne(fetch = FetchType.EAGER)
    private Team winner;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Round round;

    public Match(Team teamHome, Team teamAway) {
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
