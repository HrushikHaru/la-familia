package com.example.familia.dtos;

public class LikePostDto {
	
	private int postId;
	
	private int userProfileId;
	
	private String userDisplayName;

	public LikePostDto(int postId, int userProfileId, String userDisplayName) {
		super();
		this.postId = postId;
		this.userProfileId = userProfileId;
		this.userDisplayName = userDisplayName;
	}

	public LikePostDto() {
		super();
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	@Override
	public String toString() {
		return "LikePostDto [postId=" + postId + ", userProfileId=" + userProfileId + ", userDisplayName="
				+ userDisplayName + "]";
	}

}
