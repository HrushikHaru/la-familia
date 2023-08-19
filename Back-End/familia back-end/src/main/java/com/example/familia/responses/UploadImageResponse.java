package com.example.familia.responses;

import java.util.Date;

public class UploadImageResponse {
	
	private String message;
	
	private Date uploadedOn;
	
	private String imgName;

	public UploadImageResponse() {
		super();
	}

	public UploadImageResponse(String message, Date uploadedOn, String imgName) {
		super();
		this.message = message;
		this.uploadedOn = uploadedOn;
		this.imgName = imgName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(Date uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Override
	public String toString() {
		return "UploadImageResponse [message=" + message + ", uploadedOn=" + uploadedOn + ", imgName=" + imgName + "]";
	}

}
