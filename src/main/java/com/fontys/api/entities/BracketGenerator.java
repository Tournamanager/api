//package com.fontys.api.entities;
//
//import java.util.PriorityQueue;
//import java.util.Queue;
//
//public class BracketGenerator implements MatchGenerator
//{
//    public TournamentSchedule generateSchedule(Tournament tournament) {
//        Queue<IBranch> brackets = new PriorityQueue<>(tournament.getTeams());
//        while (brackets.size() > 1)
//        {
//            brackets.add(new Bracket(brackets.remove(), brackets.remove()));
//        }
//        BracketSchedule schedule = new BracketSchedule(brackets.remove());
//        tournament.setSchedule(schedule);
//        return schedule;
//    }
//}
