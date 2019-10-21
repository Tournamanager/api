package com.fontys.api.mockrepositories;

import com.fontys.api.entities.Team;
import com.fontys.api.repositories.TeamRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class MockTeamRepository implements TeamRepository {

    private List<Team> teams;

    public MockTeamRepository(List<Team> teamList) {
        teams = teamList;
    }

    @Override
    public List<Team> findAll() {
        return teams;
    }

    @Override
    public List<Team> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Team> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Team> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Team team) {
        teams.remove(team);
    }

    @Override
    public void deleteAll(Iterable<? extends Team> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Team> S save(S s) {
        Team t = new Team(teams.size(), s.getName());
        teams.add(t);
        return (S) t;
    }

    @Override
    public <S extends Team> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Team> findById(Integer integer) {
        for (Team t: teams) {
            if (t.getId().equals(integer)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Team> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Team> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Team getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends Team> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Team> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Team> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Team> Page<S> findAll(@NotNull Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Team> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Team> boolean exists(Example<S> example) {
        return false;
    }
}
