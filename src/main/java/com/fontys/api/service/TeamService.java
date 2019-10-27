package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final
    TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public Team createTeam(String name) {
        return teamRepository.save(new Team(name));
    }

    @Transactional
    public void deleteTeam(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        team.ifPresent(teamRepository::delete);

    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Team> getTeam(Long id) {
        return teamRepository.findById(id);
    }
}
