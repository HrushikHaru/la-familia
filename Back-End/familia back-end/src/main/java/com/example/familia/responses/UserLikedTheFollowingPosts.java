package com.example.familia.responses;

public class UserLikedTheFollowingPosts {
	
	private int postId;

	public UserLikedTheFollowingPosts(int postId) {
		super();
		this.postId = postId;
	}

	public UserLikedTheFollowingPosts() {
		super();
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "UserLikedTheFollowingPosts [postId=" + postId + "]";
	}

}
