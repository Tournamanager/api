package com.fontys.api.service;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Round;
import com.fontys.api.entities.Tournament;
import com.fontys.api.repositories.RoundRepository;
import com.fontys.api.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {
    private final RoundRepository roundRepository;
    private final TournamentRepository tournamentRepository;

    public RoundService(RoundRepository roundRepository, TournamentRepository tournamentRepository) {
        this.roundRepository = roundRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Round createRound(Round round) {
        return roundRepository.save(round);
    }

    public void updateRound(Match match) {
        Tournament tournament = match.getTournament();
        List<Round> roundList = tournament.getRounds();

        int roundIndex = -1;
        int matchIndex = -1;

        for (int i = 0; i < roundList.size(); i++) {
            List<Match> matchList = roundList.get(i).getMatches();
            for (int j = 0; j < matchList.size(); j++) {
                if (matchList.get(j).getId().equals(match.getId())) {
                    roundIndex = i;
                    matchIndex = j;
                    break;
                }
            }
            if (roundIndex != -1) {
                if (roundIndex < roundList.size()-1) {
                    roundIndex++;
                    if (matchIndex % 2 == 0) {
                        roundList.get(roundIndex).getMatches().get(matchIndex/2).setTeamHome(matchList.get(matchIndex).getWinner());
                    } else {
                        roundList.get(roundIndex).getMatches().get((matchIndex-1)/2).setTeamAway(matchList.get(matchIndex).getWinner());
                    }
                }
                break;
            }
        }
        tournament.setRounds(roundList);
        tournamentRepository.save(tournament);
    }
}
