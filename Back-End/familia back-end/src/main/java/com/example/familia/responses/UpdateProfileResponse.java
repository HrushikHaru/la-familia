package com.example.familia.responses;

public class UpdateProfileResponse {
	
	private String response;
	
	private String updatedOn;

	public UpdateProfileResponse(String response, String updatedOn) {
		super();
		this.response = response;
		this.updatedOn = updatedOn;
	}

	public UpdateProfileResponse() {
		super();
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "UpdateProfileResponse [response=" + response + ", updatedOn=" + updatedOn + "]";
	}

}
