package com.exec.controller;

import com.exec.EmailServiceImpl;
import com.exec.Utils;
import com.exec.model.Candidate;
import com.exec.service.CandidateService;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequestMapping("/api/Candidate")
@RestController
public class CandidateController {

    private final CandidateService candidateservice;
    private Utils utils = new Utils();
    private EmailServiceImpl emailSender= new EmailServiceImpl();

    public CandidateController(CandidateService candidateservice) {
        this.candidateservice = candidateservice;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCandidate(@RequestBody Map<String, String> body) {
        try{
            Candidate new_candidate = new Candidate(body.get("roll_no"), body.get("name"), body.get("email"));
            candidateservice.addCandidate(new_candidate);
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
            Candidate candidate;
            Map<String, String> response = new HashMap<String, String>();

            try{
                candidate = candidateservice.getCandidateByRoll(body.get("roll_no"));
            }
            catch(Exception E){
                response.put("message", "No student with this roll no.");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            if(candidate.is_activated == true)
            {
                response.put("message", "Student is already a candidate");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            String otp = utils.otpGenerator();
            candidateservice.setOtp(body.get("roll_no"), otp);
            emailSender.sendOTPMessage(candidate.email, candidate.name, otp);
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
            Candidate candidate = candidateservice.getCandidateByRoll(body.get("roll_no"));
            Map<String,String> response = new HashMap<>();

            if(! (candidate.otp).equals(body.get("otp"))){
                response.put("message", "Invalid OTP");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            
            String password = passwordEncoder.encode(body.get("password"));
            candidateservice.activateCandidate(body.get("roll_no"), password);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> body) {
        try{
            Candidate candidate = candidateservice.getCandidateByRoll(body.get("roll_no"));
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(! passwordEncoder.matches(body.get("password"), candidate.password)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}