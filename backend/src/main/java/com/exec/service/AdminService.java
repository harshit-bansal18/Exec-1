package com.exec.service;

import java.io.*;
import java.net.*;
import java.util.*;

import com.exec.model.Admin;
import com.exec.model.Candidate;
import com.exec.model.GBM;
import com.exec.repository.AdminRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Lazy
@Service
public class AdminService {
    
    private final AdminRepository adminRepository;
    private final GBMService gbmService;

    public AdminService(AdminRepository adminRepository, GBMService gbmService) {
        this.adminRepository = adminRepository;
        this.gbmService = gbmService;
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

    public void populate() {
	
	    HttpURLConnection conn = null;
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		try{
			URL url = new URL("https://search.pclub.in/api/students");
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			int status = conn.getResponseCode();
			
			if (status >= 300) {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
            JSONArray students = new JSONArray(responseContent.toString());
                for (int i = 0 ; i < students.length(); i++) {
                    JSONObject student = students.getJSONObject(i);			
                    String roll_no = student.getString("i").trim();
                    String name = student.getString("n").trim();
                    String email = student.getString("u").trim()+"@iitk.ac.in";
                    GBM gbm = new GBM(roll_no,name,email);
                    try{
                        gbmService.addGBM(gbm);
                    }
                    catch(Exception e){
                        System.out.println(e);
                        System.out.println(roll_no);
                        continue;
                    }

                }
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			conn.disconnect();
		}
			
    }
}
