package com.exec.controller;

import com.exec.EmailServiceImpl;
import com.exec.Utils;
import com.exec.model.Candidate;
import com.exec.service.CandidateService;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequestMapping("/api/candidate")
@RestController
public class CandidateController {

    private final CandidateService candidateservice;
    private Utils utils = new Utils();
    private EmailServiceImpl emailSender= new EmailServiceImpl();

    public CandidateController(CandidateService candidateservice) {
        this.candidateservice = candidateservice;
    }

    //TODO: add the httpsession access level to admin when the class is made
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
    public ResponseEntity<Object> signup(@RequestBody Map<String, String> body, HttpSession session) {
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
                response.put("message", "Student already signed up");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            String otp = utils.otpGenerator();
            candidateservice.setOtp(body.get("roll_no"), otp);
            emailSender.sendOTPMessage(candidate.email, candidate.name, otp);
            session.setAttribute("unverified_roll_no", body.get("roll_no"));
            session.setAttribute("unverified_access_level", "Candidate");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody Map<String, String> body, HttpSession session) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try{
            String roll_no = utils.isLoggedInUnverified(session);
            Map<String,String> response = new HashMap<>();

            if(roll_no == null || !session.getAttribute("unverified_access_level").equals("Candidate"))
            {
                response.put("message", "Invalid password change request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            Candidate candidate = candidateservice.getCandidateByRoll(body.get("roll_no"));

            if(! (candidate.otp).equals(body.get("otp"))){
                response.put("message", "Invalid OTP");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            
            String password = passwordEncoder.encode(body.get("password"));
            candidateservice.activateCandidate(body.get("roll_no"), password);
            session.removeAttribute("unverified_roll_no");
            session.removeAttribute("unverified_access_level");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> body, HttpSession session) {

        Map<String,String> response = new HashMap<>();

        try{

            String roll_no = utils.isLoggedIn(session);
            if(roll_no != null)
            {
                response.put("message", "Already logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            Candidate candidate = candidateservice.getCandidateByRoll(body.get("roll_no"));
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(! passwordEncoder.matches(body.get("password"), candidate.password)){
                response.put("message", "Invalid credentials");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            session.setAttribute("roll_no", candidate.roll_no);
            session.setAttribute("access_level", "Candidate");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) {

        Map<String,String> response = new HashMap<>();

        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Candidate"))
            {
                response.put("message", "Candidate not logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            session.removeAttribute("roll_no");
            session.removeAttribute("access_level");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}