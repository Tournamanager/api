package com.fontys.api.repositories;

import com.fontys.api.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long>
{
}
