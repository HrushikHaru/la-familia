package com.example.familia.responses;

import java.util.Date;

public class UserProfileUploadResponse {
	
	private String responseMessage;
	
	private Date publishedDate;

	public UserProfileUploadResponse() {
		super();
	}

	public UserProfileUploadResponse(String responseMessage, Date publishedDate) {
		super();
		this.responseMessage = responseMessage;
		this.publishedDate = publishedDate;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	@Override
	public String toString() {
		return "UserProfileUploadResponse [responseMessage=" + responseMessage + ", publishedDate=" + publishedDate
				+ "]";
	}

}
