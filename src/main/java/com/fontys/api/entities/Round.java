package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private List<Match> matches;

    private int teamCount;

    public Round(List<Match> matches, int teamCount) {
        this.matches = matches;
        this.teamCount = teamCount;
    }

    public Round(List<Match> matches) {
        this.matches = matches;
    }
}
