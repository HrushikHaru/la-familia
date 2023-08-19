package com.example.familia.dtos;

import java.time.LocalDateTime;
import java.util.Date;

public class UserProfilePostsByFollowersDto {
	
	private int userProfileId;
	
	private int postId;
	
	private String userProfileDisplayName;
	
	private String base64EncodingProfilePic;
	
	private String base64EncodingPostPic;
	
	private String caption;
	
	private String postdateTime;
	
	private String location;
	
	private int commentCount;
	
	private int likeCount;

	public UserProfilePostsByFollowersDto(int userProfileId, int postId, String userProfileDisplayName,
			String base64EncodingProfilePic, String base64EncodingPostPic, String caption, String postdateTime,
			String location, int commentCount, int likeCount) {
		super();
		this.userProfileId = userProfileId;
		this.postId = postId;
		this.userProfileDisplayName = userProfileDisplayName;
		this.base64EncodingProfilePic = base64EncodingProfilePic;
		this.base64EncodingPostPic = base64EncodingPostPic;
		this.caption = caption;
		this.postdateTime = postdateTime;
		this.location = location;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
	}

	public UserProfilePostsByFollowersDto() {
		super();
	}

	public String getUserProfileDisplayName() {
		return userProfileDisplayName;
	}

	public void setUserProfileDisplayName(String userProfileDisplayName) {
		this.userProfileDisplayName = userProfileDisplayName;
	}

	public String getBase64EncodingProfilePic() {
		return base64EncodingProfilePic;
	}

	public void setBase64EncodingProfilePic(String base64EncodingProfilePic) {
		this.base64EncodingProfilePic = base64EncodingProfilePic;
	}

	public String getBase64EncodingPostPic() {
		return base64EncodingPostPic;
	}

	public void setBase64EncodingPostPic(String base64EncodingPostPic) {
		this.base64EncodingPostPic = base64EncodingPostPic;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getPostdateTime() {
		return postdateTime;
	}

	public void setPostdateTime(String postdateTime) {
		this.postdateTime = postdateTime;
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "UserProfilePostsByFollowersDto [userProfileId=" + userProfileId + ", postId=" + postId
				+ ", userProfileDisplayName=" + userProfileDisplayName + ", base64EncodingProfilePic="
				+ base64EncodingProfilePic + ", base64EncodingPostPic=" + base64EncodingPostPic + ", caption=" + caption
				+ ", postdateTime=" + postdateTime + ", location=" + location + ", commentCount=" + commentCount
				+ ", likeCount=" + likeCount + "]";
	}

}
