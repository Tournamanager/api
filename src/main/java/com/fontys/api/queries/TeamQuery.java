package com.fontys.api.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fontys.api.entities.Team;
import com.fontys.api.service.TeamService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeamQuery implements GraphQLQueryResolver {

    private final
    TeamService teamService;

    public TeamQuery(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<Team> teams(Integer count) {
        return this.teamService.getAllTeams();
    }

    public Optional<Team> team(final Integer id) {
        return this.teamService.getTeam(id);
    }
}
