//package com.fontys.api.entities;
//
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@NoArgsConstructor
//@Entity
//public class Bracket implements IBranch
//{
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @OneToOne
//    Match match;
//    @OneToOne
//    IBranch leftBranch;
//    @OneToOne
//    IBranch rightBranch;
//
//    public IBranch getLeftBranch()
//    {
//        return leftBranch;
//    }
//
//    public IBranch getRightBranch()
//    {
//        return rightBranch;
//    }
//
//    public Bracket(IBranch leftBranch, IBranch rightBranch)
//    {
//        this.leftBranch = leftBranch;
//        this.rightBranch = rightBranch;
//        this.match = new Match(leftBranch.getTeam(), rightBranch.getTeam());
//    }
//
//    @Override
//    public Team getTeam()
//    {
//        return match.getWinner();
//    }
//}
