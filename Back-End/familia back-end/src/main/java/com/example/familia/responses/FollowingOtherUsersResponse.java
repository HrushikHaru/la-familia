package com.example.familia.responses;

import java.util.Date;

public class FollowingOtherUsersResponse {
	
	private boolean follows;
	
	private String askedOn;

	public FollowingOtherUsersResponse(boolean follows, String askedOn) {
		super();
		this.follows = follows;
		this.askedOn = askedOn;
	}

	public FollowingOtherUsersResponse() {
		super();
	}

	public boolean isFollows() {
		return follows;
	}

	public void setFollows(boolean follows) {
		this.follows = follows;
	}

	public String getAskedOn() {
		return askedOn;
	}

	public void setAskedOn(String askedOn) {
		this.askedOn = askedOn;
	}

	@Override
	public String toString() {
		return "FollowingOtherUsersResponse [follows=" + follows + ", askedOn=" + askedOn + "]";
	}

}
