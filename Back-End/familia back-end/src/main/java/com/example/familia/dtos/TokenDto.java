package com.example.familia.dtos;

public class TokenDto {
	
	private String firstName;
	
	private String token;
	
	private int userId;

	public TokenDto() {
		super();
	}

	public TokenDto(String firstName, String token, int userId) {
		super();
		this.firstName = firstName;
		this.token = token;
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TokenDto [firstName=" + firstName + ", token=" + token + ", userId=" + userId + "]";
	}
	
}
