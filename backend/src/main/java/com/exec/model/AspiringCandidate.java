package com.exec.model;

import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("AspiringCandidate")
public class AspiringCandidate extends User{
    
    public List<String> Seconders;
    public List<String> Proposers;
    public String manifesto;
    
    public AspiringCandidate(String roll_no, String name, String email, List<String> Seconders, List<String> Proposers, String manifesto) {
        super(roll_no, name, email);
        this.Seconders = Seconders;
        this.Proposers = Proposers;
        this.manifesto = manifesto;
    }

}
