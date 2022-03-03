package com.exec.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("GBM")
public class GBM extends User{
    
    public boolean is_campaigner;
    public boolean is_activated;
    public String otp;

    public GBM(String roll_no, String name, String email) {
        super(roll_no, name, email);
        this.is_campaigner = false;
        this.is_activated = false;
    }

}
