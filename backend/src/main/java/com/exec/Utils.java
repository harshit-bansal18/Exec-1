package com.exec;

import java.util.*;

public class Utils {
    
    public String otpGenerator(){
        String otp = "";
        Random random = new Random();
        for(int i=0;i<6;i++){
            otp += random.nextInt(10);
        }
        return otp;
    }
}
