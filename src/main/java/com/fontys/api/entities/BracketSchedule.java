//package com.fontys.api.entities;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class BracketSchedule implements TournamentSchedule
//{
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @OneToOne
//    private IBranch finals;
//
//    public BracketSchedule(IBranch finals) {
//        this.finals = finals;
//    }
//
//    public List<IBranch> getSchedule() {
//        return getBrackets(new ArrayList<>(), finals);
//    }
//
//    private List<IBranch> getBrackets(List<IBranch> branches, IBranch branch) {
//        branches.add(branch);
//        if(branch instanceof Team) {
//            return branches;
//        }
//        Bracket bracket = (Bracket) branch;
//        branches = getBrackets(branches, bracket.leftBranch);
//        branches = getBrackets(branches, bracket.rightBranch);
//        return branches;
//    }
//
//    @Override
//    public void setMatches(List<Match> matches)
//    {
//
//    }
//
//    @Override
//    public List<Match> getMatches()
//    {
//        return null;
//    }
//}
