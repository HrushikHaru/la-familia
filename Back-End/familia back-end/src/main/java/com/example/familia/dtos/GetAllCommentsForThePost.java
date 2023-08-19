package com.example.familia.dtos;

public class GetAllCommentsForThePost {
	
	private int comment_id;
	
	private String comment_caption;
	
	private String userDisplayName;
	
	private String base64EncodedprofilePicName;

	public GetAllCommentsForThePost(int comment_id, String comment_caption, String userDisplayName,
			String base64EncodedprofilePicName) {
		super();
		this.comment_id = comment_id;
		this.comment_caption = comment_caption;
		this.userDisplayName = userDisplayName;
		this.base64EncodedprofilePicName = base64EncodedprofilePicName;
	}

	public GetAllCommentsForThePost() {
		super();
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment_caption() {
		return comment_caption;
	}

	public void setComment_caption(String comment_caption) {
		this.comment_caption = comment_caption;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	public String getBase64EncodedprofilePicName() {
		return base64EncodedprofilePicName;
	}

	public void setBase64EncodedprofilePicName(String base64EncodedprofilePicName) {
		this.base64EncodedprofilePicName = base64EncodedprofilePicName;
	}

	@Override
	public String toString() {
		return "GetAllCommentsForThePost [comment_id=" + comment_id + ", comment_caption=" + comment_caption
				+ ", userDisplayName=" + userDisplayName + ", base64EncodedprofilePicName="
				+ base64EncodedprofilePicName + "]";
	}

}
