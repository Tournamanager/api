package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.mockrepositories.MockTournamentRepository;
import com.fontys.api.mockrepositories.MockUserRepository;
import org.junit.Before;
import org.junit.Test;

import javax.naming.directory.InvalidAttributeValueException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TournamentServiceTest
{
    private MockTournamentRepository tournamentRepository;
    private MockUserRepository userRepository;
    private TournamentService tournamentService;

    @Before
    public void setUp()
    {
        tournamentRepository = new MockTournamentRepository();
        userRepository = new MockUserRepository();
        tournamentService = new TournamentService(tournamentRepository, userRepository);
    }

    @Test
    public void createTournamentValid()
    {
        createTournamentTestValid(1, "testTournament1", "Tournament for testing 1", 1, 4);
    }

    @Test
    public void createTournamentNumberOfTeamsInvalid()
    {
        createTournamentTestInvalid(2, "testTournament2", "Tournament for testing 2", 1, -1,
                                    "A tournament must be created for at least 2 teams. Number of teams provided was -1." +
                                    " Please change the value and try again.");
    }

    @Test
    public void createTournamentUserIdInvalid()
    {
        createTournamentTestInvalid(3, "testTournament3", "Tournament for testing 3", -1, 16,
                                    "Something went wrong while creating the tournament. Please try again.");
    }

    @Test
    public void createTournamentNameEmptyStringInvalid()
    {
        createTournamentTestInvalid(4, "", "Tournament for testing 4", 1, 16,
                                    "The tournament name can't be empty. Please give your tournament a name and try again.");
    }

    @Test
    public void createTournamentNameBlankStringInvalid()
    {
        createTournamentTestInvalid(5, " ", "Tournament for testing 5", 1, 16,
                                    "The tournament name can't be empty. Please give your tournament a name and try again.");
    }

    private void createTournamentTestValid(Integer id, String name, String description, String UUID, int numberOfTeams)
    {
        User user = new User(UUID);
        Tournament tournamentIn = new Tournament(name, description, user, numberOfTeams);
        Tournament tournamentOut = new Tournament(id, name, description, user, numberOfTeams);

        this.userRepository.setFindByIdReturnValue(user);
        this.tournamentRepository.setSaveReturnValue(tournamentOut);
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
        assertEquals(tournamentOut, actualTournamentOut);

        Tournament actualTournamentIn = this.tournamentRepository.getSaveCalledWithParameters();
        assertEquals(tournamentIn, actualTournamentIn);

        Integer actualUserId = this.userRepository.getFindByIdCalledWithParameter();
        assertEquals(user.getId(), actualUserId);
    }

    private void createTournamentTestInvalid(Integer id, String name, String description, String UUID, int numberOfTeams,
                                             String errorMessage)
    {
        User user = new User(UUID);
        Tournament tournamentIn = new Tournament(name, description, user, numberOfTeams);
        Tournament tournamentOut = new Tournament(id, name, description, user, numberOfTeams);

        this.tournamentRepository.setSaveReturnValue(tournamentOut);
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
