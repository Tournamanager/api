package com.fontys.api.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fontys.api.entities.Tournament;
import com.fontys.api.service.TournamentService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TournamentQuery implements GraphQLQueryResolver
{
    private final TournamentService tournamentService;

    public TournamentQuery(TournamentService tournamentService)
    {
        this.tournamentService = tournamentService;
    }

    public List<Tournament> tournaments() { return tournamentService.tournaments(); }

    public Optional<Tournament> tournament(long id) { return tournamentService.tournament(id); }
}
