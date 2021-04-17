package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredential(CredentialForm credentialForm) {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);

        Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), encodedKey,
                encryptionService.encryptValue(credentialForm.getPassword(), encodedKey), Integer.parseInt(credentialForm.getUserid()));
        credentialsMapper.addCredential(credential);

        System.out.println("Credential was added to the Database  " + credential.getCredentialid());

    }

    public List<Credential> getCredentials(int userId) {
        return credentialsMapper.getAllCredentials(userId);
    }

    public void updateCredential(CredentialForm credentialForm, int userId) {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);

        Credential credential = new Credential(Integer.parseInt(credentialForm.getCredentialid()),
                credentialForm.getUrl(), credentialForm.getUsername(), encodedKey,
                encryptionService.encryptValue(credentialForm.getPassword(), encodedKey), userId);


        credentialsMapper.update(credential);


    }
}
