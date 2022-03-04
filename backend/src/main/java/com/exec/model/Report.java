package com.exec.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Report")
public class Report {
    
    public boolean is_auth;
    public boolean is_complete;
    @Id
    public String id;
    public String ring;

    public Report() {
        // super(roll_no, name, email);
        this.is_auth = false;
        this.is_complete = false;
        this.ring = "null";
    }

}
