package com.fontys.api.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fontys.api.entities.Tournament;
import com.fontys.api.entities.User;
import com.fontys.api.service.TournamentService;
import org.springframework.stereotype.Component;

@Component
public class TournamentMutation implements GraphQLMutationResolver
{
    private final TournamentService tournamentService;

    public TournamentMutation(TournamentService tournamentService) { this.tournamentService = tournamentService; }

    public Tournament createTournament(String name, String description, Integer ownerId, Integer numberOfTeams)
    {
        return tournamentService.createTournament(name, description, ownerId, numberOfTeams);
    }
}
