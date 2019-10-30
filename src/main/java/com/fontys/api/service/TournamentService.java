package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TournamentRepository;
import com.fontys.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    {
        User user = userRepository.findById(ownerId).get();
        return tournamentRepository.save(new Tournament(name, description, user, numberOfTeams));
    }

    @Transactional(readOnly = true)
    public List<Tournament> tournaments() { return tournamentRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Tournament> tournament(Integer id) { return tournamentRepository.findById(id); }
}
