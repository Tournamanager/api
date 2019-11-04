package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService
{
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public TournamentService(TournamentRepository tournamentRepository, UserRepository userRepository)
    {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Tournament createTournament(String name, String description, Integer ownerId, int numberOfTeams)
    throws InvalidAttributeValueException
    {
        if (numberOfTeams <= 1)
        {
            throw new InvalidAttributeValueException(String.format(
                    "A tournament must be created for at least 2 teams. Number of teams provided was %d." +
                    " Please change the value and try again.", numberOfTeams));
        }
        if (name == null || name.isBlank())
        {
            throw new InvalidAttributeValueException(
                    "The tournament name can't be empty. Please give your tournament a name and try again.");
        }
        if (ownerId == null || ownerId < 1)
        {
            throw new InvalidAttributeValueException(
                    "Something went wrong while creating the tournament. Please try again.");
        }
        User user = userRepository.findById(ownerId).orElse(null);
        return tournamentRepository.save(new Tournament(name, description, user, numberOfTeams));
    }

    @Transactional(readOnly = true)
    public List<Tournament> tournaments()
    {
        return tournamentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tournament> tournament(Integer id)
    {
        return tournamentRepository.findById(id);
    }
}
