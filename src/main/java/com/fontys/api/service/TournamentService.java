package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
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

    public TournamentService(TournamentRepository tournamentRepository, UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
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

        Tournament oldTournament = validateTournament(id);
        validateTournamentName(name);
        validateUserId(ownerId);
        validateNumberOfTeams(numberOfTeams);

        User user = userRepository.findById(ownerId).orElse(null);
        validateOwner(user);

        return tournamentRepository.save(new Tournament(id, name, description, user, numberOfTeams, oldTournament.getMatches()));
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
            return tournamentRepository.findByOwner(userRepository.findById(idOfOwner).get());
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
    public String addMatchToTournament(Integer tournamentId, Integer matchId)
    {
        return null;
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
