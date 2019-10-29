package com.fontys.api.mockrepositories;

import com.fontys.api.entities.Tournament;
import com.fontys.api.repositories.TournamentRepository;

public class MockTournamentRepository extends MockJPARepository<Tournament, Integer> implements TournamentRepository
{
}
