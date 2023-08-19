package com.example.familia.services;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.familia.entities.LikePosts;
import com.example.familia.entities.UserProfilePosts;
import com.example.familia.repositories.LikePostsRepository;
import com.example.familia.responses.UserLikedTheFollowingPosts;

@Service
public class LikePostsService {
	
	private final LikePostsRepository likePostsRepo;
	
	private final JdbcTemplate jdbcTemp;
	
	public LikePostsService(LikePostsRepository likePostsRepo, JdbcTemplate jdbcTemp) {
		this.likePostsRepo = likePostsRepo;
		this.jdbcTemp = jdbcTemp;
	}

	public void addLikeToPost(int postId, int userProfileId, String userDisplayName) {
		
		UserProfilePosts userPost = new UserProfilePosts();
		userPost.setPostId(postId);
		
		LikePosts likePost = new LikePosts();
		likePost.setUserProfileId(userProfileId);
		likePost.setLikedBy(userDisplayName);
		likePost.setUserProfilePosts(userPost);
		
		likePostsRepo.save(likePost);
		
	}

	public int deleteLikeFromThePostForTheUser(int postId, int userProfileId) {
		
		String query = "delete from like_posts where user_profile_id = ? and post_id = ?";
		
		Object [] args = {userProfileId,postId};
		int rowsAffected = jdbcTemp.update(query, args);
		
		return rowsAffected;
		
	}

	public List<UserLikedTheFollowingPosts> getAllLikesOnPostsByUser(int userProfileId) {
		
		String query = "select b.post_id from user_profile a right join like_posts b ON(a.user_profile_id = b.user_profile_id) \r\n"
				+ "where a.user_profile_id = ?";
		
		List<UserLikedTheFollowingPosts> userLikedPosts = jdbcTemp.query(query, ps->ps.setInt(1, userProfileId), (resultSet,rowNum)->{
			
			UserLikedTheFollowingPosts userLiked = new UserLikedTheFollowingPosts();
			
			userLiked.setPostId(resultSet.getInt(1));
			
			return userLiked;
			
		});
		
		return userLikedPosts;
		
	}
	
	

}
