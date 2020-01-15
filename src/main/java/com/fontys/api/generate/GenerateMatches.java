package com.fontys.api.generate;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Round;
import com.fontys.api.entities.Team;
import com.fontys.api.entities.Tournament;
import com.fontys.api.service.MatchService;
import com.fontys.api.service.RoundService;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GenerateMatches {

    private final MatchService matchService;
    private final RoundService roundService;

    public GenerateMatches(MatchService matchService, RoundService roundService) {
        this.matchService = matchService;
        this.roundService = roundService;
    }

    public Tournament competition(Tournament tournament) throws ParseException, InvalidAttributeValueException {
        List<Team> teamList = new ArrayList<>(tournament.getTeams());
        if (tournament.getTeams().size()%2 != 0) {
            teamList.add(new Team("null"));
        }

        List<Team> teams = new ArrayList<>(teamList);
        teams.remove(0);

        List<Round> roundList = new ArrayList<>();

        for (int i = 0; i < teamList.size() - 1; i++) {
            List<Match> matchList = new ArrayList<>();
            int index = i % teamList.size();
            if (!teams.get(index).getName().equals("null")) {
                matchList.add(matchService.createMatch(teamList.get(0).getId(), teams.get(index).getId(),null, tournament.getId()));
            }

            for (int j = 1; j < teamList.size()/2; j++) {
                int teamHome = (i + j) % teams.size();
                int teamAway = (i - j + teams.size()) % teams.size();
                if (teams.get(teamHome).getName().equals("null") || teams.get(teamAway).getName().equals("null")) {
                    continue;
                }
                matchList.add(matchService.createMatch(teams.get(teamHome).getId(), teams.get(teamAway).getId(),null, tournament.getId()));
            }
            roundList.add(roundService.createRound(matchList));
        }
        tournament.setRounds(roundList);
        return tournament;
    }

    public Tournament bracket(Tournament tournament) throws ParseException, InvalidAttributeValueException {
        List<Team> teamList = new ArrayList<>(tournament.getTeams());

        int rounds = (int) Math.ceil(Math.log(teamList.size())/Math.log(2));
        int numberOfFreePassesRound1 = (int) (Math.pow(2, rounds)) - teamList.size() ;

        List<Round> roundList = new ArrayList<>();
        for (int i = 0; i < rounds; i++) {
            List<Match> matchList = new ArrayList<>();
            int k = 2;
            if(i == 0) {
                k += numberOfFreePassesRound1;
            }
            while (teamList.size() >= k) {
                matchList.add(matchService.createMatch(teamList.get(0).getId(),teamList.get(1).getId(),null,tournament.getId()));
                teamList.remove(0);
                teamList.remove(0);
            }
            for (int j = 0; j < matchList.size(); j++) {
                teamList.add(new Team());
            }
            roundList.add(roundService.createRound(matchList));
        }
        tournament.setRounds(roundList);
        return tournament;
    }
}
