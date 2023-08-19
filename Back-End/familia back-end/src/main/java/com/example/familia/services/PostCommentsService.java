package com.example.familia.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.familia.dtos.PostCommentDto;
import com.example.familia.entities.PostComments;
import com.example.familia.entities.UserProfilePosts;
import com.example.familia.repositories.PostCommentsRepository;
import com.example.familia.dtos.GetAllCommentsForThePost;

@Service
public class PostCommentsService {

	private final PostCommentsRepository postCommentsRepo;
	
	private final JdbcTemplate jdbcTemp;
	
	public PostCommentsService(PostCommentsRepository postCommentsRepo, JdbcTemplate jdbcTemp) {
		this.postCommentsRepo = postCommentsRepo;
		this.jdbcTemp = jdbcTemp;
	}
	
	public void postComment(PostCommentDto postComment) {
		
		UserProfilePosts userProfilePosts = new UserProfilePosts();
		userProfilePosts.setPostId(postComment.getPostId());
		
		PostComments postCommentFromUser = new PostComments();
		postCommentFromUser.setUserProfilePosts(userProfilePosts);
		postCommentFromUser.setCommentCaption(postComment.getCommentCaption());
		postCommentFromUser.setUserProfileDisplayName(postComment.getUserProfileDisplayName());
		postCommentFromUser.setUserProfileId(postComment.getUserProfileId());
		
		postCommentsRepo.save(postCommentFromUser);
		
	}

	public List<GetAllCommentsForThePost> getAllComments(int postId, String path) {
		
		String query = "select a.comment_id, a.comment_caption, a.user_profile_id, c.display_name, c.profile_pic_name from post_comments a left join user_profile_posts b \r\n"
				+ "ON(a.post_id = b.post_id) left join user_profile c \r\n"
				+ "ON(a.user_profile_id = c.user_profile_id) where a.post_id = ?";
		
		List<GetAllCommentsForThePost> allComments = jdbcTemp.query(query, ps->ps.setInt(1, postId), (resultSet, rowNum)->{
			
			GetAllCommentsForThePost allComment = new GetAllCommentsForThePost();
			
			allComment.setComment_id(resultSet.getInt(1));
			allComment.setComment_caption(resultSet.getString(2));
			allComment.setUserDisplayName(resultSet.getString(4));
			
			int userProfileId = resultSet.getInt(3);
			String userProfilePicName = resultSet.getString(5);
			byte [] imgBytes = getAllBytesOfImgs(path, userProfileId, userProfilePicName);
			
			String profilePic = Base64.encodeBase64String(imgBytes);
			allComment.setBase64EncodedprofilePicName(profilePic);
			
			return allComment;
		});
		
		return allComments;
		
	}
	
	private byte[] getAllBytesOfImgs(String path, int userProfileId, String userProfilePicName) {
		
		String filePath = path+userProfileId+"/"+userProfilePicName;
		
		Path uriPath = Paths.get(filePath);
		
		try {
			byte [] imgBytes = Files.readAllBytes(uriPath);
			return imgBytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
