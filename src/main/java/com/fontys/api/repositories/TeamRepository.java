package com.fontys.api.repositories;

import com.fontys.api.entities.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    Optional<Team> findByName(String name);
    List<Team> findAllByName(String name);
    Page<Team> findAllByName(String name, Pageable pageable);
}
