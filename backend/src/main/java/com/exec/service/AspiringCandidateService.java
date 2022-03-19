package com.exec.service;
import com.exec.repository.AspiringCandidateRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import com.exec.Utils;

import com.exec.model.AspiringCandidate;
import com.exec.model.GBM;

@Lazy
@Service
public class AspiringCandidateService{
    
    private final GBMService gbmService;
    private final AspiringCandidateRepository aspiringCandidateRepository;

    public AspiringCandidateService(GBMService gbmService, AspiringCandidateRepository aspiringCandidateRepository) {
        this.gbmService = gbmService;
        this.aspiringCandidateRepository = aspiringCandidateRepository;
    }


    public void addAspiringCandidate(AspiringCandidate aspiringcandidate){
        aspiringCandidateRepository.insert(aspiringcandidate);
    }

     public void applyCandidature(String roll_no_gbm, List<String> Seconders, List<String> Proposers, String manifesto, String post){
        GBM gbm = gbmService.getGBMByRoll(roll_no_gbm);

        Seconders = Utils.removeDuplicates(Seconders);
        Proposers = Utils.removeDuplicates(Proposers);
        
        for(String roll_no : Seconders){
            if(!gbm.is_campaigner){
                gbmService.setIsCampaigner(roll_no);
            }else{
                throw new RuntimeException();
            }
        }

        for(String roll_no : Proposers){
            if(!gbm.is_campaigner){
                gbmService.setIsCampaigner(roll_no);
            }else{
                throw new RuntimeException();
            }
        }

        if(gbm.applied_for_candidature || Seconders.size() < 2 || Proposers.size() < 1 || manifesto == null || post == null){
            throw new RuntimeException();
        }else{
            AspiringCandidate aspiringcandidate = new AspiringCandidate(gbm.roll_no, gbm.name, gbm.email, Seconders, Proposers, manifesto, post);
            addAspiringCandidate(aspiringcandidate);
        }
    }

    public List<AspiringCandidate> viewAllAspiringCandidates(){
        List<AspiringCandidate> allAspiringCandidate = aspiringCandidateRepository.findAll();
        return allAspiringCandidate;
    }

    public AspiringCandidate getAspiringCandidateByRoll(String roll){
        return aspiringCandidateRepository.findById(roll)
                .orElseThrow(() -> new RuntimeException("No Aspiring Candidate found with roll_no: " + roll));
    }

    public void deleteCandidature(String roll){
        AspiringCandidate aspiringCandidate = getAspiringCandidateByRoll(roll);
        aspiringCandidateRepository.delete(aspiringCandidate);
    }
}
