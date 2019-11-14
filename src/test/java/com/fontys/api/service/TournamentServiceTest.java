package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class TournamentServiceTest {
    private TournamentRepository tournamentRepositoryMock;
    private UserRepository userRepositoryMock;
    private TeamRepository teamRepositoryMock;

    private TournamentService tournamentService;

    @BeforeEach
    void setUp() {
        tournamentRepositoryMock = mock(TournamentRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        teamRepositoryMock = mock(TeamRepository.class);

        tournamentService = new TournamentService(tournamentRepositoryMock, userRepositoryMock, teamRepositoryMock);
    }

    @Test
    void deleteTournamentShouldReturnDeletedString() {
        Tournament t = new Tournament(1, "testTournament", null, new User("uuid"), 2, new ArrayList<>());
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
        Tournament tournament = new Tournament(1, "testTournament", "description", user, 2, new ArrayList<>());
        Tournament result = new Tournament(1, "testTournamentNew", "descriptionNew", user, 4, new ArrayList<>());

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

        Tournament tournament = new Tournament(1, "testTournament", "description", user, 2, new ArrayList<>());
        Tournament result = new Tournament(1, "testTournamentNew", "descriptionNew", user1, 4, new ArrayList<>());

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
    public void addTeamToTournamentValid()
    {
        User user = new User(1, "User 1");
        Team team = new Team(1, "The A Team");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament 1", user, 4, new ArrayList<>());

        List<Team> teams = new ArrayList<>();
        teams.add(team);
        Tournament tournamentOut = new Tournament(1, "Tournament1", "Tournament 1", user, 4, teams);

        when(tournamentRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(tournament));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team));

        String response = this.tournamentService.addTeamToTournament(tournament.getId(), team.getId());

        assertEquals("Team The A Team added to tournament Tournament1", response);
        Mockito.verify(teamRepositoryMock, times(1)).findById(1);
        Mockito.verify(tournamentRepositoryMock, times(1)).findById(1);
        Mockito.verify(tournamentRepositoryMock, times(1)).save(tournamentOut);
    }

    @Test
    public void addTeamToTournamentInvalidTeamId()
    {
        User user = new User(1, "User 1");
        Team team = new Team(1, "The A Team");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament 1", user, 4, new ArrayList<>());

        List<Team> teams = new ArrayList<>();
        teams.add(team);
        Tournament tournamentOut = new Tournament(1, "Tournament1", "Tournament 1", user, 4, teams);

        when(tournamentRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(tournament));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        String response = this.tournamentService.addTeamToTournament(tournament.getId(), 2);

        assertEquals("The team does not exist", response);
        Mockito.verify(teamRepositoryMock, times(1)).findById(2);
        Mockito.verify(tournamentRepositoryMock, times(1)).findById(1);
        Mockito.verify(tournamentRepositoryMock, times(0)).save(tournamentOut);
    }

    @Test
    public void addTeamToTournamentInvalidTournamentId()
    {
        User user = new User(1, "User 1");
        Team team = new Team(1, "The A Team");
        Tournament tournament = new Tournament(1, "Tournament1", "Tournament 1", user, 4, new ArrayList<>());

        List<Team> teams = new ArrayList<>();
        teams.add(team);
        Tournament tournamentOut = new Tournament(1, "Tournament1", "Tournament 1", user, 4, teams);

        when(tournamentRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team));

        String response = this.tournamentService.addTeamToTournament(2, team.getId());

        assertEquals("The tournament does not exist", response);
        Mockito.verify(teamRepositoryMock, times(1)).findById(1);
        Mockito.verify(tournamentRepositoryMock, times(1)).findById(2);
        Mockito.verify(tournamentRepositoryMock, times(0)).save(tournamentOut);
    }

    @Test
    public void addTeamToTournamentInvalidTeamAlreadyInTournament()
    {
        User user = new User(1, "User 1");
        Team team = new Team(1, "The A Team");

        List<Team> teams = new ArrayList<>();
        teams.add(team);

        Tournament tournament = new Tournament(1, "Tournament1", "Tournament 1", user, 4, teams);

        when(tournamentRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(tournament));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team));

        String response = this.tournamentService.addTeamToTournament(tournament.getId(), team.getId());

        assertEquals("The team already joined the tournament!", response);
        Mockito.verify(teamRepositoryMock, times(1)).findById(team.getId());
        Mockito.verify(tournamentRepositoryMock, times(1)).findById(tournament.getId());
        Mockito.verify(tournamentRepositoryMock, times(0)).save(tournament);
    }

    @Test
    public void addTeamToTournamentInvalidTeamLimitReached()
    {
        User user = new User(1, "User 1");
        Team team1 = new Team(1, "The A Team");
        Team team2 = new Team(2, "The B Team");
        Team team3 = new Team(3, "The C Team");
        Team team4 = new Team(4, "The D Team");
        Team team5 = new Team(5, "The E Team");


        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);

        Tournament tournament = new Tournament(1, "Tournament1", "Tournament 1", user, 4, teams);

        when(tournamentRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(tournament));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team5));

        String response = this.tournamentService.addTeamToTournament(tournament.getId(), team5.getId());

        assertEquals("The tournament is currently filled with teams!", response);
        Mockito.verify(teamRepositoryMock, times(1)).findById(team5.getId());
        Mockito.verify(tournamentRepositoryMock, times(1)).findById(tournament.getId());
        Mockito.verify(tournamentRepositoryMock, times(0)).save(tournament);
    }

    @Test
    public void generateMatchesValid()
    {
        assertEquals(4, Math.ceil(Math.log(16)/Math.log(2)),0);
        assertEquals(7, Math.ceil(Math.log(128)/Math.log(2)),0);
        assertEquals(7, Math.ceil(Math.log(127)/Math.log(2)),0);
        assertEquals(7, Math.ceil(Math.log(65)/Math.log(2)),0);
        assertNotEquals(7, Math.ceil(Math.log(64)/Math.log(2)),0);
        assertNotEquals(7, Math.ceil(Math.log(63)/Math.log(2)),0);

        assertEquals(1, (int) Math.pow(2, 0));

        int numberOfTeams = 4;
        int numberOfRounds = (int) Math.ceil(Math.log(numberOfTeams)/Math.log(2));
        int numberOfMatches = 0;
        for(int i = 0; i < numberOfRounds - 1; i++)
        {
            numberOfMatches += (int) Math.pow(2, i);
        }
        numberOfMatches += Math.floorDiv(numberOfTeams, 2);
        assertEquals(3, numberOfMatches);

        numberOfTeams = 8;
        numberOfRounds = (int) Math.ceil(Math.log(numberOfTeams)/Math.log(2));
        numberOfMatches = 0;
        for(int i = 0; i < numberOfRounds - 1; i++)
        {
            numberOfMatches += (int) Math.pow(2, i);
        }
        numberOfMatches += Math.floorDiv(numberOfTeams, 2);
        assertEquals(7, numberOfMatches);

        numberOfTeams = 7;
        numberOfRounds = (int) Math.ceil(Math.log(numberOfTeams)/Math.log(2));
        numberOfMatches = 0;
        for(int i = 0; i < numberOfRounds - 1; i++)
        {
            numberOfMatches += (int) Math.pow(2, i);
        }
        numberOfMatches += Math.floorDiv(numberOfTeams, 2);
        assertEquals(6, numberOfMatches);

        numberOfTeams = 5;
        numberOfRounds = (int) Math.ceil(Math.log(numberOfTeams)/Math.log(2));
        numberOfMatches = 0;
        for(int i = 0; i < numberOfRounds - 1; i++)
        {
            numberOfMatches += (int) Math.pow(2, i);
        }
        numberOfMatches += Math.floorDiv(numberOfTeams, 2);
        assertEquals(4, numberOfMatches);
    }
}
