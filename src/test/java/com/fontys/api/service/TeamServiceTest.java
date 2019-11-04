package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.entities.User;
import com.fontys.api.mockrepositories.MockTeamRepository;
import com.fontys.api.mockrepositories.MockUserRepository2;
import com.fontys.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamServiceTest {

    private TeamService teamService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserRepository mockUserRepository = new MockUserRepository2();
        teamService = new TeamService(new MockTeamRepository(), mockUserRepository);
        userService = new UserService(mockUserRepository);
    }

    @Test
    void createTeamShouldReturnTeamOne() {
        assertEquals("Team One",teamService.createTeam("Team One").getName());
    }

    @Test
    void getAllTeamsShouldReturnOneTeam() {
        teamService.createTeam("Team One");
        assertEquals(1, teamService.getAllTeams().size());
    }

    @Test
    void getTeamShouldReturnTeamOne() {
        Team t = teamService.createTeam("Team One");
        assertEquals("Team One", teamService.getTeam(t.getId()).get().getName());
    }

    @Test
    void deleteTeamShouldReturnNoTeams() {
        Team t = teamService.createTeam("Team One");
        teamService.deleteTeam(t.getId());
        assertEquals(0,teamService.getAllTeams().size());
    }

    @Test
    void deleteTeamShouldReturnDeletedString() {
        Team t = teamService.createTeam("Team One");
        assertEquals("Team " + t.getName() + " deleted",teamService.deleteTeam(t.getId()));
    }

    @Test
    void deleteTeamShouldReturnErrorString() {
        assertEquals("Team does not exist",teamService.deleteTeam(1));
    }

    @Test
    void addUserToTeamShouldReturnUserString() {
        User u = userService.createUser("UUID1");
        Team t = teamService.createTeam("Team One");
        assertEquals("User " + u.getId() + " added to team " + t.getName(), teamService.addUserToTeam(t.getId(), u.getId()));
    }

    @Test
    void addUserToTeamShouldReturnOneUser() {
        User u = userService.createUser("UUID1");
        Team t = teamService.createTeam("Team One");
        teamService.addUserToTeam(t.getId(), u.getId());
        assertEquals(1,teamService.getTeam(t.getId()).get().getUsers().size());
    }

    @Test
    void addUserToTeamShouldReturnErrorString() {
        assertEquals("User or team does not exist", teamService.addUserToTeam(1,1));
    }
}
