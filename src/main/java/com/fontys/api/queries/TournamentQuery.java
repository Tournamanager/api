package com.fontys.api.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fontys.api.entities.Tournament;
import com.fontys.api.service.TournamentService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
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

    public List<Tournament> tournaments(@Nullable Integer idOfOwner) { return tournamentService.tournaments(); }

    public Optional<Tournament> tournament(@Nullable Integer id, @Nullable String name) { return tournamentService.tournament(id, name); }
}
