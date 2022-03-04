package com.exec.service;

import com.exec.repository.ReportRepository;

import org.springframework.stereotype.Service;
import com.exec.model.Report;

@Service
public class ReportService {
    
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void addReport(Report Report) {
        reportRepository.insert(Report);
    }

  



}
