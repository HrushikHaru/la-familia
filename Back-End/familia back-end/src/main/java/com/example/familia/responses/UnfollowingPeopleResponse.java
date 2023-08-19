package com.example.familia.responses;

import java.util.Date;

public class UnfollowingPeopleResponse {
	
	private String response;
	
	private String unfollowedOn;

	public UnfollowingPeopleResponse(String response, String unfollowedOn) {
		super();
		this.response = response;
		this.unfollowedOn = unfollowedOn;
	}

	public UnfollowingPeopleResponse() {
		super();
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUnfollowedOn() {
		return unfollowedOn;
	}

	public void setUnfollowedOn(String unfollowedOn) {
		this.unfollowedOn = unfollowedOn;
	}

	@Override
	public String toString() {
		return "UnfollowingPeopleResponse [response=" + response + ", unfollowedOn=" + unfollowedOn + "]";
	}

}
