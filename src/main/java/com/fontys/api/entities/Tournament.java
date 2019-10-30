package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tournament
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Unique
    private String name;
    private String description;
    @OneToOne
    private User owner;
    private int numberOfTeams;


    public Tournament(String name, String description, User owner, Integer numberOfTeams)
    {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.numberOfTeams = numberOfTeams;
    }
}
