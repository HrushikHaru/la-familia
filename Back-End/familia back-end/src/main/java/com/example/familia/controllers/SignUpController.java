package com.example.familia.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.familia.dtos.SignUpDto;
import com.example.familia.dtos.TokenDto;
import com.example.familia.entities.LogIn;
import com.example.familia.repositories.LogInRepository;
import com.example.familia.security.JWTTokenGenerator;
import com.example.familia.services.SignUpService;


@RestController
public class SignUpController {
	
	private SignUpService signUpServ;
	
	private LogInRepository logInRepo;
	
	private JWTTokenGenerator token;
	
	public SignUpController(SignUpService signUpServ,LogInRepository logInRepo, JWTTokenGenerator token) {
		super();
		this.signUpServ = signUpServ;
		this.logInRepo = logInRepo;
		this.token = token;
	}
	
	@PostMapping(path = "/signup")
	public ResponseEntity signUp(@RequestBody SignUpDto signUp) {
		signUpServ.signIn(signUp);
		
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping(path="/signin")
	public ResponseEntity<TokenDto> signIn(Authentication auth) {
		TokenDto tokenSend = null;
		try {
		Optional<LogIn> user = logInRepo.findByUsername(auth.getName());
		
		if(user.isPresent()) {
			
			String firstName =user.get().getSignup().getFirtName();
			
			String tokenJWT = token.receiveToken();
			
			int userId = user.get().getLogInId();
			
			tokenSend = new TokenDto();
			tokenSend.setToken(tokenJWT);
			tokenSend.setFirstName(firstName);
			tokenSend.setUserId(userId);
			
			return new ResponseEntity<TokenDto>(tokenSend,HttpStatus.OK);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<TokenDto>(tokenSend,HttpStatus.BAD_REQUEST);
		
	}

}
