package com.fontys.api.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fontys.api.entities.Team;
import com.fontys.api.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeamQuery implements GraphQLQueryResolver {

    @Autowired
    TeamService teamService;

    public List<Team> teams(final int count) {
        return this.teamService.getAllTeams(count);
    }

    public Optional<Team> team(final Long id) {
        return this.teamService.getTeam(id);
    }
}
