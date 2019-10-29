package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Team createTeam(String name) {
        return teamRepository.save(new Team(name));
    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Team> getTeam(Integer id) {
        return teamRepository.findById(id);
    }

    @Transactional
    public String deleteTeam(Integer id) {
        Optional<Team> team = teamRepository.findById(id);
        team.ifPresent(teamRepository::delete);
        return "Team " + team.get().getName() + " deleted";
    }

    @Transactional
    public String addUserToTeam(Integer teamId, Integer userId) {
        User user = userRepository.findById(userId).get();
        Team team = teamRepository.findById(teamId).get();
        team.getUsers().add(user);
        teamRepository.save(team);
        return "User " + user.getId() + " added to team " + team.getName();
    }
}
