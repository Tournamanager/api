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
        if (team.isPresent()) {
            teamRepository.delete(team.get());
            return "Team " + team.get().getName() + " deleted";
        } else {
            return "Team does not exist";
        }
    }

    @Transactional
    public String addUserToTeam(Integer teamId, Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Team> team = teamRepository.findById(teamId);
        if (user.isPresent() && team.isPresent()) {
            User u = user.get();
            Team t = team.get();
            t.getUsers().add(u);
            teamRepository.save(t);
            return "User " + u.getId() + " added to team " + t.getName();
        } else {
            return "User or team does not exist";
        }
    }
}
