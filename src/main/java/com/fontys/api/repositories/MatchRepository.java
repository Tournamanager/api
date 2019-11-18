package com.fontys.api.repositories;

import com.fontys.api.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Integer>
{
}
