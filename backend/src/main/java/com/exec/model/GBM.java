package com.exec.model;

import java.util.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("GBM")
public class GBM extends User{
    
    public boolean is_campaigner;
    public boolean is_activated;
    public List<String> campaign_requests;
    public String campaigner_of;
    public String otp;

    public GBM(String roll_no, String name, String email) {
        super(roll_no, name, email);
        this.is_campaigner = false;
        this.is_activated = false;
        campaign_requests = new ArrayList<String>();
        campaigner_of = null;
    }

}
