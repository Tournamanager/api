package com.fontys.api.service;


import com.fontys.api.entities.Team;
import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.generate.GenerateMatches;
import com.fontys.api.repositories.MatchRepository;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService
{
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final GenerateMatches generateMatches;

    public TournamentService(TournamentRepository tournamentRepository, UserRepository userRepository,
                             TeamRepository teamRepository, MatchRepository matchRepository, GenerateMatches generateMatches)
    {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.generateMatches = generateMatches;
    }

    @Transactional
    public Tournament createTournament(String name, String description, Integer ownerId, int numberOfTeams)
    throws InvalidAttributeValueException
    {
        validateTournamentName(name);
        validateUserId(ownerId);
        validateNumberOfTeams(numberOfTeams);

        User user = userRepository.findById(ownerId).orElse(null);
        validateOwner(user, "An error occurred while creating the tournament. The user was not found! Please try again.");

        return tournamentRepository.save(new Tournament(name, description, user, numberOfTeams));
    }

    @Transactional
    public Tournament updateTournament(Integer id, String name, String description, Integer ownerId,
                                       Integer numberOfTeams) throws InvalidAttributeValueException
    {
        Tournament tournament = validateTournamentId(tournamentRepository.findById(id));

        validateTournamentName(name);
        validateUserId(ownerId);
        validateNumberOfTeams(numberOfTeams);

        User user = userRepository.findById(ownerId).orElse(null);
        validateOwner(user, "An error occurred while updating the tournament. The user was not found! Please try again.");

        return tournamentRepository.save(new Tournament(id, name, description, user, numberOfTeams, tournament.getTeams(), tournament.getRounds()));
    }

    @Transactional
    public String deleteTournament(Integer id)
    {
        Optional<Tournament> tournament = tournamentRepository.findById(id);

        if (tournament.isPresent())
        {
            tournamentRepository.delete(tournament.get());
            return "Tournament " + tournament.get().getName() + " deleted";
        }
        else
        {
            return "Tournament does not exist";
        }
    }

    @Transactional(readOnly = true)
    public List<Tournament> tournaments(Integer idOfOwner) throws InvalidAttributeValueException
    {
        if (idOfOwner != null)
        {
            User owner = userRepository.findById(idOfOwner).orElse(null);
            validateOwner(owner, "An error occurred while loading the tournament. The user was not found! Please try again.");
            return tournamentRepository.findByOwner(owner);
        }
        return tournamentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tournament> tournament(Integer id, String name)
    {
        if (id != null)
        {
            return tournamentRepository.findById(id);
        }
        if (name != null)
        {
            return tournamentRepository.findByName(name);
        }
        return Optional.empty();
    }


    private Tournament validateTournamentId(Optional<Tournament> tournament) throws InvalidAttributeValueException
    {
        if (tournament.isEmpty())
        {
            throw new InvalidAttributeValueException(
                    "The tournament doesn't exist. Please select a tournament and try again.");
        }
        return tournament.get();
    }

    @Transactional
    public String addTeamToTournament(Integer tournamentId, Integer teamId) throws IllegalArgumentException
    {
        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);
        Optional<Team> team = teamRepository.findById(teamId);
        if (tournament.isEmpty())
        {
            return "The tournament does not exist";
        }
        else if (team.isEmpty())
        {
            return "The team does not exist";
        }
        Tournament tournament1 = tournament.get();
        Team team1 = team.get();
        if (tournament1.getTeams().contains(team1))
        {
            return "The team already joined the tournament!";
        }
        if (tournament1.getNumberOfTeams() == tournament1.getTeams().size())
        {
            return "The tournament is currently filled with teams!";
        }
        tournament1.getTeams().add(team1);
        team1.getTournaments().add(tournament1);
        tournamentRepository.save(tournament1);
        teamRepository.save(team1);
        return "Team " + team1.getName() + " added to tournament " + tournament1.getName();

    }

    @Transactional
    public Tournament startTournament(Integer tournamentId, String method) throws InvalidAttributeValueException, ParseException {
        Tournament tournament = validateTournament(tournamentId);

        if (method.equals("competition")) {
            tournament = generateMatches.competition(tournament);
        } else if (method.equals("brackets")) {
            tournament = generateMatches.bracket(tournament);
        } else {
            throw new InvalidAttributeValueException("Can't generate matches");
        }

        return tournament;
    }

    @Transactional
    public String removeTeamFromTournament(Integer tournamentId, Integer teamId)
    {
        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);
        Optional<Team> team = teamRepository.findById(teamId);
        if (tournament.isEmpty())
        {
            return "Tournament does not exist";
        }
        else if (team.isEmpty())
        {
            return "Team does not exist";
        }
        else
        {
            Tournament tournament1 = tournament.get();
            Team team1 = team.get();

            int removeIndex = 0;

            boolean found = false;
            for(Team t: tournament1.getTeams()){
                if (t.getId() == team1.getId()){
                    found = true;
                    removeIndex = tournament1.getTeams().indexOf(t);
                }
            }

            if (!found){
                return "The team is not part of this tournament";
            }
            else if (tournament1.getRounds().size() > 0)
            {
                return "You cannot leave a tournament that is already started";
            }

            tournament1.getTeams().remove(removeIndex);
            tournamentRepository.save(tournament1);
            return "Team " + team1.getId() + " is removed from tournament " + tournament1.getName();
        }
    }

    private Tournament validateTournament(Integer id) throws InvalidAttributeValueException
    {
        Optional<Tournament> tournament = tournamentRepository.findById(id);
        if (tournament.isEmpty())
        {
            throw new InvalidAttributeValueException(
                    "The tournament doesn't exist. Please select a tournament and try again.");
        }
        return tournament.get();
    }

    private void validateTournamentName(String name) throws InvalidAttributeValueException
    {
        if (name == null || name.isBlank())
        {
            throw new InvalidAttributeValueException(
                    "The tournament name can't be empty. Please give your tournament a name and try again.");
        }
    }

    private void validateUserId(Integer ownerId) throws InvalidAttributeValueException
    {
        if (ownerId == null || ownerId < 1)
        {
            throw new InvalidAttributeValueException(
                    "Something went wrong while creating the tournament. Please try again.");
        }
    }

    private void validateNumberOfTeams(Integer numberOfTeams) throws InvalidAttributeValueException
    {
        if (numberOfTeams <= 1)
        {
            throw new InvalidAttributeValueException(String.format(
                    "A tournament must be created for at least 2 teams. Number of teams provided was %d." +
                    " Please change the value and try again.", numberOfTeams));
        }
    }

    private void validateOwner(User owner, String errorMessage) throws InvalidAttributeValueException
    {
        if (owner == null)
        {
            throw new InvalidAttributeValueException(errorMessage);
        }
    }


}
