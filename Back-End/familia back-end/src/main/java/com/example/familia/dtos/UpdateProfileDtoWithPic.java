package com.example.familia.dtos;

public class UpdateProfileDtoWithPic {
	
	private String displayName;
	
	private String bio;
	
	private String location;
	
	private String hobbies;
	
	private String profilePicName;
	
	private int userProfileId;

	public UpdateProfileDtoWithPic(String displayName, String bio, String location, String hobbies,
			String profilePicName, int userProfileId) {
		super();
		this.displayName = displayName;
		this.bio = bio;
		this.location = location;
		this.hobbies = hobbies;
		this.profilePicName = profilePicName;
		this.userProfileId = userProfileId;
	}

	public UpdateProfileDtoWithPic() {
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

	public String getProfilePicName() {
		return profilePicName;
	}

	public void setProfilePicName(String profilePicName) {
		this.profilePicName = profilePicName;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	@Override
	public String toString() {
		return "UpdateProfileDtoWithPic [displayName=" + displayName + ", bio=" + bio + ", location=" + location
				+ ", hobbies=" + hobbies + ", profilePicName=" + profilePicName + ", userProfileId=" + userProfileId
				+ "]";
	}

}
