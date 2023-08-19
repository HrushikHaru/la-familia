package com.example.familia.dtos;

public class UserProfileDto {
	
	private String profilePicName;
	
	private String displayName;
	
	private String bio;
	
	private String location;
	
	private String hobbies;
	
	private int userId;

	public UserProfileDto() {
		super();
	}

	public UserProfileDto(String profilePicName, String displayName, String bio, String location, String hobbies, int userId) {
		super();
		this.profilePicName = profilePicName;
		this.displayName = displayName;
		this.bio = bio;
		this.location = location;
		this.hobbies = hobbies;
		this.userId = userId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserProfileDto [profilePicName=" + profilePicName + ", displayName=" + displayName + ", bio=" + bio
				+ ", location=" + location + ", hobbies=" + hobbies + ", userId=" + userId + "]";
	}
	
}
