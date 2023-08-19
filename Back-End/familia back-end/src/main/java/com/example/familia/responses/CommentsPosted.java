package com.example.familia.responses;

import java.util.Date;

public class CommentsPosted {
	
	private String message;
	
	private Date commentPostedOn;

	public CommentsPosted() {
		super();
	}

	public CommentsPosted(String message, Date commentPostedOn) {
		super();
		this.message = message;
		this.commentPostedOn = commentPostedOn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCommentPostedOn() {
		return commentPostedOn;
	}

	public void setCommentPostedOn(Date commentPostedOn) {
		this.commentPostedOn = commentPostedOn;
	}

}
