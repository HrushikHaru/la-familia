package com.example.familia.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.familia.dtos.LikePostDto;
import com.example.familia.responses.LikedPostResponse;
import com.example.familia.responses.UserLikedTheFollowingPosts;
import com.example.familia.services.LikePostsService;

@RestController
public class LikePostsController {
	
	private final LikePostsService likePostsServ;
	
	public LikePostsController(LikePostsService likePostsServ) {
		this.likePostsServ = likePostsServ;
	}
	
	@PostMapping(path = "/addLikeToThePost")
	public ResponseEntity<LikedPostResponse> likeCurrentPost(@RequestBody LikePostDto likePost) {
		LikedPostResponse likedResponse = null;
		
		try {
			int postId = likePost.getPostId();
			int userProfileId = likePost.getUserProfileId();
			String userDisplayName = likePost.getUserDisplayName();
			
			likePostsServ.addLikeToPost(postId, userProfileId,userDisplayName);
			
			likedResponse = new LikedPostResponse();
			likedResponse.setMessage("The post was liked by "+userDisplayName);
			likedResponse.setLikedOn(new Date());
			
			return new ResponseEntity<>(likedResponse,HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(likedResponse,HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(path = "/deleteLikeOnThePost/{postId}/{userProfileId}")
	public ResponseEntity<LikedPostResponse> deleteLike(@PathVariable int postId, @PathVariable int userProfileId) {
		LikedPostResponse likedResponse = null;
		
		try {
			int rowsAffected = likePostsServ.deleteLikeFromThePostForTheUser(postId,userProfileId);
			
			if(rowsAffected > 0) {
				likedResponse = new LikedPostResponse();
				likedResponse.setMessage("The like was been removed for this post from user with profileId "+userProfileId);
				likedResponse.setLikedOn(new Date());
				
				return new ResponseEntity<>(likedResponse,HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(likedResponse,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path="/getLikesOnPost/{userProfileId}")
	public ResponseEntity<List<UserLikedTheFollowingPosts>> getLikesOnThePostByUser(@PathVariable int userProfileId) {
		List<UserLikedTheFollowingPosts> userLikedPosts = null;
		
		try {
			userLikedPosts = likePostsServ.getAllLikesOnPostsByUser(userProfileId);
			
			return new ResponseEntity<>(userLikedPosts,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(userLikedPosts,HttpStatus.BAD_REQUEST);
	}

}
