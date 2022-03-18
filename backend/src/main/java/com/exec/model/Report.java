package com.exec.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Report")
public class Report {
    
    // public boolean is_auth;
    // public boolean is_complete;
    @Id
    public String id;
    public String message;

    public String signed;

    public Report(String message, String signed) {
        // super(roll_no, name, email);
        this.message = message;
        this.signed = signed;
        // this.is_complete = false;
        // this.ring = "null";
    }

}
