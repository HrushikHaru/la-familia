package com.example.familia.dtos;

public class GetDetailsForUserDeatilsPost {
	
	private String base64EncodedPostPic;
	
	private String caption;
	
	private int likeCount;
	
	private int commentCount;

	public GetDetailsForUserDeatilsPost(String base64EncodedPostPic, String caption, int likeCount, int commentCount) {
		super();
		this.base64EncodedPostPic = base64EncodedPostPic;
		this.caption = caption;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}

	public GetDetailsForUserDeatilsPost() {
		super();
	}

	public String getBase64EncodedPostPic() {
		return base64EncodedPostPic;
	}

	public void setBase64EncodedPostPic(String base64EncodedPostPic) {
		this.base64EncodedPostPic = base64EncodedPostPic;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public String toString() {
		return "GetDetailsForUserDeatilsPost [base64EncodedPostPic=" + base64EncodedPostPic + ", caption=" + caption
				+ ", likeCount=" + likeCount + ", commentCount=" + commentCount + "]";
	}

}
