package com.exec.service;

import com.exec.repository.KeyRepository;
import com.exec.repository.ReportRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.exec.model.Key;
import com.exec.model.Report;


@Service
public class ReportService {
    
    private final ReportRepository reportRepository;
    private final KeyRepository keyRepository;


    public ReportService(ReportRepository reportRepository,KeyRepository keyRepository) {
        this.reportRepository = reportRepository;
        this.keyRepository = keyRepository;
    }

    public void addReport(Report Report) {
        reportRepository.insert(Report);
    }
    public List<String> publicKeys() {
        List<String> myList = new ArrayList<String>();
        for(Key key: keyRepository.findAll()){
            myList.add(key.getPublicKey());
        }
        // for i  keyRepository.findAll();
        return myList;

    }
    
    public Key getPriv(String roll ) {
        return keyRepository.findById(roll).get();

    }

}


