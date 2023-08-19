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
public class Following {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int followingId;
	
	private int userProfileIdFollowing;
	
	private String followingName;
	
	@ManyToMany
	@JoinTable(
			name = "userProfileFollowing",
			joinColumns = @JoinColumn(name="followingId"),
			inverseJoinColumns = @JoinColumn(name="userProfileId")
			)
	private Set<UserProfile> userProfile;

	public Following() {
		super();
	}

	public Following(int followingId, String followingName, Set<UserProfile> userProfile) {
		super();
		this.followingId = followingId;
		this.followingName = followingName;
		this.userProfile = userProfile;
	}

	public int getFollowingId() {
		return followingId;
	}

	public void setFollowingId(int followingId) {
		this.followingId = followingId;
	}

	public String getFollowingName() {
		return followingName;
	}

	public int getUserProfileIdFollowing() {
		return userProfileIdFollowing;
	}

	public void setUserProfileIdFollowing(int userProfileIdFollowing) {
		this.userProfileIdFollowing = userProfileIdFollowing;
	}

	public void setFollowingName(String followingName) {
		this.followingName = followingName;
	}

	public Set<UserProfile> getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Set<UserProfile> userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "Following [followingId=" + followingId + ", followingName=" + followingName + ", userProfile="
				+ userProfile + "]";
	}
	
}
