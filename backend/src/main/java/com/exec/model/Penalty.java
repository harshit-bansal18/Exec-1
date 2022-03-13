package com.exec.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Penalty")
public class Penalty {

    @Id
    public String id;

    public String role = "GBM", name, roll_no;
    public String fine;
    public String level;
    public String part;
    public String remark; // Put remark = "None" if no additional remarks.

    public Penalty(String role, String name, String roll_no, String fine, String level, String part, String remark){
        this.role = role;
        this.name = name;
        this.roll_no = roll_no;
        this.fine = fine;
        this.level = level;
        this.part = part;
        this.remark = remark;
    }

    public Penalty(String role, String name, String roll_no, String fine, String level, String part){
        this.role = role;
        this.name = name;
        this.roll_no = roll_no;
        this.fine = fine;
        this.level = level;
        this.part = part;
        this.remark = "";
    }
}
