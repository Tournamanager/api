package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamServiceTest {

    private TeamService teamService;

    private UserRepository userRepositoryMock;
    private TeamRepository teamRepositoryMock;

    @BeforeEach
    void setUp()
    {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        teamRepositoryMock = Mockito.mock(TeamRepository.class);
        teamService = new TeamService(teamRepositoryMock, userRepositoryMock);
    }

    @Test
    void createTeamShouldReturnTeam()
    {
        Team t = new Team("Team One");
        Mockito.when(teamRepositoryMock.save(Mockito.any(Team.class))).thenReturn(t);

        assertEquals(t,teamService.createTeam("Team One"));
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).save(t);
    }

    @Test
    void getAllTeamsShouldReturnTeamList()
    {
        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team("Team One"));
        teamList.add(new Team("Team Two"));
        Mockito.when(teamRepositoryMock.findAll()).thenReturn(teamList);
        assertEquals(teamList, teamService.getAllTeams(null, null));
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findAll();
    }

    @Test
    void getAllTeamsShouldReturnByName()
    {
        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team("Team One"));
        teamList.add(new Team("Team Two"));
        Mockito.when(teamRepositoryMock.findAll()).thenReturn(teamList);
        Mockito.when(teamRepositoryMock.findAllByNameContains("Team One")).thenReturn(teamList.subList(0,1));
        Mockito.when(teamRepositoryMock.findAllByNameContains("Team Two")).thenReturn(teamList.subList(1,2));
        assertEquals(1, teamService.getAllTeams(null, "Team One").size());
        assertEquals("Team Two", teamService.getAllTeams(null, "Team Two").get(0).getName());
        Mockito.verify(teamRepositoryMock, Mockito.times(0)).findAll();
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findAllByNameContains("Team One");
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findAllByNameContains("Team Two");
    }

    @Test
    void deleteTeamShouldReturnDeletedString()
    {
        Team t = new Team(1, "Team One");
        Mockito.when(teamRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(t));
        teamService.deleteTeam(t.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findById(t.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).delete(t);
    }

    @Test
    void deleteTeamShouldReturnErrorString()
    {
        assertEquals("Team does not exist",teamService.deleteTeam(1));
    }

    @Test
    void addUserToTeamShouldReturnUserString()
    {
        User u = new User(1,"UUID1");
        Mockito.when(userRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(u));
        Team t = new Team(1,"Team One");
        Mockito.when(teamRepositoryMock.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(t));
        assertEquals("User " + u.getId() + " added to team " + t.getName(), teamService.addUserToTeam(t.getId(), u.getId()));
        t.getUsers().add(u);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).findById(u.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).findById(t.getId());
        Mockito.verify(teamRepositoryMock, Mockito.times(1)).save(t);
    }

    @Test
    void addUserToTeamShouldReturnErrorString()
    {
        assertEquals("User or team does not exist", teamService.addUserToTeam(1,1));
    }
}
