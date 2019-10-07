package com.fontys.api.service;

import com.fontys.api.entities.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private List<Team> teams = new ArrayList<>();

    public Team createTeam(Long id, String name) {
        Team team = new Team();
        team.setId(id);
        team.setName(name);

        teams.add(team);
        return team;
    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeams(int count) {
        return teams;
    }

    @Transactional(readOnly = true)
    public Optional<Team> getTeam(Long id) {
        for(Team t : teams) {
            if (t.getId().equals(id)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }
}
