package com.example.familia.responses;

import java.util.Date;

public class LikedPostResponse {
	
	private String message;
	
	private Date likedOn;

	public LikedPostResponse(String message, Date likedOn) {
		super();
		this.message = message;
		this.likedOn = likedOn;
	}

	public LikedPostResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getLikedOn() {
		return likedOn;
	}

	public void setLikedOn(Date likedOn) {
		this.likedOn = likedOn;
	}

	@Override
	public String toString() {
		return "LikedPostResponse [message=" + message + ", likedOn=" + likedOn + "]";
	}

}
