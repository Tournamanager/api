package com.fontys.api.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fontys.api.entities.Team;
import com.fontys.api.service.TeamService;
import org.springframework.stereotype.Component;

@Component
public class TeamMutation implements GraphQLMutationResolver {

    private final
    TeamService teamService;

    public TeamMutation(TeamService teamService) {
        this.teamService = teamService;
    }

    public Team createTeam(String name) {
        return teamService.createTeam(name);
    }

    public void deleteTeam(Long id) {
        teamService.deleteTeam(id);
    }
}
