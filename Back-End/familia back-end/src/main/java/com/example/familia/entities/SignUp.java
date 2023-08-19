package com.example.familia.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class SignUp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int signUpId;
	
	private String firtName;
	
	private String lastName;
	
	@Column(columnDefinition = "DATE")
	private Date dateOfBirth;
	
	private int age;
	
	@OneToOne(mappedBy = "signup",cascade = CascadeType.ALL)
	private LogIn login;

	public SignUp() {
		super();
	}

	public SignUp(int signUpId, String firtName, String lastName, Date dateOfBirth, int age, LogIn login) {
		super();
		this.signUpId = signUpId;
		this.firtName = firtName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.login = login;
	}

	public SignUp(String firtName, String lastName, Date dateOfBirth, int age) {
		super();
		this.firtName = firtName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
	}

	public int getSignUpId() {
		return signUpId;
	}

	public void setSignUpId(int signUpId) {
		this.signUpId = signUpId;
	}

	public String getFirtName() {
		return firtName;
	}

	public void setFirtName(String firtName) {
		this.firtName = firtName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LogIn getLogin() {
		return login;
	}

	public void setLogin(LogIn login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "SignUp [signUpId=" + signUpId + ", firtName=" + firtName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", age=" + age + ", login=" + login + "]";
	}
	
}
