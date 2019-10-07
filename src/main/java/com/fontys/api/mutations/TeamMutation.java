package com.fontys.api.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fontys.api.entities.Team;
import com.fontys.api.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamMutation implements GraphQLMutationResolver {

    @Autowired
    private TeamService teamService;

    public Team createTeam(String name) {
        return teamService.createTeam(name);
    }
}
