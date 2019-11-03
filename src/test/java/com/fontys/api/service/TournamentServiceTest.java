package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.mockrepositories.MockTournamentRepository;
import org.junit.Before;
import org.junit.Test;

import javax.naming.directory.InvalidAttributeValueException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TournamentServiceTest
{
    private MockTournamentRepository tournamentRepository;
    private TournamentService tournamentService;

    @Before
    public void setUp()
    {
        tournamentRepository = new MockTournamentRepository();
        tournamentService = new TournamentService(tournamentRepository);
    }

    @Test
    public void createTournamentTests()
    {
        User user = new User();
        createTournamentTestValid(1, "testTournament1", "Tournament for testing 1", user, 4);
        createTournamentTestValid(2, "testTournament2", "Tournament for testing 2", user, 8);
        createTournamentTestInvalid(3, "testTournament3", "Tournament for testing 3", user, -1,
                "A tournament must be created for at least 2 teams. Number of teams provided was -1." +
                " Please change the value and try again.");
        createTournamentTestInvalid(4, "testTournament4", "Tournament for testing 4", null, 16,
                                    "Something went wrong while creating the tournament. Please try again.");
        createTournamentTestInvalid(5, "", "Tournament for testing 5", user, 16,
                                    "The tournament name can't be empty. Please give your tournament a name and try again.");
        createTournamentTestInvalid(6, " ", "Tournament for testing 5", user, 16,
                                    "The tournament name can't be empty. Please give your tournament a name and try again.");
    }

    private void createTournamentTestValid(Integer id, String name, String description, User user, int numberOfTeams)
    {
        Tournament tournamentIn = new Tournament(name, description, user, numberOfTeams);
        Tournament tournamentOut = new Tournament(id, name, description, user, numberOfTeams);

        this.tournamentRepository.setSaveReturnValue(tournamentOut);
        Tournament actualTournamentOut = null;
        try
        {
            actualTournamentOut = this.tournamentService.createTournament(
                    name, description, user, numberOfTeams);
        }
        catch (InvalidAttributeValueException e)
        {
            fail();
            return;
        }
        assertEquals(tournamentOut, actualTournamentOut);

        Tournament actualTournamentIn = this.tournamentRepository.getSaveCalledWithParameters();
        assertEquals(tournamentIn, actualTournamentIn);
    }

    private void createTournamentTestInvalid(Integer id, String name, String description, User user, int numberOfTeams,
                                             String errorMessage)
    {
        Tournament tournamentIn = new Tournament(name, description, user, numberOfTeams);
        Tournament tournamentOut = new Tournament(id, name, description, user, numberOfTeams);

        this.tournamentRepository.setSaveReturnValue(tournamentOut);
        Tournament actualTournamentOut = null;
        try
        {
            actualTournamentOut = this.tournamentService.createTournament(
                    name, description, user, numberOfTeams);
            fail();
        }
        catch (InvalidAttributeValueException e)
        {
            assertEquals(errorMessage, e.getMessage());
        }
    }
}
