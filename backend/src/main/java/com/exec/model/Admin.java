package com.exec.model;

import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Admin")
public class Admin extends User {
    
    public Map<String,Candidate> CandidateRequests;
    public Admin(String roll_no, String name, String email){
        super(roll_no,name,email);
        this.CandidateRequests= new HashMap<String,Candidate>();
    }

}
