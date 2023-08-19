package com.example.familia.responses;

public class UserProfileDetailsResponse {
	
	private int userProfileId;
	
	private String profilePicName;
	
	private String displayName;
	
	private String bio;
	
	private String location;
	
	private String hobbies;

	public UserProfileDetailsResponse() {
		super();
	}

	public UserProfileDetailsResponse(int userProfileId, String profilePicName, String displayName, String bio,
			String location, String hobbies) {
		super();
		this.userProfileId = userProfileId;
		this.profilePicName = profilePicName;
		this.displayName = displayName;
		this.bio = bio;
		this.location = location;
		this.hobbies = hobbies;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getProfilePicName() {
		return profilePicName;
	}

	public void setProfilePicName(String profilePicName) {
		this.profilePicName = profilePicName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	@Override
	public String toString() {
		return "UserProfileDetailsResponse [userProfileId=" + userProfileId + ", profilePicName=" + profilePicName
				+ ", displayName=" + displayName + ", bio=" + bio + ", location=" + location + ", hobbies=" + hobbies
				+ "]";
	}

}
