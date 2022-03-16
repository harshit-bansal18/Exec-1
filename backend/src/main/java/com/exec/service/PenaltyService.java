package com.exec.service;

import com.exec.repository.PenaltyRepository;

import com.opencsv.*;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.exec.model.Penalty;
import java.util.*;
import java.io.*; 

@Lazy
@Service
public class PenaltyService {
    
    private final PenaltyRepository penaltyRepository;
    private String CSVFile_path;

    public PenaltyService(PenaltyRepository PR, String CSVFile){
        this.penaltyRepository = PR;
        this.CSVFile_path = CSVFile;

        File file = new File(this.CSVFile_path);
        
        try {
            FileWriter file_writer = new FileWriter(file);

            CSVWriter writer = new CSVWriter(file_writer);
    
            String[] header = { "ID", "Role", "Name", "Roll No.", "Fine (in Rs.)", "Penalty Level", "Section of CoC Violated", "Remarks" };
            writer.writeNext(header);

            writer.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void addPenalty(Penalty penalty){
        String[] pen = {penalty.id, penalty.role, penalty.name, penalty.roll_no, penalty.fine, penalty.level, penalty.part, penalty.remark};

        File file = new File(this.CSVFile_path);
        try {
            penaltyRepository.insert(penalty);
            FileWriter file_writer = new FileWriter(file);
            CSVWriter writer = new CSVWriter(file_writer);
            writer.writeNext(pen);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void removePenalty(Penalty penalty){
        try {
            List<String[]> allPenalties = this.getAllPenalties();

            File file = new File(this.CSVFile_path);
            if(file.delete()){
                try {
                    FileWriter file_writer = new FileWriter(file);
                    CSVWriter writer = new CSVWriter(file_writer);
                    String[] header = { "ID", "Role", "Name", "Roll No.", "Fine (in Rs.)", "Penalty Level", "Section of CoC Violated", "Remarks" };
                    writer.writeNext(header);
            
                    for(String[] pen : allPenalties){
                        if(pen[0] == penalty.id){
                            continue;
                        }else{
                            writer.writeNext(pen);
                        }
                    }

                    writer.close();
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }else{
                throw new RuntimeException();
            }

            penaltyRepository.delete(penalty);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<String[]> getAllPenalties(){
        try {
            FileReader filereader = new FileReader(this.CSVFile_path);
    
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                                    .withSkipLines(1)
                                    .build();
            return csvReader.readAll();
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    } 

    public void copyToFile(String newFile){
        List<String[]> allPenalties = this.getAllPenalties();
        File file = new File(newFile);

        try {
            FileWriter file_writer = new FileWriter(file);
            CSVWriter writer = new CSVWriter(file_writer);
            String[] header = { "ID", "Role", "Name", "Roll No.", "Fine (in Rs.)", "Penalty Level", "Section of CoC Violated", "Remarks" };
            writer.writeNext(header);
            writer.writeAll(allPenalties);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    
    public void updateFilePath(String newFile){
        this.copyToFile(newFile);
        File file = new File(this.CSVFile_path);
        if(file.delete()){
            this.CSVFile_path = newFile;
        }else{
            throw new RuntimeException();
        }
    }


}