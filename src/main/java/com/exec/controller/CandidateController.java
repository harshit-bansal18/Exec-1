package com.exec.controller;

import com.exec.EmailServiceImpl;
import com.exec.Utils;
import com.exec.model.Candidate;
import com.exec.model.GBM;
import com.exec.service.CandidateService;
import com.exec.service.GBMService;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequestMapping("/api/candidate")
@RestController
public class CandidateController {

    private final CandidateService candidateservice;
    private final GBMService gbmservice;
    private Utils utils = new Utils();
    private EmailServiceImpl emailSender= new EmailServiceImpl();

    public CandidateController(CandidateService candidateservice, GBMService gbmservice) {
        this.candidateservice = candidateservice;
        this.gbmservice = gbmservice;
    }
    // First you signup with your roll, name and email. 
    // If an object does not already exist in the candidate database yet, it will be created
    // after the necessary checks. Otherwise rejected.

    // Then you have to activate the candidate account. Such an account must have at least one proposer and two seconders
    //TODO: add the httpsession access level to admin when the class is made

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody Map<String, String> body, HttpSession session) {
        try
        {
            Candidate candidate;
            Map<String, String> response = new HashMap<String, String>();

            String roll_no = utils.isLoggedIn(session);
            if(roll_no != null){
                response.put("message", "You are already logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            try{
                candidate = candidateservice.getCandidateByRoll(body.get("roll_no"));
            }
            catch(Exception E){
                response.put("message", "No candidate with this roll no.");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            if(candidate.is_activated == true)
            {
                response.put("message", "Candidate already signed up");
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

    @PostMapping("/activate")
    public ResponseEntity<Object> activateCandidate(@RequestBody Map<String, String> body, HttpSession session) {
        try 
        {
            Map<String, String> response = new HashMap<String, String>();
            if(utils.isLoggedIn(session) != null || utils.isLoggedInUnverified(session) != null){
                response.put("message", "User already logged in.");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            Candidate candidate = candidateservice.getCandidateByRoll(body.get("roll_no"));
            if(candidate.is_activated == true)
            {
                response.put("message", "Student already signed up");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            else{
                String otp = utils.otpGenerator();
                candidateservice.setOtp(body.get("roll_no"), otp);
                emailSender.sendOTPMessage(candidate.email, candidate.name, otp);
                session.setAttribute("unverified_roll_no", body.get("roll_no"));
                session.setAttribute("unverified_access_level", "Candidate");
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        catch (Exception E) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // //TODO: take a look at the real working of the EC ki seconders/proposers add kab hote hain
    // @PostMapping("/addSeconder")
    // public ResponseEntity<Object> add_Seconder(@RequestBody Map<String,String> body){
    //     try {
    //         candidateservice.addSeconder(body.get("candidate_roll_no"), body.get("seconder_roll_no"));
    //         return ResponseEntity.status(HttpStatus.OK).build();
    //     }
    //     catch(Exception E){
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //     }
    // }

    // //TODO: same as in that of the seconder
    // @PostMapping("/addProposer")
    // public ResponseEntity<Object> add_Proposer(@RequestBody Map<String,String> body){
    //     try {
    //         candidateservice.addProposer(body.get("candidate_roll_no"), body.get("seconder_roll_no"));
    //         return ResponseEntity.status(HttpStatus.OK).build();
    //     }
    //     catch(Exception E){
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //     }
    // }
     @PostMapping("/requestCampaigner")
    public ResponseEntity<Object> request_Campaigner(@RequestBody Map<String,String> body, HttpSession session){
        try {
            Map<String, String> response = new HashMap<String, String>();
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Candidate")){
                response.put("message", "No candidate logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            GBM requested_gbm;
            try{
                requested_gbm = gbmservice.getGBMByRoll(body.get("gbm_roll_no"));
            }
            catch(Exception E){
                response.put("message", "No GBM with this roll no.");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            if(requested_gbm.is_campaigner){
                response.put("message", "GBM already a campaigner");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }
            
            gbmservice.addCampainRequests(requested_gbm.roll_no, roll_no);
            emailSender.sendCampaignRequestMessage(requested_gbm.email, requested_gbm.name, candidateservice.getCandidateByRoll(roll_no).name);
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
            gbmservice.removeOtp(roll_no);
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

    @PostMapping("/addform")
    public ResponseEntity<Object> add_form_link(@RequestBody Map<String, String> body, HttpSession session){
        Map<String,String> response = new HashMap<>();
        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Candidate")){
                response.put("message", "Candidate not logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            response.put("pk",Integer.toString(candidateservice.add_form(roll_no, body.get("form_link"))));
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/removeform")
    public ResponseEntity<Object> remove_form_link(@RequestBody Map<String, String> body, HttpSession session){
        Map<String,String> response = new HashMap<>();
        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Candidate")){
                response.put("message", "Candidate not logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            candidateservice.remove_form(roll_no, body.get("form_link"));
    
           return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/viewmyforms")
    public ResponseEntity<Object> view_my_forms(@RequestBody Map<String, String> body, HttpSession session){
        Map<String,String> response = new HashMap<>();
        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Candidate")){
                response.put("message", "Candidate not logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            List <String> forms = candidateservice.view_forms(roll_no); 
            for (Integer i = 0; i < forms.size(); ++i){
                response.put("form" + i.toString(), forms.get(i));
            }
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/addVideos")
    public ResponseEntity<Object> add_video(@RequestBody List<String> body, HttpSession session){
        try {
            Map<String, String> response = new HashMap<String, String>();
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Candidate")){
                response.put("message", "No candidate logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            for (String i : body) {
                candidateservice.add_video(roll_no, i);
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/addPosters")
    public ResponseEntity<Object> add_poster(@RequestBody List<String> body, HttpSession session){
        try {
            Map<String, String> response = new HashMap<String, String>();
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Candidate")){
                response.put("message", "No candidate logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            for (String i : body) {
                candidateservice.add_poster(roll_no, i);
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
            
}