package com.example.familia.dtos;

public class UserProfileIdOfFriendRequest {
	
	private int userProfileId;

	public UserProfileIdOfFriendRequest() {
		super();
	}

	public UserProfileIdOfFriendRequest(int userProfileId) {
		super();
		this.userProfileId = userProfileId;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	@Override
	public String toString() {
		return "UserProfileIdOfFriendRequest [userProfileId=" + userProfileId + "]";
	}

}
