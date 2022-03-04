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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
            GBM gbm;
            Map<String, String> response = new HashMap<String, String>();

            try{
                gbm = gbmservice.getGBMByRoll(body.get("roll_no"));
            }
            catch(Exception E){
                response.put("message", "No student with this roll no.");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            if(gbm.is_activated == true)
            {
                response.put("message", "Student already signed up");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            String otp = utils.otpGenerator();
            gbmservice.setOtp(body.get("roll_no"), otp);
            emailSender.sendOTPMessage(gbm.email, gbm.name, otp);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody Map<String, String> body) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try{
            GBM gbm = gbmservice.getGBMByRoll(body.get("roll_no"));
            Map<String,String> response = new HashMap<>();

            if(! (gbm.otp).equals(body.get("otp"))){
                response.put("message", "Invalid OTP");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            
            String password = passwordEncoder.encode(body.get("password"));
            gbmservice.activateGBM(body.get("roll_no"), password);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> body) {
        try{
            GBM gbm = gbmservice.getGBMByRoll(body.get("roll_no"));
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(! passwordEncoder.matches(body.get("password"), gbm.password)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
