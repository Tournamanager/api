package com.fontys.api.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fontys.api.entities.Team;
import com.fontys.api.service.TeamService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Component
public class TeamQuery implements GraphQLQueryResolver {

    private final
    TeamService teamService;

    public TeamQuery(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<Team> teams(@Nullable final Integer count, @Nullable final String name) {
        return this.teamService.getAllTeams(count, name);
    }

    public Optional<Team> team(@Nullable final Integer id, @Nullable final String name) {
        return this.teamService.getTeam(id, name);
    }
}
