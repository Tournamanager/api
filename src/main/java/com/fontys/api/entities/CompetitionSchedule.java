package com.fontys.api.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CompetitionSchedule
{
    @GeneratedValue @Id
    Integer id;

    @OneToMany
    List<Match> matches = new ArrayList<>();

    public void setMatches(List<Match> matches)
    {
        this.matches = matches;
    }

    public List<Match> getMatches()
    {
        return matches;
    }
}
