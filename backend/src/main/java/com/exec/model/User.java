package com.exec.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("User")
public class User{ // * User Class that stores users in database

    @Id 
    @Indexed(unique = true)
    public String roll_no;
    public String name;
    public String username;
    public String password; // ! Hashed
    
}