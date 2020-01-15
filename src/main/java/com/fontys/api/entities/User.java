package com.fontys.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @ManyToMany(mappedBy = "users")
    private List<Team> teams;

    public User(String UUID) {
        this.uuid = UUID;
    }

    public User(int id, String uuid) {
        this.id = id;
        this.uuid = uuid;
        this.teams = new ArrayList<>();
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
