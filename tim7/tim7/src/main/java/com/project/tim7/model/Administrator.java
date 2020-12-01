package com.project.tim7.model;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "administrators")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Administrator extends Person {

    public Administrator(){}

    public Administrator(Integer id, String email, String username, String password) {
        super(id, email, username, password);
    }
}
