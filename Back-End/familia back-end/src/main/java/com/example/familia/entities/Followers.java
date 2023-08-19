package com.example.familia.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Followers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int followersId;
	
	private int userProfileIdFollower;
	
	private String followerName;
	
	@ManyToMany
	@JoinTable(
				name = "userProfileFollowers",
				joinColumns = @JoinColumn(name="followersId"),
				inverseJoinColumns = @JoinColumn(name="userProfileId")
				)
	private Set<UserProfile> userProfile;
	
	public Followers() {
		super();
	}

	public Followers(int followersId, String followerName) {
		super();
		this.followersId = followersId;
		this.followerName = followerName;
	}

	public int getFollowersId() {
		return followersId;
	}

	public void setFollowersId(int followersId) {
		this.followersId = followersId;
	}

	public String getFollowerName() {
		return followerName;
	}

	public void setFollowerName(String followerName) {
		this.followerName = followerName;
	}

	public int getUserProfileIdFollower() {
		return userProfileIdFollower;
	}

	public void setUserProfileIdFollower(int userProfileIdFollower) {
		this.userProfileIdFollower = userProfileIdFollower;
	}

	public Set<UserProfile> getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Set<UserProfile> userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "Followers [followersId=" + followersId + ", followerName=" + followerName + "]";
	}

}
