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
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        mockUserRepository = new MockUserRepository2();
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
        assertEquals("Team One", teamService.getTeam(t.getId(), null).get().getName());
    }

    @Test
    void deleteTeamShouldReturnNoTeams() {
        Team t = teamService.createTeam("Team One");
        teamService.deleteTeam(t.getId());
        assertEquals(0,teamService.getAllTeams().size());
    }

    @Test
    void addUserToTeamShouldReturnUserString() {
        User u = userService.createUser();
        Team t = teamService.createTeam("Team One");
        assertEquals("User " + u.getId() + " added to team " + t.getName(), teamService.addUserToTeam(t.getId(), u.getId()));
    }

    @Test
    void addUserToTeamShouldReturnOneUser() {
        User u = userService.createUser();
        Team t = teamService.createTeam("Team One");
        teamService.addUserToTeam(t.getId(), u.getId());
        assertEquals(1,teamService.getTeam(t.getId(), null).get().getUsers().size());
    }
}
