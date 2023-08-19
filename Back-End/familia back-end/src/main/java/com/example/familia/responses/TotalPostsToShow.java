package com.example.familia.responses;

public class TotalPostsToShow {
	
	private int postsNumber;

	public TotalPostsToShow(int postsNumber) {
		super();
		this.postsNumber = postsNumber;
	}

	public TotalPostsToShow() {
		super();
	}

	public int getPostsNumber() {
		return postsNumber;
	}

	public void setPostsNumber(int postsNumber) {
		this.postsNumber = postsNumber;
	}

	@Override
	public String toString() {
		return "TotalPostsToShow [postsNumber=" + postsNumber + "]";
	}

}
