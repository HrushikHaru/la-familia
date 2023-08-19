package com.example.familia.responses;

public class FollowingCount {

	private int followingCount;

	public FollowingCount() {
		super();
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	@Override
	public String toString() {
		return "FollowingCount [followingCount=" + followingCount + "]";
	}
}
