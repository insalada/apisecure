package com.ipbsoft.repositories;

import com.ipbsoft.beans.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CredentialRepository extends MongoRepository<Credential, String> {

    public Credential save(Credential credential);

    public Credential findByName(String name);

    public List<Credential> findAll();

}
