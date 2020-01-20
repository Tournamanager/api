package com.fontys.api.service;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Team;
import com.fontys.api.entities.Tournament;
import com.fontys.api.repositories.MatchRepository;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MatchServiceTest
{
    private MatchService matchService;

    private RoundService roundServiceMock;
    private MatchRepository matchRepositoryMock;
    private TeamRepository teamRepositoryMock;
    private TournamentRepository tournamentRepositoryMock;

    @BeforeEach
    void setUp() {
        matchRepositoryMock = mock(MatchRepository.class);
        teamRepositoryMock = mock(TeamRepository.class);
        tournamentRepositoryMock = mock(TournamentRepository.class);
        roundServiceMock = mock(RoundService.class);

        matchService = new MatchService(matchRepositoryMock, teamRepositoryMock, tournamentRepositoryMock,roundServiceMock);
    }

    @Test
    void createMatchValid()
    {
        Team team1 = new Team(1, "The A Team");
        Team team2 = new Team(2, "The B Team");
        Tournament tournament = new Tournament(1, "Tournament 1", "Tournament 1", null, 8,"competition");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, 1);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Match match = new Match(team1, team2, calendar.getTime());
        Match expectedMatch = new Match(1, team1, team2, null, calendar.getTime(), null);

        Mockito.when(teamRepositoryMock.findById(1)).thenReturn(Optional.of(team1));
        Mockito.when(teamRepositoryMock.findById(2)).thenReturn(Optional.of(team2));

        Mockito.when(matchRepositoryMock.save(Mockito.any(Match.class))).thenReturn(expectedMatch);

        Mockito.when(tournamentRepositoryMock.findById(1)).thenReturn(Optional.of(tournament));

        Match actualMatch = null;
        try
        {
            actualMatch = this.matchService.createMatch(match.getTeamHome().getId(), match.getTeamAway().getId(), formatter.format(match.getDate()), tournament.getId());
        }
        catch (ParseException | InvalidAttributeValueException e)
        {
            fail();
        }

        assertEquals(expectedMatch, actualMatch);
        verify(teamRepositoryMock, Mockito.times(1)).findById(1);
        verify(teamRepositoryMock, Mockito.times(1)).findById(2);
        verify(matchRepositoryMock, Mockito.times(1)).save(match);
    }

    @Test
    void createMatchInvalidDateFormat()
    {
        Team team1 = new Team(1, "The A Team");
        Team team2 = new Team(2, "The B Team");
        Tournament tournament = new Tournament(1, "Tournament 1", "Tournament 1", null, 8,"competition");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, -1);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Match match = new Match(team1, team2, calendar.getTime());
        Match expectedMatch = new Match(1, team1, team2, null, calendar.getTime(), null);

        Mockito.when(teamRepositoryMock.findById(1)).thenReturn(Optional.of(team1));
        Mockito.when(teamRepositoryMock.findById(2)).thenReturn(Optional.of(team2));

        Mockito.when(tournamentRepositoryMock.findById(1)).thenReturn(Optional.of(tournament));

        Match actualMatch = null;
        try
        {
            actualMatch = this.matchService.createMatch(match.getTeamHome().getId(), match.getTeamAway().getId(), "12-12-2020", tournament.getId());
            fail();
        }
        catch (ParseException | InvalidAttributeValueException e)
        {
            assertEquals("Unparseable date: \"12-12-2020\"", e.getMessage());
        }
    }
}
