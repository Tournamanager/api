package com.fontys.api.mutations;

import com.fontys.api.entities.Match;
import com.fontys.api.service.MatchService;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;

public class MatchMutation
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