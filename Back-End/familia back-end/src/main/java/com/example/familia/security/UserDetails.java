package com.example.familia.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.familia.entities.LogIn;
import com.example.familia.repositories.LogInRepository;

@Service
public class UserDetails implements UserDetailsService {
	
	@Autowired
	private LogInRepository logInRepo;

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Optional<LogIn> validUser = logInRepo.findByUsername(username);
		
		if(validUser.isPresent()) {
			LogIn user = validUser.get();
			
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			return new User(user.getUsername(),user.getPassword(),authorities);
		}
		
		throw new BadCredentialsException("Invalid User");
	}

}
