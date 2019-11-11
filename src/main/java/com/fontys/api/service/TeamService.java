package com.fontys.api.service;

import com.fontys.api.entities.Team;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
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
    public List<Team> getAllTeams(@Nullable Integer count, @Nullable String name) {
        if (count == null && name == null) {
            return teamRepository.findAll();
        }
        if (count == null) {
            return teamRepository.findAllByNameContains(name);
        }
        if (name == null) {
            Pageable pageable = PageRequest.of(0, count);
            Page<Team> teams = teamRepository.findAll(pageable);
            return teams.getContent();
        }
        Pageable pageable = PageRequest.of(0, count);
        Page<Team> teams = teamRepository.findAllByNameContains(name, pageable);
        return teams.getContent();
    }

    @Transactional(readOnly = true)
    public Optional<Team> getTeam(Integer id, String name) {
        if (id != null)
            return teamRepository.findById(id);
        if (name != null)
            return teamRepository.findByName(name);
        return Optional.empty();
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
