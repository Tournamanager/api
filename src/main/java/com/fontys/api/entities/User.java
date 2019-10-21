package com.fontys.api.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @GeneratedValue @Id
    Integer id;

    User() {}
}
