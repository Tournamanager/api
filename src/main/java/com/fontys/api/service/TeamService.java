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

    @Autowired
    TeamRepository teamRepository;

    @Transactional
    public Team createTeam(String name) {
        return teamRepository.save(new Team(name));
    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeams(int count) {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Team> getTeam(Long id) {
        return teamRepository.findById(id);
    }
}
