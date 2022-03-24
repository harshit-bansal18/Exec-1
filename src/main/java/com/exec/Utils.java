package com.exec;

import java.util.*;

import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

public class Utils {

    public String isLoggedIn(HttpSession session){
        try{
            String roll_no = (String) session.getAttribute("roll_no");
            return roll_no;
        }
        catch(Exception E){
            return null;
        }
    }

    public String isLoggedInUnverified(HttpSession session){
        try{
            String roll_no = (String) session.getAttribute("unverified_roll_no");
            return roll_no;
        }
        catch(Exception E){
            return null;
        }
    }
    
    public String otpGenerator(){
        String otp = "";
        Random random = new Random();
        for(int i=0;i<6;i++){
            otp += random.nextInt(10);
        }
        return otp;
    }

    public static <T> List<T> removeDuplicates(List<T> list){
        
        List<T> newList = list.stream().distinct().collect(Collectors.toList());
        return newList;
    }
}
