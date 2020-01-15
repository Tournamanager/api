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
import javax.naming.directory.InvalidAttributeValueException;
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
            Pageable pageable = PageRequest.of(0, count, Sort.by("id").descending());
            Page<Team> teams = teamRepository.findAll(pageable);
            return teams.getContent();
        }
        Pageable pageable = PageRequest.of(0, count, Sort.by("id").descending());
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
    public Team updateTeam(Integer id, String name) throws InvalidAttributeValueException {
        validateTeamName(name);
        validateTeam(id);
        return teamRepository.save(new Team(id, name));
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
            if(t.getUsers().contains(u))
            {
                return "User is already added to the team!";
            }
            t.getUsers().add(u);
            u.getTeams().add(t);
            teamRepository.save(t);
            userRepository.save(u);
            return "User " + u.getId() + " added to team " + t.getName();
        } else {
            return "User or team does not exist";
        }
    }

    @Transactional
    public String removeUserFromTeam(Integer teamId, Integer userId)
    {
        Optional<User> user = userRepository.findById(userId);
        Optional<Team> team = teamRepository.findById(teamId);
        if (user.isEmpty())
        {
            return "User does not exist";
        }
        else if (team.isEmpty())
        {
            return "Team does not exist";
        }
        else
        {
            User user1 = user.get();
            Team team1 = team.get();

            int removeIndex = 0;

            boolean found = false;
            for(User u: team1.getUsers()){
                if (u.getId() == user1.getId()){
                    found = true;
                    removeIndex = team1.getUsers().indexOf(u);
                }
            }

            if (!found){
                return "User is not added to the team!";
            }

            team1.getUsers().remove(removeIndex);
            teamRepository.save(team1);
            return "User " + user1.getId() + " is removed from team " + team1.getName();
        }
    }

    private void validateTeam(Integer id) throws InvalidAttributeValueException {
        if (teamRepository.findById(id).isEmpty())
            throw new InvalidAttributeValueException(
                    "Team doesn't exist. Please add a valid team."
            );
    }

    private void validateTeamName(String name) throws InvalidAttributeValueException {
        if (name == null || name.isBlank()) {
            throw new InvalidAttributeValueException(
                    "The tournament name can't be empty. Please give your tournament a name and try again.");
        }
    }
}
