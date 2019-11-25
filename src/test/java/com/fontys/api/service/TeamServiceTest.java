package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class TeamServiceTest {

    private TeamService teamService;

    private UserRepository userRepositoryMock;
    private TeamRepository teamRepositoryMock;

    @BeforeEach
    void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        teamRepositoryMock = Mockito.mock(TeamRepository.class);
        teamService = new TeamService(teamRepositoryMock, userRepositoryMock);
    }

    @Test
    void createTeamShouldReturnTeam()
    {
        Team teamExpected = new Team(1,"Team One");
        Team team = new Team("Team One");
        Mockito.when(teamRepositoryMock.save(Mockito.any(Team.class))).thenReturn(teamExpected);

        assertEquals(teamExpected, teamService.createTeam("Team One"));
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).save(team);
    }

    @Test
    void getAllTeamsShouldReturnTeamList() {
        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team("Team One"));
        teamList.add(new Team("Team Two"));
        Mockito.when(teamRepositoryMock.findAll()).thenReturn(teamList);
        assertEquals(teamList, teamService.getAllTeams(null, null));
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findAll();
    }

    @Test
    void getAllTeamsShouldReturnByName() {
        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team("Team One"));
        teamList.add(new Team("Team Two"));
        Mockito.when(teamRepositoryMock.findAll()).thenReturn(teamList);
        Mockito.when(teamRepositoryMock.findAllByNameContains("Team One")).thenReturn(teamList.subList(0, 1));
        Mockito.when(teamRepositoryMock.findAllByNameContains("Team Two")).thenReturn(teamList.subList(1, 2));
        assertEquals(1, teamService.getAllTeams(null, "Team One").size());
        assertEquals("Team Two", teamService.getAllTeams(null, "Team Two").get(0).getName());
        Mockito.verify(teamRepositoryMock, Mockito.times(0)).findAll();
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findAllByNameContains("Team One");
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findAllByNameContains("Team Two");
    }

    @Test
    void deleteTeamShouldReturnDeletedString() {
        Team t = new Team(1, "Team One");
        Mockito.when(teamRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(t));
        teamService.deleteTeam(t.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findById(t.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).delete(t);
    }

    @Test
    void deleteTeamShouldReturnErrorString() {
        assertEquals("Team does not exist", teamService.deleteTeam(1));
    }

    @Test
    void addUserToTeamShouldReturnUserString() {
        User u = new User(1, "UUID1");
        Mockito.when(userRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(u));
        Team t = new Team(1, "Team One");
        Mockito.when(teamRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(t));
        assertEquals("User " + u.getId() + " added to team " + t.getName(), teamService.addUserToTeam(t.getId(), u.getId()));
        t.getUsers().add(u);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).findById(u.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findById(t.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).save(t);
    }

    @Test
    void addUserToTeamShouldReturnErrorString() {
        assertEquals("User or team does not exist", teamService.addUserToTeam(1, 1));
    }

    @Test
    void addUserToTeamInvalidUserAlreadyInTeam() {
        User user = new User(1, "User 1");
        List<User> players = new ArrayList<>();
        players.add(user);

        Team team = new Team(1, "The A Team", players);

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team));

        String message = this.teamService.addUserToTeam(team.getId(), user.getId());

        assertEquals("User is already added to the team!", message);
        Mockito.verify(userRepositoryMock, times(1)).findById(1);
        Mockito.verify(teamRepositoryMock, times(1)).findById(1);
        Mockito.verify(teamRepositoryMock, times(0)).save(team);
    }

    @Test
    void updateTeamValid() {
        Team team = new Team(1, "testTeam");
        Team result = new Team(1, "testTeamNew");

        when(teamRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(team));
        when(teamRepositoryMock.save(Mockito.any(Team.class))).thenReturn(result);
        Team updatedTeamOut;

        try {
            updatedTeamOut = this.teamService.updateTeam(
                    1, "testTeamNew"
            );
        } catch (InvalidAttributeValueException e) {
            fail();
            return;
        }

        Assert.assertEquals(result, updatedTeamOut);
        Mockito.verify(teamRepositoryMock, times(1)).save(result);
    }

    @Test
    void updateTeamInValid() {
        Team team = new Team(1, "testTeam");
        Team result = new Team(1, "testTeamNew");

        when(teamRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
        when(teamRepositoryMock.save(Mockito.any(Team.class))).thenReturn(result);
        Team updatedTeamOut;

        try {
            updatedTeamOut = this.teamService.updateTeam(
                    2, "testTeamNew"
            );
            fail();
        } catch (InvalidAttributeValueException e) {
            Assert.assertEquals("Team doesn't exist. Please add a valid team.", e.getMessage());
        }
    }

    @Test
    void removeUserFromTeamValid()
    {
        User user = new User(1, "User 1");
        List<User> players = new ArrayList<>();
        players.add(user);

        Team team = new Team(1, "The A Team", players);
        Team teamNew = new Team(1, "The A Team", new ArrayList<>());

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team));

        String message = this.teamService.removeUserFromTeam(team.getId(), user.getId());

        assertEquals("User 1 is removed from team The A Team", message);
        Mockito.verify(userRepositoryMock, times(1)).findById(1);
        Mockito.verify(teamRepositoryMock, times(1)).findById(1);
        Mockito.verify(teamRepositoryMock, times(1)).save(teamNew);
    }

    @Test
    void removeUserFromTeamInvalidUserId()
    {
        User user = new User(1, "User 1");
        List<User> players = new ArrayList<>();
        players.add(user);

        Team team = new Team(1, "The A Team", players);
        Team teamNew = new Team(1, "The A Team", new ArrayList<>());

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team));

        String message = this.teamService.removeUserFromTeam(team.getId(), 2);

        assertEquals("User does not exist", message);
        Mockito.verify(userRepositoryMock, times(1)).findById(2);
        Mockito.verify(teamRepositoryMock, times(1)).findById(1);
        Mockito.verify(teamRepositoryMock, times(0)).save(teamNew);
    }

    @Test
    void removeUserFromTeamInvalidTeamId()
    {
        User user = new User(1, "User 1");
        List<User> players = new ArrayList<>();
        players.add(user);

        Team team = new Team(1, "The A Team", players);
        Team teamNew = new Team(1, "The A Team", new ArrayList<>());

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        String message = this.teamService.removeUserFromTeam(2, user.getId());

        assertEquals("Team does not exist", message);
        Mockito.verify(userRepositoryMock, times(1)).findById(1);
        Mockito.verify(teamRepositoryMock, times(1)).findById(2);
        Mockito.verify(teamRepositoryMock, times(0)).save(teamNew);
    }

    @Test
    void removeUserFromTeamInvalidUserNotInTeam()
    {
        User user = new User(1, "User 1");
        User user2 = new User(2, "User 2");
        List<User> players = new ArrayList<>();
        players.add(user);

        Team team = new Team(1, "The A Team", players);
        Team teamNew = new Team(1, "The A Team", new ArrayList<>());

        when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(user2));
        when(teamRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(team));

        String message = this.teamService.removeUserFromTeam(team.getId(), user2.getId());

        assertEquals("User is not added to the team!", message);
        Mockito.verify(userRepositoryMock, times(1)).findById(2);
        Mockito.verify(teamRepositoryMock, times(1)).findById(1);
        Mockito.verify(teamRepositoryMock, times(0)).save(teamNew);
    }
}
