package com.fontys.api.service;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Team;
import com.fontys.api.entities.Tournament;
import com.fontys.api.repositories.MatchRepository;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.TournamentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService
{
    private MatchRepository matchRepository;
    private TeamRepository teamRepository;
    private TournamentRepository tournamentRepository;

    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository, TournamentRepository tournamentRepository)
    {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Match createMatch(Integer teamHomeId, Integer teamAwayId, String dateString, Integer tournamentId)
            throws ParseException, InvalidAttributeValueException
    {
        Team teamHome = teamRepository.findById(teamHomeId).orElse(null);
        Team teamAway = teamRepository.findById(teamAwayId).orElse(null);
        Tournament tournament = tournamentRepository.findById(tournamentId).orElse(null);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormatter.parse(dateString);

        validateTeam(teamHome);
        validateTeam(teamAway);
        validateTeams(teamHome, teamAway);
        validateTournament(tournament);
        validateDate(date);

        Match match = new Match(teamHome, teamAway, date, tournament);

        return matchRepository.save(match);
    }

    public Match updateMatch(Integer id, String dateString, Integer winnerId, Integer homeScore, Integer awayScore)
            throws ParseException, InvalidAttributeValueException
    {
        Match match = matchRepository.findById(id).orElse(null);
        validateMatch(match);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormatter.parse(dateString);

        Team winner = validateWinnerInMatch(match, winnerId);

        validateDate(date);

        return matchRepository.save(new Match(id, match.getTeamHome(), match.getTeamAway(), winner, date, homeScore, awayScore, match.getTournament()));
    }

    @Transactional(readOnly = true)
    public List<Match> getAllMatches(@Nullable Integer count) {
        if (count == null) {
            return matchRepository.findAll();
        }
        Pageable pageable = PageRequest.of(0, count, Sort.by("id").descending());
        Page<Match> matches = matchRepository.findAll(pageable);
        return matches.getContent();
    }

    @Transactional(readOnly = true)
    public Optional<Match> getMatch(Integer id) {
        if (id != null)
            return matchRepository.findById(id);
        return Optional.empty();
    }

    private void validateMatch(Match match) throws InvalidAttributeValueException
    {
        if (match == null)
        {
            throw new InvalidAttributeValueException("The match doesn't exist. Please select a match and try again.");
        }
    }

    private Team validateWinnerInMatch(Match match, Integer winnerId) throws InvalidAttributeValueException
    {
        if (match.getTeamHome().getId().equals(winnerId))
        {
            return match.getTeamHome();
        }
        else if (match.getTeamAway().getId().equals(winnerId))
        {
            return match.getTeamAway();
        }
        else{
            throw new InvalidAttributeValueException("The selected winner is not part of this match. Please select a valid team and try again.");
        }
    }

    private void validateTeam(Team team) throws InvalidAttributeValueException
    {
        if(team == null)
        {
            throw new InvalidAttributeValueException("An error occurred while creating the match. A team was not found in the database. Please try again.");
        }
    }

    private void validateTournament(Tournament tournament) throws InvalidAttributeValueException
    {
        if (tournament == null)
        {
            throw new InvalidAttributeValueException("An error occurred while creating the match. The tournament was not found in the database. Please try again");
        }
    }

    private void validateTeams(Team team1, Team team2) throws InvalidAttributeValueException
    {
        if(team1.equals(team2))
        {
            throw new InvalidAttributeValueException("An error occurred while creating the match. Tried to create a match with the same team. Please change one of the teams and try again.");
        }
    }

    private void validateDate(Date date) throws InvalidAttributeValueException
    {
        if(date.compareTo(new Date()) < 0)
        {
            throw new InvalidAttributeValueException("You cannot create a match in the Past. Change the date and try again.");
        }
    }
}
