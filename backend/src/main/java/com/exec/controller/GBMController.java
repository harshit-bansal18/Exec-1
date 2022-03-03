package com.exec.controller;

import com.exec.EmailServiceImpl;
import com.exec.Utils;
import com.exec.model.GBM;
import com.exec.service.GBMService;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/GBM")
@RestController
public class GBMController {
    
    private final GBMService gbmservice;
    private Utils utils = new Utils();
    private EmailServiceImpl emailSender= new EmailServiceImpl();

    public GBMController(GBMService gbmservice) {
        this.gbmservice = gbmservice;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addGBM(@RequestBody Map<String, String> body) {
        try{
            GBM new_gbm = new GBM(body.get("roll_no"), body.get("name"), body.get("email"));
            gbmservice.addGBM(new_gbm);
            return ResponseEntity.status(HttpStatus.CREATED).build(); 
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody Map<String, String> body) {
        try
        {
            String otp = utils.otpGenerator();
            gbmservice.setOtp(body.get("roll_no"), otp);
            GBM gbm = gbmservice.getGBMByRoll(body.get("roll_no"));
            emailSender.sendOTPMessage(gbm.email, gbm.name, otp);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
