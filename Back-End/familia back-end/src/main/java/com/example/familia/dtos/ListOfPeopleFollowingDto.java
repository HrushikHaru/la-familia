package com.example.familia.dtos;

public class ListOfPeopleFollowingDto {
	
	private int followingId;
	
	private String followingName;
	
	private int followingPersonUserId;

	public ListOfPeopleFollowingDto(int followingId, String followingName, int followingPersonUserId) {
		super();
		this.followingId = followingId;
		this.followingName = followingName;
		this.followingPersonUserId = followingPersonUserId;
	}

	public ListOfPeopleFollowingDto() {
		super();
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

	public void setFollowingName(String followingName) {
		this.followingName = followingName;
	}

	public int getFollowingPersonUserId() {
		return followingPersonUserId;
	}

	public void setFollowingPersonUserId(int followingPersonUserId) {
		this.followingPersonUserId = followingPersonUserId;
	}

	@Override
	public String toString() {
		return "ListOfPeopleFollowingDto [followingId=" + followingId + ", followingName=" + followingName
				+ ", followingPersonUserId=" + followingPersonUserId + "]";
	}

}
