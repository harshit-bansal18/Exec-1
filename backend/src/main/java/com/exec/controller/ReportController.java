package com.exec.controller;
import com.exec.model.Report;
import com.exec.service.ReportService;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/Report")
@RestController
public class ReportController {
    
    private final ReportService Reportservice;

    public ReportController(ReportService Reportservice) {
        this.Reportservice = Reportservice;
    }



//Open APIs---- Not to be Authenticated
    @PostMapping("/gen")
    public ResponseEntity<Object> gen(@RequestBody Map<String, String> body) {
         
        try{
            Report rep = new Report();
            Reportservice.addReport(rep);
            return ResponseEntity.status(HttpStatus.CREATED).build(); 
            
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
