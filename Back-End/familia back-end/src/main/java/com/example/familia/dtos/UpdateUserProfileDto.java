package com.example.familia.dtos;

public class UpdateUserProfileDto {
	
	private String displayName;
	
	private String bio;
	
	private String location;
	
	private String hobbies;
	
	private int userProfileId;

	public UpdateUserProfileDto(String displayName, String bio, String location, String hobbies, int userProfileId) {
		super();
		this.displayName = displayName;
		this.bio = bio;
		this.location = location;
		this.hobbies = hobbies;
		this.userProfileId = userProfileId;
	}

	public UpdateUserProfileDto() {
		super();
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

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	@Override
	public String toString() {
		return "UpdateUserProfileDto [displayName=" + displayName + ", bio=" + bio + ", location=" + location
				+ ", hobbies=" + hobbies + ", userProfileId=" + userProfileId + "]";
	}

}
