package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Team")
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;

    public Team(String name) {
        this.name = name;
        this.users = new ArrayList<>();
    }

    public Team(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Team team = (Team) o;

        return name.equals(team.name);
    }

    @Override
    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

//    @Override
    public Team getTeam()
    {
        return this;
    }
}
