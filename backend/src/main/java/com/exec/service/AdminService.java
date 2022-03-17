package com.exec.service;

import com.exec.EmailServiceImpl;

import java.util.*;

import com.exec.model.Admin;
import com.exec.model.Candidate;
import com.exec.repository.AdminRepository;
import com.exec.repository.CandidateRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Lazy
@Service
public class AdminService {
    
    private final AdminRepository adminRepository;
    private final CandidateRepository candidateRepository;
    private EmailServiceImpl emailSender= new EmailServiceImpl();

    public AdminService(AdminRepository adminRepository,CandidateRepository candidateRepository){
        this.adminRepository=adminRepository;
        this.candidateRepository=candidateRepository;
    }

    public List<Map<String, String>> viewCandidateRequests(){

        List<Map<String, String>> candidates = new ArrayList<Map<String, String>>();

        Admin admin=adminRepository.findAll().get(0);
        
        for(Map.Entry<String,Candidate> entry : admin.CandidateRequests.entrySet()){
            Candidate candidate = entry.getValue();
            Map<String, String> candidate_details = new HashMap<String, String>();
            candidate_details.put("name", candidate.name);
            candidate_details.put("roll_no", candidate.roll_no);
            candidate_details.put("email", candidate.email);
            candidate_details.put("post", candidate.post);
            candidates.add(candidate_details);
        }

        return candidates;
    }
    
    public void addCandidate(String roll_no){
        candidateRepository.insert(getCandidatebyRoll(roll_no));
        Admin admin=adminRepository.findAll().get(0);
        //an email for acceptance will be sent here
        emailSender.sendCandidatureAcceptanceMessage(getCandidatebyRoll(roll_no).email, getCandidatebyRoll(roll_no).name);
        admin.CandidateRequests.remove(roll_no);
        adminRepository.save(admin);
        
    }

    public void rejectCandidate(String roll_no,String description){
        Admin admin=adminRepository.findAll().get(0);
        emailSender.sendCandidatureRejectionMessage(getCandidatebyRoll(roll_no).email, getCandidatebyRoll(roll_no).name, description);   
        admin.CandidateRequests.remove(roll_no);
        adminRepository.save(admin);
    }

    public Candidate getCandidatebyRoll(String roll_no){
        Admin admin=adminRepository.findAll().get(0);
        return admin.CandidateRequests.getOrDefault(roll_no,null);
    }

    public Admin returnAdmin(){
        return adminRepository.findAll().get(0);
    }

    public Integer add_announcement(String announcement){
        Admin admin=adminRepository.findAll().get(0);
        
        List<Integer> value_set = new ArrayList<Integer>();
        for (Map.Entry<String,Integer> entry : admin.Announcements.entrySet()){
            value_set.add(entry.getValue());
        }
        Integer new_pk = 0;
        for ( int i =0; i<value_set.size();++i){
            if(value_set.get(i)>new_pk){
                new_pk = value_set.get(i);
            }
        }
        new_pk = new_pk +1;
        admin.Announcements.put(announcement,new_pk);
        adminRepository.save(admin);
        return new_pk;
            // return 1;
       
    }
    public void remove_announcement(String announcement){
        Admin admin=adminRepository.findAll().get(0);
        admin.Announcements.remove(announcement);
        adminRepository.save(admin);
        
    }

    public ArrayList<String> view_announcements(){
        Admin admin=adminRepository.findAll().get(0);
        
        Set<String> key_set = admin.Announcements.keySet();
        return new ArrayList<String>(key_set);
      
    }
}
