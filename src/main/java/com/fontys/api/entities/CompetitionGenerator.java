package com.fontys.api.entities;

import java.util.ArrayList;
import java.util.List;

public class CompetitionGenerator
{
    public CompetitionSchedule generateSchedule(Tournament tournament)
    {
        CompetitionSchedule schedule = new CompetitionSchedule();
        List<Match> matches = new ArrayList<>();
        for(Team team1: tournament.getTeams()) {
            for(Team team2: tournament.getTeams()) {
                if(team1.equals(team2)) {
                    continue;
                }
                matches.add(new Match(team1, team2));
            }
        }
        schedule.setMatches(matches);
        tournament.setSchedule(schedule);
        return schedule;
    }
}
