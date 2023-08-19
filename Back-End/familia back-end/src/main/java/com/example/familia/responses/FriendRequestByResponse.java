package com.example.familia.responses;

import java.util.Date;

public class FriendRequestByResponse {
	
	private String message;
	
	private Date requestSentOn;

	public FriendRequestByResponse() {
		super();
	}

	public FriendRequestByResponse(String message, Date requestSentOn) {
		super();
		this.message = message;
		this.requestSentOn = requestSentOn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getRequestSentOn() {
		return requestSentOn;
	}

	public void setRequestSentOn(Date requestSentOn) {
		this.requestSentOn = requestSentOn;
	}

	@Override
	public String toString() {
		return "FriendRequestByResponse [message=" + message + ", requestSentOn=" + requestSentOn + "]";
	}

}
