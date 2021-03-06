package com.fontys.api.service;

import com.fontys.api.entities.*;
import com.fontys.api.repositories.RoundRepository;
import com.fontys.api.repositories.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class RoundServiceTest {


    private RoundService roundService;

    private RoundRepository roundRepository;
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void setUp() {
        roundRepository = Mockito.mock(RoundRepository.class);
        tournamentRepository = Mockito.mock(TournamentRepository.class);

        roundService = new RoundService(roundRepository, tournamentRepository);
    }

    @Test
    void updateRoundEvenTeams() {
        Tournament t = new Tournament(1,"hoi","",new User(),4,null,null,"bracket");

        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        Team t4 = new Team(4,"d");
        List<Team> teamList = Arrays.asList(t1,t2,t3,t4);
        t.setTeams(teamList);

        Match m1 = new Match(1,t1,t2,null,null,null);
        Match m2 = new Match(2,t3,t4,null,null,null);
        Match m3 = new Match(3,null,null,null,null,null);

        List<Match> ml1 = Arrays.asList(m1,m2);
        List<Match> ml2 = Collections.singletonList(m3);
        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);
        List<Round> roundList = Arrays.asList(r1,r2);
        t.setRounds(roundList);

        m1.setRound(r1);
        m2.setRound(r1);
        m3.setRound(r2);

        roundService.updateRound(new Match(1,t1,t2,t1,null,r1));

        //updating the tournament for verification
        m3.setTeamHome(t1);

        Mockito.verify(tournamentRepository,Mockito.times(1)).save(t);
    }

    @Test
    void updateRoundOddTeams() {
        Tournament t = new Tournament(1,"hoi","",new User(),3,null,null,"bracket");

        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        List<Team> teamList = Arrays.asList(t1,t2,t3);
        t.setTeams(teamList);

        Match m1 = new Match(1,t1,t2,t1,null,null);
        Match m2 = new Match(2,null,t3,null,null,null);

        List<Match> ml1 = Collections.singletonList(m1);
        List<Match> ml2 = Collections.singletonList(m2);
        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);
        List<Round> roundList = Arrays.asList(r1,r2);
        t.setRounds(roundList);

        m1.setRound(r1);
        m2.setRound(r2);

        roundService.updateRound(m1);

        //updating the tournament for verification
        m2.setTeamHome(t1);

        Mockito.verify(tournamentRepository,Mockito.times(1)).save(t);
    }
}
