package com.example.familia.responses;

import java.util.Date;

public class PostImageSaveInFile {
	
	private String message;
	
	private String postPicName;
	
	private Date saveDate;

	public PostImageSaveInFile(String message,String postPicName, Date saveDate) {
		super();
		this.message = message;
		this.postPicName = postPicName;
		this.saveDate = saveDate;
	}

	public PostImageSaveInFile() {
		super();
	}

	public String getPostPicName() {
		return postPicName;
	}

	public void setPostPicName(String postPicName) {
		this.postPicName = postPicName;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PostImageSaveInFile [message=" + message + ", postPicName=" + postPicName + ", saveDate=" + saveDate
				+ "]";
	}

}
