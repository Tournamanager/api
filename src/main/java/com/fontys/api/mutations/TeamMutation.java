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

    public String deleteTeam(Integer id) {
        return teamService.deleteTeam(id);
    }

    public String addUserToTeam(Integer id) {
        return teamService.addUserToTeam(id);
    }
}
