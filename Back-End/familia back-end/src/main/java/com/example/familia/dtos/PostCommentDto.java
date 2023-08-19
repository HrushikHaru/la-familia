package com.example.familia.dtos;

public class PostCommentDto {
	
	private int postId;
	
	private String commentCaption;
	
	private int userProfileId;
	
	private String userProfileDisplayName;

	public PostCommentDto(int postId, String commentCaption, int userProfileId, String userProfileDisplayName
			) {
		super();
		this.postId = postId;
		this.commentCaption = commentCaption;
		this.userProfileId = userProfileId;
		this.userProfileDisplayName = userProfileDisplayName;
	}

	public PostCommentDto() {
		super();
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getCommentCaption() {
		return commentCaption;
	}

	public void setCommentCaption(String commentCaption) {
		this.commentCaption = commentCaption;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getUserProfileDisplayName() {
		return userProfileDisplayName;
	}

	public void setUserProfileDisplayName(String userProfileDisplayName) {
		this.userProfileDisplayName = userProfileDisplayName;
	}

	@Override
	public String toString() {
		return "PostCommentDto [postId=" + postId + ", commentCaption=" + commentCaption + ", userProfileId="
				+ userProfileId + ", userProfileDisplayName=" + userProfileDisplayName + "]";
	}

}
