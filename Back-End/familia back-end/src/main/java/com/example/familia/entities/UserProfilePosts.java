package com.example.familia.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class UserProfilePosts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	private String postPicName;
	
	private String caption;
	
	@Column(columnDefinition = "DATETIME")
	private String postDateTime;
	
	@ManyToOne
	@JoinColumn(name = "userProfileId",referencedColumnName = "userProfileId")
	private UserProfile userProfile;
	
	@OneToMany(mappedBy = "userProfilePosts")
	private Set<LikePosts> likePosts;
	
	@OneToMany(mappedBy = "userProfilePosts")
	private Set<PostComments> postComments;

	public UserProfilePosts() {
		super();
	}

	public UserProfilePosts(int postId, String postPicName, String caption, String postDateTime,
			UserProfile userProfile) {
		super();
		this.postId = postId;
		this.postPicName = postPicName;
		this.caption = caption;
		this.postDateTime = postDateTime;
		this.userProfile = userProfile;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
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

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "UserProfilePosts [postId=" + postId + ", postPicName=" + postPicName + ", caption=" + caption
				+ ", postDateTime=" + postDateTime + ", userProfile=" + userProfile + "]";
	}
	

}
