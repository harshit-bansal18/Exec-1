package com.exec.controller;

import java.util.*;

import com.exec.Utils;

import com.exec.repository.AdminRepository;
import com.exec.model.Admin;

import com.exec.service.AdminService;

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


@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;
    private final AdminRepository  adminRepository;
    private Utils utils=new Utils();

    public AdminController(AdminService adminService,AdminRepository adminRepository){
        this.adminService=adminService;
        this.adminRepository=adminRepository;
    }


    //a temporary function for testing. Will be removed later
    @PostMapping("/addAdmin")
    public ResponseEntity<Object> addAdmin(@RequestBody Map<String,String> body) {
        
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encoded_password=passwordEncoder.encode(body.get("password"));
        Admin admin=new Admin(body.get("roll_no"),body.get("name"),body.get("email"));
        admin.password=encoded_password;
        adminRepository.insert(admin);
    
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> body, HttpSession session) {

        Map<String,String> response = new HashMap<>();

        try{

            String roll_no = utils.isLoggedIn(session);
            if(roll_no=="0")
            {
                response.put("message", "Already logged in");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            
    
            Admin admin = adminRepository.findAll().get(0);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(! passwordEncoder.matches(body.get("password"), admin.password)){
                response.put("message", "Invalid credentials");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            session.setAttribute("roll_no", admin.roll_no);
            session.setAttribute("access_level", "Admin");
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
            if( roll_no==null || !session.getAttribute("access_level").equals("Admin"))
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
    
    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody Map<String, String> body, HttpSession session) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try{
            String roll_no = utils.isLoggedIn(session);
            Map<String,String> response = new HashMap<>();

            if( roll_no==null || !session.getAttribute("access_level").equals("Admin"))
            {
                response.put("message", "Invalid password change request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            Admin admin=adminRepository.findAll().get(0);
            if(! passwordEncoder.matches(body.get("password"), admin.password)){
                response.put("message", "Invalid Password");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            
            String password = passwordEncoder.encode(body.get("new_password"));
            admin.password=password;
            adminRepository.save(admin);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/viewCandidateRequests")
    public ResponseEntity<Object> getCampaignRequests(HttpSession session) {

        try{
            String roll_no = utils.isLoggedIn(session);
            Map<String,String> response = new HashMap<>();

            if(roll_no == null || !session.getAttribute("access_level").equals("Admin"))
            {
                response.put("message", "Admin access required");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }

            List<Map<String, String>> requests = adminService.viewCandidateRequests();
            return new ResponseEntity<Object>(requests, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("/acceptCandidate")
    public ResponseEntity<Object> acceptCandidate(@RequestBody Map<String,String> body,HttpSession session) {
        Map<String,String> response=new HashMap<>();
        try{
            String roll_no=utils.isLoggedIn(session);
            if( roll_no==null || !session.getAttribute("access_level").equals("Admin"))
            {
                response.put("message", "Invalid acceptance request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
    
            adminService.addCandidate(body.get("roll_no"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/rejectCandidate")
    public ResponseEntity<Object> rejectCandidate(@RequestBody Map<String,String> body,HttpSession session) {
        Map<String,String> response=new HashMap<>();
        try{
            String roll_no=utils.isLoggedIn(session);
            if( roll_no==null || !session.getAttribute("access_level").equals("Admin"))
            {
                response.put("message", "Invalid rejection request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
    
            adminService.rejectCandidate(body.get("roll_no"),body.get("description"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } 
    }
    
    @PostMapping("/addannouncement")
    public ResponseEntity<Object> addAnnouncement(@RequestBody Map<String, String> body, HttpSession session){
        Map<String,String> response = new HashMap<>();
        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Admin")){
                response.put("message", "Invalid add announcement request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            response.put("pk",Integer.toString(adminService.add_announcement(body.get("announcement"))));
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/removeannouncement")
    public ResponseEntity<Object> remove_form_link(@RequestBody Map<String, String> body, HttpSession session){
        Map<String,String> response = new HashMap<>();
        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Admin")){
                response.put("message", "Invalid remove announcement request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            adminService.remove_announcement(body.get("announcement"));
    
           return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/viewmyannouncements")
    public ResponseEntity<Object> view_my_forms(@RequestBody Map<String, String> body, HttpSession session){
        Map<String,String> response = new HashMap<>();
        try{
            String roll_no = utils.isLoggedIn(session);
            if(roll_no == null || !session.getAttribute("access_level").equals("Admin")){
                response.put("message", "Invalid view request");
                return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
            }
            List <String> announcements = adminService.view_announcements(); 
            for (Integer i = 0; i < announcements.size(); ++i){
                response.put("announcement" + i.toString(), announcements.get(i));
            }
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
        catch(Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
