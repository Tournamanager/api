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
public class User
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Unique
    private String UUID;

    public User(String UUID) {
        this.UUID = UUID;
    }
}
