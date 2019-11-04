package com.fontys.api.mockrepositories;

import com.fontys.api.entities.Tournament;
import com.fontys.api.repositories.TournamentRepository;

import java.util.Optional;

public class MockTournamentRepository extends MockJPARepository<Tournament, Integer> implements TournamentRepository
{
    @Override
    public Optional<Tournament> findByName(String name) {
        return Optional.empty();
    }
}
