package com.fontys.api.service;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Team;
import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.MatchRepository;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public TournamentService(TournamentRepository tournamentRepository, UserRepository userRepository, TeamRepository teamRepository, MatchRepository matchRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @Transactional
    public Tournament createTournament(String name, String description, Integer ownerId, int numberOfTeams)
            throws InvalidAttributeValueException {
        validateTournamentName(name);
        validateUserId(ownerId);
        validateNumberOfTeams(numberOfTeams);

        User user = userRepository.findById(ownerId).orElse(null);
        validateOwner(user);

        return tournamentRepository.save(new Tournament(name, description, user, numberOfTeams));
    }

    @Transactional
    public Tournament updateTournament(Integer id, String name, String description, Integer ownerId, Integer numberOfTeams) throws InvalidAttributeValueException {


        Tournament tournamentOld = validateTournament(id);
        validateTournamentName(name);
        validateUserId(ownerId);
        validateNumberOfTeams(numberOfTeams);

        User user = userRepository.findById(ownerId).orElse(null);
        validateOwner(user);

        return tournamentRepository.save(new Tournament(id, name, description, user, numberOfTeams, tournamentOld.getTeams(), tournamentOld.getMatches()));
    }

    @Transactional
    public String deleteTournament(Integer id) {
        Optional<Tournament> tournament = tournamentRepository.findById(id);
        if (tournament.isPresent()) {
            tournamentRepository.delete(tournament.get());
            return "Tournament " + tournament.get().getName() + " deleted";
        } else {
            return "Tournament does not exist";
        }
    }

    @Transactional(readOnly = true)
    public List<Tournament> tournaments(Integer idOfOwner) {
        if (idOfOwner != null)
            return tournamentRepository.findByOwner(userRepository.findById(idOfOwner).orElse(null));
        return tournamentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tournament> tournament(Integer id, String name) {
        if (id != null)
            return tournamentRepository.findById(id);
        if (name != null)
            return tournamentRepository.findByName(name);
        return Optional.empty();
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
        if(tournament1.getTeams().contains(team1))
        {
            return "The team already joined the tournament!";
        }
        if(tournament1.getNumberOfTeams() == tournament1.getTeams().size())
        {
            return "The tournament is currently filled with teams!";
        }
        tournament1.getTeams().add(team1);
        tournamentRepository.save(tournament1);
        return "Team " + team1.getName() + " added to tournament " + tournament1.getName();

    }

    public Tournament generateMatches(Integer id) throws InvalidAttributeValueException
    {
        Tournament tournament = validateTournament(id);
        int numberOfTeams = tournament.getTeams().size();
        if(numberOfTeams < 2)
        {
            throw new InvalidAttributeValueException("The tournament must at least have 2 teams to generate a tournament!");
        }
        int numberOfRounds = (int) Math.ceil(Math.log(numberOfTeams)/Math.log(2));
        int numberOfMatches = numberOfTeams - 1;
        List<Match> matches = new ArrayList<>();
        for(int i = 0; i < numberOfRounds - 1; i++)
        {
            for(int j = 0; j < (int) Math.pow(2, i); j++)
            {
                matches.add(new Match(String.format("Round %d Match %d", i, j)));
            }
        }
        return null;
    }

    public String addMatchToTournament(Integer tournamentId, Integer matchId)
    {
        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);
        Optional<Match> match = matchRepository.findById(matchId);

        if(tournament.isEmpty())
        {
            return "The tournament does not exist";
        }
        if(match.isEmpty())
        {
            return "The match does not exist";
        }
        Tournament tournament1 = tournament.get();
        Match match1 = match.get();

        if(tournament1.getMatches().contains(match1))
        {
            return "The match is already added to the tournament!";
        }
        tournament1.getMatches().add(match1);
        tournamentRepository.save(tournament1);
        return "The match was successfully added to the tournament.";
    }

    private Tournament validateTournament(Integer id) throws InvalidAttributeValueException {
        Optional<Tournament> tournament = tournamentRepository.findById(id);
        if (tournament.isEmpty()) {
            throw new InvalidAttributeValueException(
                    "The tournament doesn't exist. Please select a tournament and try again.");
        }
        return tournament.get();
    }

    private void validateTournamentName(String name) throws InvalidAttributeValueException {
        if (name == null || name.isBlank()) {
            throw new InvalidAttributeValueException(
                    "The tournament name can't be empty. Please give your tournament a name and try again.");
        }
    }

    private void validateUserId(Integer ownerId) throws InvalidAttributeValueException {
        if (ownerId == null || ownerId < 1) {
            throw new InvalidAttributeValueException(
                    "Something went wrong while creating the tournament. Please try again.");
        }
    }

    private void validateNumberOfTeams(Integer numberOfTeams) throws InvalidAttributeValueException {
        if (numberOfTeams <= 1) {
            throw new InvalidAttributeValueException(String.format(
                    "A tournament must be created for at least 2 teams. Number of teams provided was %d." +
                            " Please change the value and try again.", numberOfTeams));
        }
    }

    private void validateOwner(User owner) throws InvalidAttributeValueException {
        if (owner == null) {
            throw new InvalidAttributeValueException("An error occurred while creating the tournament. The user was not found! Please try again.");
        }
    }
}
