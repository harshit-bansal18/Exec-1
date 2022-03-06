package com.exec.model;


import java.util.List;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Candidate")
public class Candidate extends User{
    
    public List<GBM> Campaigners;
    public List<GBM> Seconders;
    public List<GBM> Proposers;
    // ! More variables to be inserted 
    
    public boolean is_activated;
    public String otp;

    public Candidate(String roll_no, String name, String email) {
        super(roll_no, name, email);
        this.is_activated = false;
        this.Campaigners = new ArrayList<GBM>();
        this.Seconders = new ArrayList<GBM>();
        this.Proposers = new ArrayList<GBM>();
    }

}
