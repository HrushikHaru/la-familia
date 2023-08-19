package com.example.familia.responses;

import java.util.Date;

public class PostCreatedResponse {
	
	private String message;
	
	private Date postedOn;

	public PostCreatedResponse(String message, Date postedOn) {
		super();
		this.message = message;
		this.postedOn = postedOn;
	}

	public PostCreatedResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public String toString() {
		return "PostCreatedResponse [message=" + message + ", postedOn=" + postedOn + "]";
	}

}
