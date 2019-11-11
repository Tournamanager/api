package com.fontys.api.service;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Team;
import com.fontys.api.repositories.MatchRepository;
import com.fontys.api.repositories.TeamRepository;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchService
{
    private MatchRepository matchRepository;
    private TeamRepository teamRepository;

    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository)
    {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public Match createMatch(Integer teamHomeId, Integer teamAwayId, String date)
    throws ParseException, InvalidAttributeValueException
    {
        Team teamHome = teamRepository.findById(teamHomeId).orElse(null);
        Team teamAway = teamRepository.findById(teamAwayId).orElse(null);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormatter.parse(date);

        validateTeam(teamHome);
        validateTeam(teamAway);
        validateTeams(teamHome, teamAway);

        Match match = new Match(teamHome, teamAway, date1);

        return matchRepository.save(match);
    }

    private void validateTeam(Team team) throws InvalidAttributeValueException
    {
        if(team == null)
        {
            throw new InvalidAttributeValueException("An error occured while creating the match. A team was not found in the database. Please try again.");
        }
    }

    private void validateTeams(Team team1, Team team2) throws InvalidAttributeValueException
    {
        if(team1.equals(team2))
        {
            throw new InvalidAttributeValueException("An error occured while creating the match. Tried to create a match with the same team. Please change one of the teams and try again.");
        }
    }
}
