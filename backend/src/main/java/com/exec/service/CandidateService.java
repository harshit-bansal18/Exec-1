package com.exec.service;

import com.exec.model.Candidate;
import com.exec.model.GBM;
import com.exec.model.Admin;
import com.exec.repository.CandidateRepository;
import com.exec.repository.GBMRepository;
import com.exec.repository.AdminRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final GBMRepository gbmRepository;
    private final AdminRepository adminRepository;

    public CandidateService(CandidateRepository candidateRepository, GBMRepository gbmRepository,AdminRepository adminRepository){
        this.candidateRepository = candidateRepository;
        this.gbmRepository = gbmRepository;
        this.adminRepository=adminRepository;
    }

    public void fileNomination(Candidate candidate){
        //candidateRepository.insert(candidate);
        Admin admin;
        admin=adminRepository.findAll().get(0);
        admin.CandidateRequests.put(candidate.roll_no,candidate);
        adminRepository.save(admin);
    }

    public Candidate getCandidateByRoll(String roll_no) {
        return candidateRepository.findById(roll_no)
                .orElseThrow(() -> new RuntimeException("No Candidate found with roll_no: " + roll_no));
    }

    public void activateCandidate(String roll_no, String password) {
        Candidate candidate = getCandidateByRoll(roll_no);
        candidate.is_activated = true;
        candidate.password = password;
        candidateRepository.save(candidate);
    }

    public void setOtp(String roll_no, String otp) {
        Candidate candidate = getCandidateByRoll(roll_no);
        candidate.otp = otp;
        candidateRepository.save(candidate);
    }

    public void addCampaigner(String roll_no_candidate, String roll_no_gbm){
        
        Candidate candidate = getCandidateByRoll(roll_no_candidate);
        GBM gbm = getGBMByRoll(roll_no_gbm);

        if(!gbm.is_campaigner){
            candidate.Campaigners.add(roll_no_gbm);
            candidateRepository.save(candidate);
        }else{
            throw new RuntimeException();
        }
    }

    // public void addSeconder(String roll_no_candidate, String roll_no_gbm){
        
    //     Candidate candidate = getCandidateByRoll(roll_no_candidate);
    //     GBM gbm = gbmService.getGBMByRoll(roll_no_gbm);

    //     if(!gbm.is_campaigner){
    //         gbmService.setIsCampaigner(roll_no_gbm);
    //         candidate.Seconders.add(roll_no_gbm);
    //         candidate.Campaigners.add(roll_no_gbm);
    //         candidateRepository.save(candidate);
    //     }else{
    //         throw new RuntimeException();
    //     }
    // }

    // public void addProposer(String roll_no_candidate, String roll_no_gbm){
    //     Candidate candidate = getCandidateByRoll(roll_no_candidate);
    //     GBM gbm = gbmService.getGBMByRoll(roll_no_gbm);

    //     if(!gbm.is_campaigner){
    //         gbmService.setIsCampaigner(roll_no_gbm);
    //         candidate.Proposers.add(roll_no_gbm);
    //         candidate.Campaigners.add(roll_no_gbm);
    //         candidateRepository.save(candidate);
    //     }else{
    //         throw new RuntimeException();
    //     }
    // }

    public GBM getGBMByRoll(String roll_no) {
        return gbmRepository.findById(roll_no)
                .orElseThrow(() -> new RuntimeException("No GBM found with roll_no: " + roll_no));
    }

}
