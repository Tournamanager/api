package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.mockrepositories.MockTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamServiceTest {

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team((long) 0,"Team One"));
        teamList.add(new Team((long) 1,"Team Two"));
        teamService = new TeamService(new MockTeamRepository(teamList));
    }

    @Test
    void getAllTeamsShouldReturnTwoTeams() {
        assertEquals(2, teamService.getAllTeams().size());
    }

    @Test
    void getTeamShouldReturnTeamTwo() {
        assertEquals("Team Two", teamService.getTeam((long) 1).get().getName());
    }

    @Test
    void createTeamShouldReturnATeam() {
        teamService.createTeam("Team Three");
        assertEquals(3,teamService.getAllTeams().size());
    }

    @Test
    void deleteTeamShouldReturnOneTeam() {
        teamService.deleteTeam((long) 1);
        assertEquals(1,teamService.getAllTeams().size());
    }
}
