package com.security.learn.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.learn.model.CustomUserDetails;
import com.security.learn.model.User;
import com.security.learn.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findById(username);
		if(!user.isPresent())
			throw new UsernameNotFoundException("NO USER");
		return new CustomUserDetails(user.get());
	}

}
