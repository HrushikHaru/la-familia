package com.example.familia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PostComments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	
	private int userProfileId;
	
	private String commentCaption;
	
	private String userProfileDisplayName;
	
	@ManyToOne
	@JoinColumn(name = "postId", referencedColumnName = "postId")
	private UserProfilePosts userProfilePosts;

	public PostComments(int commentId, int userProfileId, String commentCaption, UserProfilePosts userProfilePosts) {
		super();
		this.commentId = commentId;
		this.userProfileId = userProfileId;
		this.commentCaption = commentCaption;
		this.userProfilePosts = userProfilePosts;
	}

	public PostComments() {
		super();
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getCommentCaption() {
		return commentCaption;
	}

	public void setCommentCaption(String commentCaption) {
		this.commentCaption = commentCaption;
	}

	public UserProfilePosts getUserProfilePosts() {
		return userProfilePosts;
	}

	public void setUserProfilePosts(UserProfilePosts userProfilePosts) {
		this.userProfilePosts = userProfilePosts;
	}

	public String getUserProfileDisplayName() {
		return userProfileDisplayName;
	}

	public void setUserProfileDisplayName(String userProfileDisplayName) {
		this.userProfileDisplayName = userProfileDisplayName;
	}

	@Override
	public String toString() {
		return "PostComments [commentId=" + commentId + ", userProfileId=" + userProfileId + ", commentCaption="
				+ commentCaption + ", userProfileDisplayName=" + userProfileDisplayName + ", userProfilePosts="
				+ userProfilePosts + "]";
	}

}
