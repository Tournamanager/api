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

    @OneToMany(mappedBy = "round")
    private List<Match> matches;

    @ManyToOne
    private Tournament tournament;

    public Round(List<Match> matches) {
        this.matches = matches;
    }
}