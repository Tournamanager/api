package com.fontys.api.repositories;

import com.fontys.api.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Integer>
{
    Optional<Tournament> findByName(String name);
}
