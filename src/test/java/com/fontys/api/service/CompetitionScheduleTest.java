package com.fontys.api.service;

import com.fontys.api.entities.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CompetitionScheduleTest
{
    @Test
    public void getScheduleTest() {
        Team team1 = new Team("The A Team");
        Team team2 = new Team("The B Team");
        Team team3 = new Team("The C Team");
        Team team4 = new Team("The D Team");
        Team team5 = new Team("The E Team");
        Team team6 = new Team("The F Team");
        Team team7 = new Team("The G Team");
        Team team8 = new Team("The H Team");
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        teams.add(team5);
        teams.add(team6);
        teams.add(team7);
        teams.add(team8);
        Tournament tournament = new Tournament(1, "tournament1", "The first tournament", new User("1"), 8, teams, new ArrayList<>());
        CompetitionGenerator competitionGenerator = new CompetitionGenerator();
        CompetitionSchedule tournamentSchedule = competitionGenerator.generateSchedule(tournament);
        List<Match> matches = tournamentSchedule.getMatches();
        for(Match match: matches) {
            System.out.println(match.getTeamHome().getName() + " vs " + match.getTeamAway().getName());
        }
        assertEquals(56, matches.size());
    }

    @Test
    public void getScheduleTest1() {
        Team team1 = new Team("The A Team");
        Team team2 = new Team("The B Team");
        Team team3 = new Team("The C Team");
        Team team4 = new Team("The D Team");
        Team team5 = new Team("The E Team");
        Team team6 = new Team("The F Team");
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        teams.add(team5);
        teams.add(team6);

        Tournament tournament = new Tournament(1, "tournament1", "The first tournament", new User("1"), 8, teams, new ArrayList<>());
        CompetitionGenerator competitionGenerator = new CompetitionGenerator();
        CompetitionSchedule tournamentSchedule = competitionGenerator.generateSchedule(tournament);
        List<Match> matches = tournamentSchedule.getMatches();

        assertEquals(30, matches.size());
    }

    @Test
    public void getScheduleTest2() {
        Team team1 = new Team("The A Team");
        Team team2 = new Team("The B Team");
        Team team3 = new Team("The C Team");
        Team team4 = new Team("The D Team");
        Team team5 = new Team("The E Team");
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        teams.add(team5);

        Tournament tournament = new Tournament(1, "tournament1", "The first tournament", new User("1"), 8, teams, new ArrayList<>());
        CompetitionGenerator competitionGenerator = new CompetitionGenerator();
        CompetitionSchedule tournamentSchedule = competitionGenerator.generateSchedule(tournament);
        List<Match> matches = tournamentSchedule.getMatches();

        assertEquals(20, matches.size());
    }

    @Test
    public void getScheduleTest3() {
        Team team1 = new Team("The A Team");
        Team team2 = new Team("The B Team");
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);


        Tournament tournament = new Tournament(1, "tournament1", "The first tournament", new User("1"), 8, teams, new ArrayList<>());
        CompetitionGenerator competitionGenerator = new CompetitionGenerator();
        CompetitionSchedule tournamentSchedule = competitionGenerator.generateSchedule(tournament);
        List<Match> matches = tournamentSchedule.getMatches();

        assertEquals(2, matches.size());
    }
}
