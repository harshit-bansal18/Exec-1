package com.exec.model;

import org.springframework.data.annotation.Id;
// import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Key")
public class Key {
    @Id
    public String roll;
    public String publicKey;
    // public String salt;
    public String encryptedPriv;
    public Key (String roll1, String publicKey1, String encryptedPriv1){
        roll= roll1;
        publicKey = publicKey1;
        encryptedPriv = encryptedPriv1;
    }
    public String getPublicKey() {
        return publicKey;
    }

    // public
    
}
