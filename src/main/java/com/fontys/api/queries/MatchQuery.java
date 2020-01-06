package com.fontys.api.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fontys.api.entities.Match;
import com.fontys.api.service.MatchService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Component
public class MatchQuery implements GraphQLQueryResolver {

    private final
    MatchService matchService;

    public MatchQuery(MatchService matchService) {
        this.matchService = matchService;
    }

    public List<Match> matches(@Nullable final Integer count) {
        return this.matchService.getAllMatches(count);
    }

    public Optional<Match> match(@Nullable final Integer id) {
        return this.matchService.getMatch(id);
    }
}
