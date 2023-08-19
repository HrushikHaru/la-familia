package com.example.familia.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userProfileId;
	
	private String profilePicName;
	
	private String displayName;
	
	private String bio;
	
	private String location;
	
	private String hobbies;
	
	@OneToOne
	@JoinColumn(name = "logIn_id")
	private LogIn logIn;
	
	@ManyToMany(mappedBy = "userProfile")
	private Set<Following> following;
	
	@ManyToMany(mappedBy = "userProfile")
	private Set<Followers> followers;
	
	@OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<UserProfilePosts> userPosts;

	public UserProfile() {
		super();
	}

	public UserProfile(int userProfileId) {
		super();
		this.userProfileId = userProfileId;
	}

	public UserProfile(String profilePicName, String displayName, String bio, String location,
			String hobbies, LogIn logIn) {
		super();
		this.profilePicName = profilePicName;
		this.displayName = displayName;
		this.bio = bio;
		this.location = location;
		this.hobbies = hobbies;
		this.logIn = logIn;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
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

	public LogIn getLogIn() {
		return logIn;
	}

	public void setLogIn(LogIn logIn) {
		this.logIn = logIn;
	}
	
	

}
