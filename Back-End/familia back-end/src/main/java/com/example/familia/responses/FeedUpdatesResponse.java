package com.example.familia.responses;

public class FeedUpdatesResponse {
	
	private String message;

	public FeedUpdatesResponse(String message) {
		super();
		this.message = message;
	}

	public FeedUpdatesResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "FeedUpdatesResponse [message=" + message + "]";
	}

}
