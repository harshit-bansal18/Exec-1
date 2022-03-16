package com.exec.controller;

// import java.util.Map;
import com.exec.service.CandidateService;
import com.exec.service.GBMService;

// import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Integral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.exec.model.*;
@RestController
public class PublicController {
    
    private final CandidateService candidateservice;
    // private final GBMService gbmservice;

    public PublicController(CandidateService candidateservice, GBMService gbmservice) {
        this.candidateservice = candidateservice;
        // this.gbmservice = gbmservice;
    }

    @GetMapping("/viewCandidates")
    public ResponseEntity<Object> viewCandidates(){
        return new ResponseEntity<Object>(candidateservice.getAllCandidatesBasicInfo(), HttpStatus.OK);
    }

    @GetMapping("/viewCandidate")
    public ResponseEntity<Object> viewCandidate(@RequestParam(value = "roll") String roll){
        Candidate candidate = candidateservice.getCandidateByRoll(roll);
        Map<String, String> candidateInfo = new HashMap<String, String>();

        candidateInfo.put("name", candidate.name);
        candidateInfo.put("post", candidate.post);
        candidateInfo.put("email", candidate.email);
        candidateInfo.put("manifesto", candidate.manifesto_link);

        Integer listSize = candidate.Proposers.size();
        candidateInfo.put("num_proposers", Integer.toString(listSize));
        listSize = candidate.Seconders.size();
        candidateInfo.put("num_seconders", Integer.toString(listSize));
        listSize = candidate.video_links.size();
        candidateInfo.put("num_video_links", Integer.toString(listSize));
        listSize = candidate.poster_links.size();
        candidateInfo.put("num_poster_links", Integer.toString(listSize));

        String temp;
        // * Adding all the proposers, seconders, links 
        temp = "proposer";
        for (int i = 0; i < candidate.Proposers.size(); i++) {
            candidateInfo.put(temp + Integer.toString(i+1), candidate.Proposers.get(i));
        }

        temp = "seconder";
        for (int i = 0; i < candidate.Seconders.size(); i++) {
            candidateInfo.put(temp + Integer.toString(i+1), candidate.Seconders.get(i));
        }

        temp = "videolink";
        for (int i = 0; i < candidate.video_links.size(); i++) {
            candidateInfo.put(temp + Integer.toString(i+1), candidate.video_links.get(i));
        }
        
        temp = "posterlink";
        for (int i = 0; i < candidate.poster_links.size(); i++) {
            candidateInfo.put(temp + Integer.toString(i+1), candidate.poster_links.get(i));
        }
        return  new ResponseEntity<Object>(candidateInfo, HttpStatus.OK);
    }


}
