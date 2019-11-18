package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

class TournamentServiceTest {
    private TournamentRepository tournamentRepositoryMock;
    private UserRepository userRepositoryMock;
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

    @BeforeEach
    public void setUp()
    {
        tournamentRepositoryMock = mock(TournamentRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        tournamentService = new TournamentService(tournamentRepositoryMock, userRepositoryMock);

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
    void getTournamentTestValidId() {
        User user = new User(1, "User 1");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament number 1", user, 4);

        when(tournamentRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(tournament));

        Optional<Tournament> tournamentActual = this.tournamentService.tournament(tournament.getId(), null);

        assertEquals(Optional.of(tournament), tournamentActual);
        Mockito.verify(tournamentRepositoryMock, times(0)).findByName(Mockito.anyString());
        Mockito.verify(tournamentRepositoryMock, times(1)).findById(tournament.getId());
    }

    @Test
    void getTournamentTestValidName() {
        User user = new User(1, "User 1");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament number 1", user, 4);

        when(tournamentRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.of(tournament));

        Optional<Tournament> tournamentActual = this.tournamentService.tournament(null, tournament.getName());

        assertEquals(Optional.of(tournament), tournamentActual);
        Mockito.verify(tournamentRepositoryMock, times(1)).findByName(tournament.getName());
        Mockito.verify(tournamentRepositoryMock, times(0)).findById(Mockito.anyInt());
    }

    @Test
    void getTournamentTestInvalidParametersNull() {
        User user = new User(1, "User 1");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament number 1", user, 4);

        Optional<Tournament> tournamentActual = this.tournamentService.tournament(null, null);

        assertEquals(Optional.empty(), tournamentActual);
        Mockito.verify(tournamentRepositoryMock, times(0)).findByName(Mockito.anyString());
        Mockito.verify(tournamentRepositoryMock, times(0)).findById(Mockito.anyInt());
    }

    @Test
    void getTournamentTestInvalidId() {
        User user = new User(1, "User 1");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament number 1", user, 4);

        when(tournamentRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Optional<Tournament> tournamentActual = this.tournamentService.tournament(2, null);

        assertEquals(Optional.empty(), tournamentActual);
        Mockito.verify(tournamentRepositoryMock, times(0)).findByName(Mockito.anyString());
        Mockito.verify(tournamentRepositoryMock, times(1)).findById(2);
    }

    @Test
    void getTournamentTestInvalidName() {
        User user = new User(1, "User 1");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament number 1", user, 4);

        when(tournamentRepositoryMock.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        Optional<Tournament> tournamentActual = this.tournamentService.tournament(null, "Tournament2");

        assertEquals(Optional.empty(), tournamentActual);
        Mockito.verify(tournamentRepositoryMock, times(1)).findByName("Tournament2");
        Mockito.verify(tournamentRepositoryMock, times(0)).findById(Mockito.anyInt());
    }

    @Test
    void deleteTournamentShouldReturnDeletedString() {
        Tournament t = new Tournament(1, "testTournament", null, new User("uuid"), 2);
        Mockito.when(tournamentRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(t));
        assertEquals("Tournament " + t.getName() + " deleted", tournamentService.deleteTournament(t.getId()));
        Mockito.verify(tournamentRepositoryMock, Mockito.times(1)).findById(t.getId());
        Mockito.verify(tournamentRepositoryMock, Mockito.times(1)).delete(t);
    }

    @Test
    void deleteTournamentShouldReturnErrorString() {
        assertEquals("Tournament does not exist", tournamentService.deleteTournament(1));
    }

    @Test
    void createTournamentValid() {
        createTournamentTestValid("Tournament for testing 1", 1, 4);
    }

    @Test
    void updateTournamentValid() {
        updateTournamentTestValid();
    }

    @Test
    void updateTournamentInValid() {
        updateTournamentTestInValid(
                "The tournament doesn't exist. Please select a tournament and try again.");
    }

    @Test
    void TournamentValidWithoutDescription() {
        createTournamentTestValid("", 2, 2);
    }

    @Test
    void TournamentNumberOfTournamentsInvalid() {
        createTournamentTestInvalid("testTournament", "Tournament for testing 3", 2, -1,
                "A tournament must be created for at least 2 teams. Number of teams provided was -1." +
                        " Please change the value and try again.");
    }

    @Test
    void createTournamentNumberOfTournamentsIs1Invalid()
    {
        createTournamentTestInvalid("testTournament", "Tournament for testing 3", 2, 1,
                                    "A tournament must be created for at least 2 teams. Number of teams provided was 1." +
                                    " Please change the value and try again.");
    }

    @Test
    void TournamentUserIdInvalid() {
        createTournamentTestInvalid("testTournament", "Tournament for testing 4", -1, 16,
                "Something went wrong while creating the tournament. Please try again.");
    }

    @Test
    void TournamentNameEmptyStringInvalid() {
        createTournamentTestInvalid("", "Tournament for testing 5", 1, 16,
                "The tournament name can't be empty. Please give your tournament a name and try again.");
    }

    @Test
    void TournamentNameBlankStringInvalid() {
        createTournamentTestInvalid(" ", "Tournament for testing 6", 1, 16,
                "The tournament name can't be empty. Please give your tournament a name and try again.");
    }

    private void createTournamentTestValid(String description, Integer ownerId, int numberOfTournaments) {
        User user = new User(ownerId, "test");
        Tournament tournament = new Tournament("testTournament", description, user, numberOfTournaments);

        when(userRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
        when(tournamentRepositoryMock.save(Mockito.any(Tournament.class))).thenReturn(tournament);
        Tournament actualTournamentOut;

        try {
            actualTournamentOut = this.tournamentService.createTournament(
                    "testTournament", description, user.getId(), numberOfTournaments);
        } catch (InvalidAttributeValueException e) {
            fail();
            return;
        }

        assertEquals(tournament, actualTournamentOut);
        Mockito.verify(userRepositoryMock, times(1)).findById(user.getId());
        Mockito.verify(tournamentRepositoryMock, times(1)).save(tournament);
    }

    private void createTournamentTestInvalid(String name, String description, Integer ownerId, int numberOfTournaments, String errorMessage) {
        User user = new User(ownerId, "test");
        Tournament tournament = new Tournament(name, description, user, numberOfTournaments);

        when(userRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
        when(tournamentRepositoryMock.save(Mockito.any(Tournament.class))).thenReturn(tournament);

        try {
            this.tournamentService.createTournament(name, description, user.getId(), numberOfTournaments);
            fail();
        } catch (InvalidAttributeValueException e) {
            assertEquals(errorMessage, e.getMessage());
        }
    }

    private void updateTournamentTestValid() {
        User user = new User(1, "testOwner");
        Tournament tournament = new Tournament(1, "testTournament", "description", user, 2);
        Tournament result = new Tournament(1, "testTournamentNew", "descriptionNew", user, 4);

        when(userRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
        when(tournamentRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(tournament));
        when(tournamentRepositoryMock.save(Mockito.any(Tournament.class))).thenReturn(result);
        Tournament updatedTournamentOut;

        try {
            updatedTournamentOut = this.tournamentService.updateTournament(
                    1, "testTournamentNew", "descriptionNew", user.getId(), 4
            );
        } catch (InvalidAttributeValueException e) {
            fail();
            return;
        }

        assertEquals(result, updatedTournamentOut);
        Mockito.verify(userRepositoryMock, times(1)).findById(user.getId());
        Mockito.verify(tournamentRepositoryMock, times(1)).save(result);
    }

    private void updateTournamentTestInValid(String errorMessage) {
        User user = new User(1, "testOwner");
        User user1 = new User(2, "testOwnerNew");

        Tournament tournament = new Tournament(1, "testTournament", "description", user, 2);
        Tournament result = new Tournament(1, "testTournamentNew", "descriptionNew", user1, 4);

        when(userRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
        when(tournamentRepositoryMock.save(Mockito.any(Tournament.class))).thenReturn(tournament);
        Tournament updatedTournamentOut;

        try {
            updatedTournamentOut = this.tournamentService.updateTournament(
                    2,
                    "testTournamentNew",
                    "descriptionNew",
                    2,
                    4
            );
            fail();
        } catch (InvalidAttributeValueException e) {
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

        when(tournamentRepositoryMock.findAll()).thenReturn(expectedTournaments);

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
        Mockito.verify(tournamentRepositoryMock, times(1)).findAll();
    }

    @Test
    public void getAllTournamentsByOwnerId()
    {
        List<Tournament> expectedTournaments = new ArrayList<>();
        expectedTournaments.add(tournament1);
        expectedTournaments.add(tournament2);
        expectedTournaments.add(tournament3);

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(user1));
        when(tournamentRepositoryMock.findByOwner(Mockito.any(User.class))).thenReturn(expectedTournaments);

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
        verify(tournamentRepositoryMock, times(1)).findByOwner(user1);
        verify(userRepositoryMock, times(1)).findById(user1.getId());
    }

    @Test
    public void getAllTournamentsByOwnerIdNoTournaments()
    {
        List<Tournament> expectedTournaments = new ArrayList<>();

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(user3));
        when(tournamentRepositoryMock.findByOwner(Mockito.any(User.class))).thenReturn(expectedTournaments);

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
        verify(tournamentRepositoryMock, times(1)).findByOwner(user3);
        verify(userRepositoryMock, times(1)).findById(user3.getId());
    }

    @Test
    public void getAllTournamentsWithInvallidUserId()
    {
        List<Tournament> expectedTournaments = new ArrayList<>();

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        when(tournamentRepositoryMock.findByOwner(Mockito.any(User.class))).thenReturn(expectedTournaments);

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
