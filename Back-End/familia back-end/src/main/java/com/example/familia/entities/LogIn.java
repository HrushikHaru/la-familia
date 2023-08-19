package com.example.familia.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class LogIn {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int logInId;
	
	private String username;
	
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "signUpId")
	private SignUp signup;
	
	@OneToOne(mappedBy = "logIn")
	private UserProfile userProfile;

	public LogIn() {
		super();
	}

	public LogIn(int logInId) {
		super();
		this.logInId = logInId;
	}

	public LogIn(int logInId, String username, String password, SignUp signup) {
		super();
		this.logInId = logInId;
		this.username = username;
		this.password = password;
		this.signup = signup;
	}

	public LogIn(String username, String password, SignUp signup) {
		super();
		this.username = username;
		this.password = password;
		this.signup = signup;
	}

	public int getLogInId() {
		return logInId;
	}

	public void setLogInId(int logInId) {
		this.logInId = logInId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SignUp getSignup() {
		return signup;
	}

	public void setSignup(SignUp signup) {
		this.signup = signup;
	}

	@Override
	public String toString() {
		return "LogIn [logInId=" + logInId + ", username=" + username + ", password=" + password + ", signup=" + signup
				+ "]";
	}
	

}
