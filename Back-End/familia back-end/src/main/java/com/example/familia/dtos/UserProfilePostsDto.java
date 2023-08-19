package com.example.familia.dtos;

public class UserProfilePostsDto {
	
	private String postPicName;
	
	private String caption;
	
	private String postDateTime;
	
	private int userProfileId;
	
	public UserProfilePostsDto() {
		super();
	}

	public UserProfilePostsDto(String postPicName, String caption, String postDateTime, int userProfileId) {
		super();
		this.postPicName = postPicName;
		this.caption = caption;
		this.postDateTime = postDateTime;
		this.userProfileId = userProfileId;
	}

	public String getPostPicName() {
		return postPicName;
	}

	public void setPostPicName(String postPicName) {
		this.postPicName = postPicName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getPostDateTime() {
		return postDateTime;
	}

	public void setPostDateTime(String postDateTime) {
		this.postDateTime = postDateTime;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	@Override
	public String toString() {
		return "UserProfilePostsDto [postPicName=" + postPicName + ", caption=" + caption + ", postDateTime="
				+ postDateTime + ", userProfileId=" + userProfileId + "]";
	}

}
