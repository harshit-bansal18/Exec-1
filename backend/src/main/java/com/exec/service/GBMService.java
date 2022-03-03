package com.exec.service;

import com.exec.repository.GBMRepository;

import org.springframework.stereotype.Service;
import com.exec.model.GBM;

@Service
public class GBMService {
    
    private final GBMRepository gbmRepository;

    public GBMService(GBMRepository gbmRepository) {
        this.gbmRepository = gbmRepository;
    }

    public void addGBM(GBM gbm) {
        gbmRepository.insert(gbm);
    }

    public GBM getGBMByRoll(String roll_no) {
        return gbmRepository.findById(roll_no)
                .orElseThrow(() -> new RuntimeException("No GBM found with roll_no: " + roll_no));
    }

    public void activateGBM(String roll_no, String password) {
        GBM gbm = getGBMByRoll(roll_no);
        gbm.is_activated = true;
        gbm.password = password;
        gbmRepository.save(gbm);
    }

    public void setOtp(String roll_no, String otp) {
        GBM gbm = getGBMByRoll(roll_no);
        gbm.otp = otp;
        gbmRepository.save(gbm);
    }

}
