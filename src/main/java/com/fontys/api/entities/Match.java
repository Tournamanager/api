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

    public Match(Team teamHome, Team teamAway, Date date)
    {
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.date = date;
    }
}
