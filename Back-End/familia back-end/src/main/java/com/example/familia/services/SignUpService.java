package com.example.familia.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.familia.dtos.SignUpDto;
import com.example.familia.entities.LogIn;
import com.example.familia.entities.SignUp;
import com.example.familia.repositories.LogInRepository;
import com.example.familia.repositories.SignUpRepository;

@Service
public class SignUpService {
	
	@Autowired
	private LogInRepository loginRepo;

	@Autowired
	private SignUpRepository signUpRepo;
	
	@Autowired
	private PasswordEncoder passwordEncode;

	public void signIn(SignUpDto signUp) {
		
		String firstName = signUp.getFirstName();
		String lastName = signUp.getLastName();
		int age = signUp.getAge();
		String dateOfBirth = signUp.getDateOfBirth();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
		Date addDate = null;
		
		try {
			addDate = dateFormat.parse(dateOfBirth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String userName = signUp.getUserName();
		
		String passWord = passwordEncode.encode(signUp.getPassWord());
		
		SignUp signUpNewUser = new SignUp(firstName,lastName,addDate,age);
		
		LogIn insertNewUser = new LogIn(userName,passWord,signUpNewUser);
		
		loginRepo.save(insertNewUser);
		
		
	}
	
	
}
