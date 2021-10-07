package com.security.learn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security.learn.model.User;
import com.security.learn.repository.UserRepository;

@SpringBootApplication
public class SpringBootSecurityLearnApplication implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityLearnApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("ambadas",
				bCryptPasswordEncoder.encode("password"),
				"ROLE_OFFICE_ADMIN"));
		
		userRepository.save(new User("anitha",
				bCryptPasswordEncoder.encode("password"),
				"ROLE_STUDENT"));
	}

}
