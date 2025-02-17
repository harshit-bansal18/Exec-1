package com.exec.controller;

import com.exec.EmailServiceImpl;
import com.exec.Utils;
import com.exec.model.CandidateInfo;
import com.exec.model.GBM;
import com.exec.model.Candidate;
import com.exec.service.AspiringCandidateService;
import com.exec.service.CandidateService;
import com.exec.service.GBMService;
import java.util.*;
import com.exec.Utils;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//TODO:Add keys to key repository while signup

@RequestMapping("/api/GBM")
@RestController
public class GBMController {
    
    private final GBMService gbmservice;
    private final AspiringCandidateService aspiringcandidateservice;
    private final CandidateService candidateService;
    private Utils utils = new Utils();
    private EmailServiceImpl emailSender= new EmailServiceImpl();

    public GBMController(GBMService gbmservice, AspiringCandidateService aspiringcandidateservice, CandidateService candidateService) {
        this.gbmservice = gbmservice;
        this.aspiringcandidateservice = aspiringcandidateservice;
        this.candidateService = candidateService;
    }

    //TODO: add the httpsession access level to admin when the class is made
    @PostMapping("/add")
    public ResponseEntity<Object> addGBM(@RequestBody Map<String, String> body, HttpSession session) {

        Map<String, String> response = new HashMap<String, String>();
        try{
            String roll_no = utils.isLoggedIn(session);

            if(roll_no == null || !session.getAttribute("access_level").equals("Admin")){
                response.put("message", "You are not authorized to perform this action");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            GBM new_gbm = new GBM(body.get("roll_no"), body.get("name"), body.get("email"));
            gbmservice.addGBM(new_gbm);
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
            GBM gbm;
            Map<String, String> response = new HashMap<String, String>();

            String roll_no = utils.isLoggedIn(session);
            if(roll_no != null){
                response.put("message", "You are already logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

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
            session.setAttribute("unverified_roll_no", body.get("roll_no"));
            session.setAttribute("unverified_access_level", "GBM");
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

            if(roll_no == null || !session.getAttribute("unverified_access_level").equals("GBM"))
            {
                response.put("message", "Invalid password change request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            GBM gbm = gbmservice.getGBMByRoll(roll_no);

            if(! (gbm.otp).equals(body.get("otp"))){
                response.put("message", "Invalid OTP");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            
            String password = passwordEncoder.encode(body.get("password"));
            gbmservice.activateGBM(roll_no, password);
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
            GBM gbm;
            try{
                gbm = gbmservice.getGBMByRoll(body.get("roll_no"));
            }
            catch(Exception E){
                response.put("message", "Invalid roll no.");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(! passwordEncoder.matches(body.get("password"), gbm.password)){
                response.put("message", "Invalid credentials");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            session.setAttribute("roll_no", gbm.roll_no);
            session.setAttribute("access_level", "GBM");
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
            if(roll_no == null || !session.getAttribute("access_level").equals("GBM"))
            {
                response.put("message", "GBM not logged in");
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

    @GetMapping("/campaignRequests")
    public ResponseEntity<Object> getCampaignRequests(HttpSession session) {

        try{
            String roll_no = utils.isLoggedIn(session);
            Map<String,String> response = new HashMap<>();

            if(roll_no == null || !session.getAttribute("access_level").equals("GBM"))
            {
                response.put("message", "No GBM user logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            List<Map<String, String>> requests = gbmservice.viewCampaignRequests(roll_no);
            return new ResponseEntity<Object>(requests, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/viewcandidateforms")
    public ResponseEntity<Object> viewForms(@RequestBody Map<String, String> body, HttpSession session){
        try{
            String roll_no = utils.isLoggedIn(session);
            Map<String,String> response = new HashMap<>();

            if(roll_no == null || !session.getAttribute("access_level").equals("GBM"))
            {
                response.put("message", "No GBM user logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            List<String> forms = gbmservice.get_form_link(body.get("roll_no"));
            for (Integer i = 0; i < forms.size(); ++i){
                response.put("form" + i.toString(), forms.get(i));
            }
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/acceptCampaignRequest")
    public ResponseEntity<Object> acceptCampaignRequest(@RequestBody Map<String, String> body, HttpSession session) {

        try{
            String roll_no = utils.isLoggedIn(session);
            Map<String,String> response = new HashMap<>();
 
            if(roll_no == null || !session.getAttribute("access_level").equals("GBM"))
            {
                response.put("message", "No GBM user logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            GBM gbm = gbmservice.getGBMByRoll(roll_no);
            try{
                gbmservice.acceptCampaignRequest(roll_no, body.get("roll_no_candidate"));
            }
            catch(Exception E){
                response.put("message", "Candidate did not request for campaign");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            Candidate candidate;
            try{
                candidate = candidateService.getCandidateByRoll(body.get("roll_no_candidate"));
            }
            catch(Exception E){
                response.put("message", "Candidate does not exist");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            emailSender.sendCampaignerAcceptanceMessage(candidate.email, gbm.name , candidate.name);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/rejectCampaignRequest")
    public ResponseEntity<Object> rejectCampaignRequest(@RequestBody Map<String, String> body, HttpSession session) {

        try{
            String roll_no = utils.isLoggedIn(session);
            Map<String,String> response = new HashMap<>();
 
            if(roll_no == null || !session.getAttribute("access_level").equals("GBM"))
            {
                response.put("message", "No GBM user logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            GBM gbm = gbmservice.getGBMByRoll(roll_no);
            try{
                gbmservice.rejectCampaignRequest(roll_no, body.get("roll_no_candidate"));
            }
            catch(Exception E){
                response.put("message", "Candidate did not request for campaign");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/fileNomination")
    public ResponseEntity<Object> fileNomination(@RequestBody CandidateInfo body, HttpSession session) {
        
        Map<String, String> response = new HashMap<>();

        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("GBM"))
            {
                response.put("message", "No GBM user logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            GBM gbm = gbmservice.getGBMByRoll(roll_no);
            body.Seconders = Utils.removeDuplicates(body.Seconders);
            body.Proposers = Utils.removeDuplicates(body.Proposers);

            if(gbm.is_campaigner)
            {
                response.put("message", "Applicant already in some other Candidate's team");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            if(gbm.applied_for_candidature){
                response.put("message", "Already applied for candidature");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            if(body.Proposers.size() < 1){
                response.put("message", "No proposers");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }
            
            if(body.Seconders.size() < 2){
                response.put("message", "Atleast two seconders required");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            if(body.manifesto_link == null){
                response.put("message", "No manifesto link");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            if(body.post == null){
                response.put("message", "No post specified");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            try{
                String _response = aspiringcandidateservice.applyCandidature(roll_no, body.Seconders, body.Proposers, body.manifesto_link, body.post);
                if(_response != null){
                    if(_response.equals("Proposer")){
                        response.put("message", "Proposer already in some other Candidate's team");
                        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
                    }
                    if(_response.equals("Seconder")){
                        response.put("message", "Seconder already in some other Candidate's team");
                        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
                    }
                }
            }
            catch(Exception E){
                response.put("message", "Proposer / Seconder not found");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }
            
            gbmservice.set_applied_for_candidature(roll_no);
            return ResponseEntity.status(HttpStatus.CREATED).build(); 
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
