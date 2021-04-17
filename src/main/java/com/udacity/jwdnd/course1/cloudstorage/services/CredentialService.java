package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    
    private CredentialsMapper credentialsMapper;

    public CredentialService(CredentialsMapper credentialsMapper) {
        this.credentialsMapper = credentialsMapper;
    }

    public void addCredential(CredentialForm credentialForm) {
        Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), credentialForm.getKey(),
                credentialForm.getPassword(), Integer.parseInt(credentialForm.getUserid()));
        credentialsMapper.addCredential(credential);

        System.out.println("Credential was added to the Database  " + credential.getCredentialid());

    }

    public List<Credential> getCredentials(int userId) {
        return credentialsMapper.getAllCredentials(userId);
    }
}
