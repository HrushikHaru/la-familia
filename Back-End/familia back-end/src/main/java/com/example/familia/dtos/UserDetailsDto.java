package com.example.familia.dtos;

public class UserDetailsDto {
	
	private String displayName;
	
	private String hobbies;
	
	private String location;
	
	private String bio;
	
	private int following;
	
	private int followers;
	
	private int posts;

	public UserDetailsDto(String displayName, String hobbies, String location, String bio, int following, int followers,
			int posts) {
		super();
		this.displayName = displayName;
		this.hobbies = hobbies;
		this.location = location;
		this.bio = bio;
		this.following = following;
		this.followers = followers;
		this.posts = posts;
	}

	public UserDetailsDto() {
		super();
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getPosts() {
		return posts;
	}

	public void setPosts(int posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "UserDetailsDto [displayName=" + displayName + ", hobbies=" + hobbies + ", location=" + location
				+ ", bio=" + bio + ", following=" + following + ", followers=" + followers + ", posts=" + posts + "]";
	}

}
