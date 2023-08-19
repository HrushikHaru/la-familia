package com.example.familia.dtos;

public class FriendRequestDto {
	
	private int requestById;
	
	private String requestByName;
	
	private String base64ProfilePicture;

	public FriendRequestDto() {
		super();
	}

	public FriendRequestDto(int requestById, String requestByName,
			String base64ProfilePicture) {
		super();
		this.requestById = requestById;
		this.requestByName = requestByName;
		this.base64ProfilePicture = base64ProfilePicture;
	}

	public int getRequestById() {
		return requestById;
	}

	public void setRequestById(int requestById) {
		this.requestById = requestById;
	}

	public String getRequestByName() {
		return requestByName;
	}

	public void setRequestByName(String requestByName) {
		this.requestByName = requestByName;
	}

	public String getBase64ProfilePicture() {
		return base64ProfilePicture;
	}

	public void setBase64ProfilePicture(String base64ProfilePicture) {
		this.base64ProfilePicture = base64ProfilePicture;
	}

	@Override
	public String toString() {
		return "FriendRequestDto [requestById=" + requestById + ", requestByName=" + requestByName
				+ ", base64ProfilePicture=" + base64ProfilePicture + "]";
	}

}
