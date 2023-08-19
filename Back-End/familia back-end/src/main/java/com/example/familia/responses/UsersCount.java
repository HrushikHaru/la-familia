package com.example.familia.responses;

public class UsersCount {
	
	private int userCounts;

	public UsersCount() {
		super();
	}

	public UsersCount(int userCounts) {
		super();
		this.userCounts = userCounts;
	}

	public int getUserCounts() {
		return userCounts;
	}

	public void setUserCounts(int userCounts) {
		this.userCounts = userCounts;
	}

	@Override
	public String toString() {
		return "UsersCount [userCounts=" + userCounts + "]";
	}

}
