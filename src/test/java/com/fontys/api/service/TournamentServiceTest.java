package com.fontys.api.service;

        import com.fontys.api.entities.Tournament;
        import com.fontys.api.entities.User;
        import com.fontys.api.repositories.TournamentRepository;
        import com.fontys.api.repositories.UserRepository;
        import org.junit.Before;
        import org.junit.Test;
        import org.mockito.Mockito;

        import javax.naming.directory.InvalidAttributeValueException;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;

        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.fail;
        import static org.mockito.Mockito.*;

public class TournamentServiceTest
{
    private TournamentRepository tournamentRepository;
    private UserRepository userRepository;
    private TournamentService tournamentService;

    private User user1;
    private User user2;
    private User user3;

    private Tournament tournament1;
    private Tournament tournament2;
    private Tournament tournament3;
    private Tournament tournament4;
    private Tournament tournament5;
    private Tournament tournament6;

    @Before
    public void setUp()
    {
        tournamentRepository = mock(TournamentRepository.class);
        userRepository = mock(UserRepository.class);
        tournamentService = new TournamentService(tournamentRepository, userRepository);

        user1 = new User(1, "TEST");
        user2 = new User(2, "TEST");
        user3 = new User(3, "Test");
        tournament1 = new Tournament("Tournament 1", "Tournament For Testing", user1, 4);
        tournament2 = new Tournament("Tournament 2", "Tournament For Testing", user1, 8);
        tournament3 = new Tournament("Tournament 3", "Tournament For Testing", user1, 64);
        tournament4 = new Tournament("Tournament 4", "Tournament For Testing", user2, 4);
        tournament5 = new Tournament("Tournament 5", "Tournament For Testing", user2, 8);
        tournament6 = new Tournament("Tournament 6", "Tournament For Testing", user2, 64);
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

    @Test
    public void getAllTournaments()
    {
        List<Tournament> expectedTournaments = new ArrayList<>();
        expectedTournaments.add(tournament1);
        expectedTournaments.add(tournament2);
        expectedTournaments.add(tournament3);
        expectedTournaments.add(tournament4);
        expectedTournaments.add(tournament5);
        expectedTournaments.add(tournament6);

        when(tournamentRepository.findAll()).thenReturn(expectedTournaments);

        List<Tournament> actualTournaments = null;
        try
        {
            actualTournaments = tournamentService.tournaments(null);
        }
        catch (InvalidAttributeValueException e)
        {
            fail();
        }

        assertEquals(expectedTournaments, actualTournaments);
        Mockito.verify(tournamentRepository, times(1)).findAll();
    }

    @Test
    public void getAllTournamentsByOwnerId()
    {
        List<Tournament> expectedTournaments = new ArrayList<>();
        expectedTournaments.add(tournament1);
        expectedTournaments.add(tournament2);
        expectedTournaments.add(tournament3);

        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user1));
        when(tournamentRepository.findByOwner(Mockito.any(User.class))).thenReturn(expectedTournaments);

        List<Tournament> actualTournaments = null;

        try
        {
            actualTournaments = tournamentService.tournaments(user1.getId());
        }
        catch (InvalidAttributeValueException e)
        {
            fail();
        }

        assertEquals(expectedTournaments, actualTournaments);
        verify(tournamentRepository, times(1)).findByOwner(user1);
        verify(userRepository, times(1)).findById(user1.getId());
    }

    @Test
    public void getAllTournamentsByOwnerIdNoTournaments()
    {
        List<Tournament> expectedTournaments = new ArrayList<>();

        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user3));
        when(tournamentRepository.findByOwner(Mockito.any(User.class))).thenReturn(expectedTournaments);

        List<Tournament> actualTournaments = null;

        try
        {
            actualTournaments = tournamentService.tournaments(user3.getId());
        }
        catch (InvalidAttributeValueException e)
        {
            fail();
        }

        assertEquals(expectedTournaments, actualTournaments);
        verify(tournamentRepository, times(1)).findByOwner(user3);
        verify(userRepository, times(1)).findById(user3.getId());
    }

    @Test
    public void getAllTournamentsWithInvallidUserId()
    {
        List<Tournament> expectedTournaments = new ArrayList<>();

        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        when(tournamentRepository.findByOwner(Mockito.any(User.class))).thenReturn(expectedTournaments);

        List<Tournament> actualTournaments = null;

        String errorMessage = "An error occurred while loading the tournament. The user was not found! Please try again.";

        try
        {
            actualTournaments = tournamentService.tournaments(4);
            fail();
        }
        catch (InvalidAttributeValueException e)
        {
            assertEquals(errorMessage, e.getMessage());
        }
    }
}
