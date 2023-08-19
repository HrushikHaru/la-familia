package com.example.familia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LikePosts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int likeId;
	
	private int userProfileId;
	
	private String likedBy;
	
	@ManyToOne
	@JoinColumn(name="postId", referencedColumnName = "postId")
	private UserProfilePosts userProfilePosts;

	public LikePosts() {
		super();
	}

	public LikePosts(int likeId, int userProfileId, String likedBy, UserProfilePosts userProfilePosts) {
		super();
		this.likeId = likeId;
		this.userProfileId = userProfileId;
		this.likedBy = likedBy;
		this.userProfilePosts = userProfilePosts;
	}

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(String likedBy) {
		this.likedBy = likedBy;
	}

	public UserProfilePosts getUserProfilePosts() {
		return userProfilePosts;
	}

	public void setUserProfilePosts(UserProfilePosts userProfilePosts) {
		this.userProfilePosts = userProfilePosts;
	}

	@Override
	public String toString() {
		return "LikePosts [likeId=" + likeId + ", userProfileId=" + userProfileId + ", likedBy=" + likedBy
				+ ", userProfilePosts=" + userProfilePosts + "]";
	}

}
