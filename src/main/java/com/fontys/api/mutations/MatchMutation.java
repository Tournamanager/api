package com.fontys.api.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fontys.api.entities.Match;
import com.fontys.api.service.MatchService;
import org.springframework.stereotype.Component;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;

@Component
public class MatchMutation implements GraphQLMutationResolver
{
    private final MatchService matchService;

    public MatchMutation(MatchService matchService)
    {
        this.matchService = matchService;
    }

    public Match createMatch(Integer teamHomeId, Integer teamAwayId, String date)
    throws ParseException, InvalidAttributeValueException
    {
        return matchService.createMatch(teamHomeId, teamAwayId, date);
    }
}
