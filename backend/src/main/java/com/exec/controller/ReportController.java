package com.exec.controller;
import com.exec.ReportAuth;
import com.exec.model.Key;
import com.exec.model.Report;
import com.exec.service.ReportService;

// import java.security.Key;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/report")
@RestController
public class ReportController {
    
    private final ReportService Reportservice;

    public ReportController(ReportService Reportservice) {
        this.Reportservice = Reportservice;
    }


//TODO: Test if working :p
//Open APIs---- Not to be Authenticated
    @PostMapping("/post/")
    public ResponseEntity<Object> post(@RequestBody Map<String, String> body) {
         
        try{
            Report rep = new Report(body.get("message"), body.get("signed"));
            if(ReportAuth.check(Reportservice.publicKeys().toString(), body.get("signed"), body.get("message"))){
                Reportservice.addReport(rep);
                return ResponseEntity.status(HttpStatus.CREATED).build(); 
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            // Reportservice.addReport(rep);
            // return ResponseEntity.status(HttpStatus.CREATED).build(); 
            
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/keys/public/")
    public ResponseEntity<Object> getPublicKeys() {

        try{
            

            List<String> requests = Reportservice.publicKeys();
            return new ResponseEntity<Object>(requests, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/keys/private/")
    public ResponseEntity<Object> getEncryptedPriv(@RequestBody Map<String, String> body) {

        try{
           
            Key requests = Reportservice.getPriv(body.get("roll"));
            return new ResponseEntity<Object>(requests, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
