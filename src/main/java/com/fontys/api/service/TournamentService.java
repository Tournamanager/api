package com.fontys.api.service;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.MatchRepository;
import com.fontys.api.entities.Team;
import com.fontys.api.repositories.TeamRepository;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;


    public TournamentService(TournamentRepository tournamentRepository, UserRepository userRepository, MatchRepository matchRepository, TeamRepository teamRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public Tournament createTournament(String name, String description, Integer ownerId, int numberOfTeams)
            throws InvalidAttributeValueException {
        validateTournamentName(name);
        validateUserId(ownerId);
        validateNumberOfTeams(numberOfTeams);

        User user = userRepository.findById(ownerId).orElse(null);
        validateOwner(user, "An error occurred while creating the tournament. The user was not found! Please try again.");

        return tournamentRepository.save(new Tournament(name, description, user, numberOfTeams));
    }

    @Transactional
    public Tournament updateTournament(Integer id, String name, String description, Integer ownerId, Integer numberOfTeams) throws InvalidAttributeValueException {

        Tournament tournament = validateTournamentId(tournamentRepository.findById(id));

        validateTournamentName(name);
        validateUserId(ownerId);
        validateNumberOfTeams(numberOfTeams);

        User user = userRepository.findById(ownerId).orElse(null);
        validateOwner(user, "An error occurred while updating the tournament. The user was not found! Please try again.");

        return tournamentRepository.save(new Tournament(id, name, description, user, numberOfTeams, tournament.getTeams(), tournament.getMatches()));
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
    public Optional<Tournament> tournament(Integer id, String name) {
        if (id != null)
            return tournamentRepository.findById(id);
        if (name != null)
            return tournamentRepository.findByName(name);
        return Optional.empty();
    }

    @Transactional
    public String addMatchToTournament(Integer tournamentId, Integer matchId) throws InvalidAttributeValueException
    {
        Match match = validateMatchId(matchRepository.findById(matchId));
        Tournament tournament = validateTournamentId(tournamentRepository.findById(tournamentId));

        System.out.println(tournament);

        validateTournamentDoesNotAlreadyHaveMatch(tournament, match);

        tournament.getMatches().add(match);
        tournamentRepository.save(tournament);
        return "The match was successfully added to the tournament.";
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
        else
        {
            Tournament tournament1 = tournament.get();
            Team team1 = team.get();
            if(tournament1.getTeams().contains(team1))
            {
                return "The team already joined the tournament!";
            }
            if(tournament1.getNumberOfTeams() == tournament1.getTeams().size())
            {
                return "The tournament has no empty slot for this team!";
            }
            tournament1.getTeams().add(team1);
            tournamentRepository.save(tournament1);
            return "Team " + team1.getName() + " added to tournament " + tournament1.getName();
        }
    }

    private Tournament validateTournament(Integer id) throws InvalidAttributeValueException {
        if (tournamentRepository.findById(id).isEmpty()) {
            throw new InvalidAttributeValueException(
                    "The tournament doesn't exist. Please select a tournament and try again.");
        }
        return tournamentRepository.findById(id).get();
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

    private void validateOwner(User owner, String errorMessage) throws InvalidAttributeValueException
    {
        if (owner == null)
        {
            throw new InvalidAttributeValueException(errorMessage);
        }
    }

    private Match validateMatchId(Optional<Match> match) throws InvalidAttributeValueException
    {
        if(match.isEmpty())
        {
            throw new InvalidAttributeValueException("The Match doesn't exist. Please select a different match and try again.");
        }
        return match.get();
    }

    private void validateTournamentDoesNotAlreadyHaveMatch(Tournament tournament, Match match)
    throws InvalidAttributeValueException
    {
        if(tournament.getMatches().contains(match))
        {
            throw new InvalidAttributeValueException("The match is already added to the tournament!");
        }
    }
}
