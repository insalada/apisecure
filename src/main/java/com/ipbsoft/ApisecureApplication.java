package com.ipbsoft;

import com.ipbsoft.beans.Credential;
import com.ipbsoft.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class ApisecureApplication {

	@Autowired
	CredentialRepository repository;

	public static void main(String[] args) {

		SpringApplication.run(ApisecureApplication.class, args);
	}

	@PostConstruct
	private void populateMongo() {
		System.out.println("Populating credentials repository...");
		repository.save(new Credential("ipbsoft", "ipbsoft", "ROLE_USER"));
		repository.save(new Credential("admin", "admin", "ROLE_ADMIN"));
	}
}
