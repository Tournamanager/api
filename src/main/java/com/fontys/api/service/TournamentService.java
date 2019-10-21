package com.fontys.api.service;

import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.repositories.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService
{
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository)
    {
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    public Tournament createTournament(String name, String description, User user, int numberOfTeams)
    {
        return tournamentRepository.save(new Tournament(name, description, user, numberOfTeams));
    }

    @Transactional(readOnly = true)
    public List<Tournament> tournaments() { return tournamentRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Tournament> tournament(long id) { return tournamentRepository.findById(id); }
}
