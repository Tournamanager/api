//package com.fontys.api.service;
//
//import com.fontys.api.entities.Bracket;
//import com.fontys.api.entities.BracketSchedule;
//import com.fontys.api.entities.IBranch;
//import com.fontys.api.entities.Team;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//public class BracketScheduleTest
//{
//    @Test
//    public void getScheduleTest() {
//        Team team1 = new Team("The A Team");
//        Team team2 = new Team("The B Team");
//        Team team3 = new Team("The C Team");
//        Team team4 = new Team("The D Team");
//        Team team5 = new Team("The E Team");
//        Team team6 = new Team("The F Team");
//        Team team7 = new Team("The G Team");
//        Team team8 = new Team("The H Team");
//        Bracket bracket1 = new Bracket(team1, team2);
//        Bracket bracket2 = new Bracket(team3, team4);
//        Bracket bracket3 = new Bracket(team5, team6);
//        Bracket bracket4 = new Bracket(team7, team8);
//        Bracket bracket5 = new Bracket(bracket1, bracket2);
//        Bracket bracket6 = new Bracket(bracket3, bracket4);
//        Bracket bracket7 = new Bracket(bracket5, bracket6);
//        BracketSchedule schedule = new BracketSchedule(bracket7);
//
//        List<IBranch> branches = schedule.getSchedule();
//
//        assertEquals(15, branches.size());
//    }
//
//    @Test
//    public void getScheduleTest1() {
//        Team team1 = new Team("The A Team");
//        Team team2 = new Team("The B Team");
//        Team team3 = new Team("The C Team");
//        Team team4 = new Team("The D Team");
//        Team team5 = new Team("The E Team");
//        Team team6 = new Team("The F Team");
//        Bracket bracket1 = new Bracket(team1, team2);
//        Bracket bracket2 = new Bracket(team3, team4);
//        Bracket bracket3 = new Bracket(team5, team6);
//        Bracket bracket5 = new Bracket(bracket1, bracket2);
//        Bracket bracket7 = new Bracket(bracket5, bracket3);
//        BracketSchedule schedule = new BracketSchedule(bracket7);
//
//        List<IBranch> branches = schedule.getSchedule();
//
//        assertEquals(11, branches.size());
//    }
//
//    @Test
//    public void getScheduleTest2() {
//        Team team1 = new Team("The A Team");
//        Team team2 = new Team("The B Team");
//        Team team3 = new Team("The C Team");
//        Team team4 = new Team("The D Team");
//        Team team5 = new Team("The E Team");
//        Bracket bracket1 = new Bracket(team1, team2);
//        Bracket bracket2 = new Bracket(team3, team4);
//        Bracket bracket5 = new Bracket(bracket1, team5);
//        Bracket bracket7 = new Bracket(bracket5, bracket2);
//        BracketSchedule schedule = new BracketSchedule(bracket7);
//
//        List<IBranch> branches = schedule.getSchedule();
//
//        assertEquals(9, branches.size());
//    }
//}
