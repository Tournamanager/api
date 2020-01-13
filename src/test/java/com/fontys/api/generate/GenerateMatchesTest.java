package com.fontys.api.generate;

import com.fontys.api.entities.Match;
import com.fontys.api.entities.Round;
import com.fontys.api.entities.Team;
import com.fontys.api.entities.Tournament;
import com.fontys.api.service.MatchService;
import com.fontys.api.service.RoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.naming.directory.InvalidAttributeValueException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenerateMatchesTest {

    private MatchService matchService;
    private RoundService roundService;


    private GenerateMatches generateMatches;

    @BeforeEach
    void setUp() {
        matchService = Mockito.mock(MatchService.class);
        roundService = Mockito.mock(RoundService.class);

        generateMatches = new GenerateMatches(matchService, roundService);
    }

    @Test
    void competition() throws ParseException, InvalidAttributeValueException {
        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        List<Team> teamList = Arrays.asList(t1,t2,t3);

        Tournament t = new Tournament();
        t.setTeams(teamList);

        Match m1 = new Match(t1,t2);
        Match m2 = new Match(t1,t3);
        Match m3 = new Match(t2,t3);

        Mockito.when(matchService.createMatch(1,2,null,t.getId())).thenReturn(m1);
        Mockito.when(matchService.createMatch(1,3,null,t.getId())).thenReturn(m2);
        Mockito.when(matchService.createMatch(2,3,null,t.getId())).thenReturn(m3);

        List<Match> ml1 = Collections.singletonList(m1);
        List<Match> ml2 = Collections.singletonList(m2);
        List<Match> ml3 = Collections.singletonList(m3);

        Round r1 = new Round(ml1);
        Round r2 = new Round(ml2);
        Round r3 = new Round(ml3);

        Mockito.when(roundService.createRound(ml1)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2)).thenReturn(r2);
        Mockito.when(roundService.createRound(ml3)).thenReturn(r3);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setRounds(Arrays.asList(r1,r2,r3));

        assertEquals(test,generateMatches.competition(t));
    }

    @Test
    void bracket() throws ParseException, InvalidAttributeValueException {
        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        Team t4 = new Team(4,"d");
        List<Team> teamList = Arrays.asList(t1,t2,t3,t4);

        Tournament t = new Tournament();
        t.setTeams(teamList);

        Match m1 = new Match(t1,t2);
        Match m2 = new Match(t3,t4);
        Match m3 = new Match(null,null);

        Mockito.when(matchService.createMatch(1,2,null,t.getId())).thenReturn(m1);
        Mockito.when(matchService.createMatch(3,4,null,t.getId())).thenReturn(m2);
        Mockito.when(matchService.createMatch(null,null,null,t.getId())).thenReturn(m3);

        List<Match> ml1 = Arrays.asList(m1,m2);
        List<Match> ml2 = Collections.singletonList(m3);

        Round r1 = new Round(ml1);
        Round r2 = new Round(ml2);

        Mockito.when(roundService.createRound(ml1)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2)).thenReturn(r2);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setRounds(Arrays.asList(r1,r2));

        assertEquals(test,generateMatches.bracket(t));
    }
}
