package com.exec.service;

import com.exec.model.Candidate;
import com.exec.model.GBM;
import com.exec.repository.CandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final GBMService gbmService;

    public CandidateService(CandidateRepository candidateRepository, GBMService gbmService){
        this.candidateRepository = candidateRepository;
        this.gbmService = gbmService;
    }

    public void addCandidate(Candidate candidate){
        candidateRepository.insert(candidate);
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
        GBM gbm = gbmService.getGBMByRoll(roll_no_gbm);

        if(!gbm.is_campaigner){
            candidate.Campaigners.add(roll_no_gbm);
            candidateRepository.save(candidate);
        }else{
            throw new RuntimeException();
        }
    }

    public void addSeconder(String roll_no_candidate, String roll_no_gbm){
        
        Candidate candidate = getCandidateByRoll(roll_no_candidate);
        GBM gbm = gbmService.getGBMByRoll(roll_no_gbm);

        if(!gbm.is_campaigner){
            gbmService.setIsCampaigner(roll_no_gbm);
            candidate.Seconders.add(roll_no_gbm);
            candidate.Campaigners.add(roll_no_gbm);
            candidateRepository.save(candidate);
        }else{
            throw new RuntimeException();
        }
    }

    public void addProposer(String roll_no_candidate, String roll_no_gbm){
        Candidate candidate = getCandidateByRoll(roll_no_candidate);
        GBM gbm = gbmService.getGBMByRoll(roll_no_gbm);

        if(!gbm.is_campaigner){
            gbmService.setIsCampaigner(roll_no_gbm);
            candidate.Proposers.add(roll_no_gbm);
            candidate.Campaigners.add(roll_no_gbm);
            candidateRepository.save(candidate);
        }else{
            throw new RuntimeException();
        }
    }

}
