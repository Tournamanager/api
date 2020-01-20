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

        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);
        Round r3 = new Round(ml3,t);

        Mockito.when(roundService.createRound(ml1,t)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2,t)).thenReturn(r2);
        Mockito.when(roundService.createRound(ml3,t)).thenReturn(r3);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setRounds(Arrays.asList(r1,r2,r3));

        assertEquals(test,generateMatches.competition(t));
    }

    @Test
    void bracket4Teams() throws ParseException, InvalidAttributeValueException {
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

        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);

        Mockito.when(roundService.createRound(ml1,t)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2,t)).thenReturn(r2);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setRounds(Arrays.asList(r1,r2));

        assertEquals(test,generateMatches.bracket(t));
    }

    @Test
    void bracket7Teams() throws ParseException, InvalidAttributeValueException {
        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        Team t4 = new Team(4,"d");
        Team t5 = new Team(5,"e");
        Team t6 = new Team(6, "f");
        Team t7 = new Team(7, "g");
        List<Team> teamList = Arrays.asList(t1,t2,t3,t4,t5,t6,t7);

        Tournament t = new Tournament();
        t.setMethod("Bracket");
        t.setTeams(teamList);

        Match m1 = new Match(t1,t2);
        Match m2 = new Match(t3,t4);
        Match m3 = new Match(t5,t6);
        Match m4 = new Match(null, null);
        Match m5 = new Match(null,t7);
        Match m6 = new Match(null,null);

        Mockito.when(matchService.createMatch(1,2,null,t.getId())).thenReturn(m1);
        Mockito.when(matchService.createMatch(3,4,null,t.getId())).thenReturn(m2);
        Mockito.when(matchService.createMatch(5,6,null,t.getId())).thenReturn(m3);
        Mockito.when(matchService.createMatch(null,null,null,t.getId())).thenReturn(m4);
        Mockito.when(matchService.createMatch(null,7,null,t.getId())).thenReturn(m5);
        Mockito.when(matchService.createMatch(null,null,null,t.getId())).thenReturn(m6);

        List<Match> ml1 = Arrays.asList(m1,m2,m3);
        List<Match> ml2 = Arrays.asList(m4,m5);
        List<Match> ml3 = Collections.singletonList(m6);

        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);
        Round r3 = new Round(ml3,t);

        Mockito.when(roundService.createRound(ml1,t)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2,t)).thenReturn(r2);
        Mockito.when(roundService.createRound(ml3,t)).thenReturn(r3);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setMethod("Bracket");
        test.setRounds(Arrays.asList(r1,r2,r3));

        assertEquals(test,generateMatches.bracket(t));
    }

    @Test
    void bracket6Teams() throws ParseException, InvalidAttributeValueException {
        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        Team t4 = new Team(4,"d");
        Team t5 = new Team(5,"e");
        Team t6 = new Team(6, "f");
        List<Team> teamList = Arrays.asList(t1,t2,t3,t4,t5,t6);

        Tournament t = new Tournament();
        t.setMethod("Bracket");
        t.setTeams(teamList);

        Match m1 = new Match(t1,t2);
        Match m2 = new Match(t3,t4);
        Match m3 = new Match(null,null);
        Match m4 = new Match(t5,t6);
        Match m5 = new Match(null,null);

        Mockito.when(matchService.createMatch(1,2,null,t.getId())).thenReturn(m1);
        Mockito.when(matchService.createMatch(3,4,null,t.getId())).thenReturn(m2);
        Mockito.when(matchService.createMatch(null,null,null,t.getId())).thenReturn(m3);
        Mockito.when(matchService.createMatch(5,6,null,t.getId())).thenReturn(m4);
        Mockito.when(matchService.createMatch(null,null,null,t.getId())).thenReturn(m5);

        List<Match> ml1 = Arrays.asList(m1,m2);
        List<Match> ml2 = Arrays.asList(m3,m4);
        List<Match> ml3 = Collections.singletonList(m5);

        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);
        Round r3 = new Round(ml3,t);

        Mockito.when(roundService.createRound(ml1,t)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2,t)).thenReturn(r2);
        Mockito.when(roundService.createRound(ml3,t)).thenReturn(r3);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setMethod("Bracket");
        test.setRounds(Arrays.asList(r1,r2,r3));

        assertEquals(test,generateMatches.bracket(t));
    }

    @Test
    void bracket5Teams() throws ParseException, InvalidAttributeValueException {
        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        Team t4 = new Team(4,"d");
        Team t5 = new Team(5,"e");
        List<Team> teamList = Arrays.asList(t1,t2,t3,t4,t5);

        Tournament t = new Tournament();
        t.setMethod("Bracket");
        t.setTeams(teamList);

        Match m1 = new Match(t1,t2);
        Match m2 = new Match(null,t3);
        Match m3 = new Match(t4,t5);
        Match m4 = new Match(null,null);

        Mockito.when(matchService.createMatch(1,2,null,t.getId())).thenReturn(m1);
        Mockito.when(matchService.createMatch(null,3,null,t.getId())).thenReturn(m2);
        Mockito.when(matchService.createMatch(4,5,null,t.getId())).thenReturn(m3);
        Mockito.when(matchService.createMatch(null,null,null,t.getId())).thenReturn(m4);

        List<Match> ml1 = Collections.singletonList(m1);
        List<Match> ml2 = Arrays.asList(m2,m3);
        List<Match> ml3 = Collections.singletonList(m4);

        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);
        Round r3 = new Round(ml3,t);

        Mockito.when(roundService.createRound(ml1,t)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2,t)).thenReturn(r2);
        Mockito.when(roundService.createRound(ml3,t)).thenReturn(r3);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setMethod("Bracket");
        test.setRounds(Arrays.asList(r1,r2,r3));

        assertEquals(test,generateMatches.bracket(t));
    }

    @Test
    void bracket3Teams() throws ParseException, InvalidAttributeValueException {
        Team t1 = new Team(1,"a");
        Team t2 = new Team(2,"b");
        Team t3 = new Team(3,"c");
        List<Team> teamList = Arrays.asList(t1,t2,t3);

        Tournament t = new Tournament();
        t.setTeams(teamList);

        Match m1 = new Match(t1,t2);
        Match m2 = new Match(null,t3);

        Mockito.when(matchService.createMatch(1,2,null,t.getId())).thenReturn(m1);
        Mockito.when(matchService.createMatch(null,3,null,t.getId())).thenReturn(m2);

        List<Match> ml1 = Collections.singletonList(m1);
        List<Match> ml2 = Collections.singletonList(m2);

        Round r1 = new Round(ml1,t);
        Round r2 = new Round(ml2,t);

        Mockito.when(roundService.createRound(ml1,t)).thenReturn(r1);
        Mockito.when(roundService.createRound(ml2,t)).thenReturn(r2);

        Tournament test = new Tournament();
        test.setTeams(teamList);
        test.setRounds(Arrays.asList(r1,r2));

        assertEquals(test,generateMatches.bracket(t));
    }
}
