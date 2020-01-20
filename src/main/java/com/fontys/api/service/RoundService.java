package com.fontys.api.service;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Round;
import com.fontys.api.entities.Tournament;
import com.fontys.api.repositories.RoundRepository;
import com.fontys.api.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundService {
    private final RoundRepository roundRepository;
    private final TournamentRepository tournamentRepository;

    public RoundService(RoundRepository roundRepository, TournamentRepository tournamentRepository) {
        this.roundRepository = roundRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Round getRound(int id) {
        Optional<Round> round = roundRepository.findById(id);
        return round.orElse(null);
    }

    public Round createRound(Tournament tournament) {
        return roundRepository.save(new Round(tournament));
    }

    public Round setMatches(Round round, List<Match> matchList) {
        round.setMatches(matchList);
        return roundRepository.save(round);
    }

    public void updateRound(Match match) {
        Round round = match.getRound();
        Tournament tournament = round.getTournament();
        List<Round> roundList = tournament.getRounds();

        for (int i = 0; i < roundList.size(); i++) {
            List<Match> matchList = roundList.get(i).getMatches();
            for (int j = 0; j < matchList.size(); j++) {
                if (matchList.get(j).getId().equals(match.getId())) {
                    if (i < roundList.size()-1) {
                        if (j % 2 == 0) {
                            roundList.get(i+1).getMatches().get(j/2).setTeamHome(matchList.get(j).getWinner());
                        } else {
                            roundList.get(i+1).getMatches().get((j-1)/2).setTeamAway(matchList.get(j).getWinner());
                        }
                    }
                    break;
                }
            }
        }
        tournament.setRounds(roundList);
        tournamentRepository.save(tournament);
    }
}
