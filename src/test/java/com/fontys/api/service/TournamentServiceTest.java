package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.naming.directory.InvalidAttributeValueException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class TournamentServiceTest
{
    private TournamentRepository tournamentRepository;
    private UserRepository userRepository;
    private TournamentService tournamentService;

    @Before
    public void setUp()
    {
        tournamentRepository = mock(TournamentRepository.class);
        userRepository = mock(UserRepository.class);
        tournamentService = new TournamentService(tournamentRepository, userRepository);
    }

    @Test
    public void createTournamentValid()
    {
        createTournamentTestValid("testTournament1", "Tournament for testing 1", 1, 4);
    }

    @Test
    public void createTournamentNumberOfTeamsInvalid()
    {
        createTournamentTestInvalid("testTournament2", "Tournament for testing 2", 2, -1,
                                    "A tournament must be created for at least 2 teams. Number of teams provided was -1." +
                                    " Please change the value and try again.");
    }

    @Test
    public void createTournamentUserIdInvalid()
    {
        createTournamentTestInvalid("testTournament3", "Tournament for testing 3", -1, 16,
                                    "Something went wrong while creating the tournament. Please try again.");
    }

    @Test
    public void createTournamentNameEmptyStringInvalid()
    {
        createTournamentTestInvalid("", "Tournament for testing 4", 1, 16,
                                    "The tournament name can't be empty. Please give your tournament a name and try again.");
    }

    @Test
    public void createTournamentNameBlankStringInvalid()
    {
        createTournamentTestInvalid(" ", "Tournament for testing 5", 1, 16,
                                    "The tournament name can't be empty. Please give your tournament a name and try again.");
    }

    private void createTournamentTestValid(String name, String description, Integer ownerId, int numberOfTeams)
    {
        User user = new User(ownerId, "test");
        Tournament tournament = new Tournament(name, description, user, numberOfTeams);

        when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
        when(tournamentRepository.save(Mockito.any(Tournament.class))).thenReturn(tournament);
        Tournament actualTournamentOut = null;

        try
        {
            actualTournamentOut = this.tournamentService.createTournament(
                    name, description, user.getId(), numberOfTeams);
        }
        catch (InvalidAttributeValueException e)
        {
            fail();
            return;
        }

        assertEquals(tournament, actualTournamentOut);
        Mockito.verify(userRepository, times(1)).findById(user.getId());
        Mockito.verify(tournamentRepository, times(1)).save(tournament);
    }

    private void createTournamentTestInvalid(String name, String description, Integer ownerId, int numberOfTeams, String errorMessage)
    {
        User user = new User(ownerId, "test");
        Tournament tournament = new Tournament(name, description, user, numberOfTeams);

        when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
        when(tournamentRepository.save(Mockito.any(Tournament.class))).thenReturn(tournament);
        Tournament actualTournamentOut = null;
        try
        {
            actualTournamentOut = this.tournamentService.createTournament(
                    name, description, user.getId(), numberOfTeams);
            fail();
        }
        catch (InvalidAttributeValueException e)
        {
            assertEquals(errorMessage, e.getMessage());
        }
    }
}
