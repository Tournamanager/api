package com.fontys.api.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fontys.api.entities.Tournament;
import com.fontys.api.service.TournamentService;
import org.springframework.stereotype.Component;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;

@Component
public class TournamentMutation implements GraphQLMutationResolver {
    private final TournamentService tournamentService;

    public TournamentMutation(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public Tournament createTournament(String name, String description, Integer ownerId, Integer numberOfTeams, String method)
            throws InvalidAttributeValueException {
        return tournamentService.createTournament(name, description, ownerId, numberOfTeams, method);
    }

    public Tournament updateTournament(Integer id, String name, String description, Integer ownerId, Integer numberOfTeams)
            throws InvalidAttributeValueException {
        return tournamentService.updateTournament(id, name, description, ownerId, numberOfTeams);
    }

    public String deleteTournament(Integer id) {
        return tournamentService.deleteTournament(id);
    }

    public Tournament startTournament(Integer id, String method) throws InvalidAttributeValueException, ParseException {
        return tournamentService.startTournament(id, method);
    }

    public String addTeamToTournament(Integer tournamentId, Integer teamId) {
        return tournamentService.addTeamToTournament(tournamentId, teamId);
    }

    public String removeTeamFromTournament(Integer tournamentId, Integer teamId) {
        return tournamentService.removeTeamFromTournament(tournamentId, teamId);
    }
}
