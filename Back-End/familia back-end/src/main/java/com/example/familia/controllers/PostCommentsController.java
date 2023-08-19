package com.example.familia.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.familia.responses.CommentsPosted;
import com.example.familia.dtos.GetAllCommentsForThePost;
import com.example.familia.dtos.PostCommentDto;
import com.example.familia.services.PostCommentsService;

@RestController
public class PostCommentsController {
	
	@Value("${project.images}")
	private String path;
	
	private final PostCommentsService postCommentServ;
	
	public PostCommentsController(PostCommentsService postCommentServ) {
		this.postCommentServ = postCommentServ;
	}

	@PostMapping("/postComment")
	public ResponseEntity<CommentsPosted> postCommentOnPost(@RequestBody PostCommentDto postComment) {
		CommentsPosted commentPost = null;
		
		try {
			postCommentServ.postComment(postComment);
			
			commentPost = new CommentsPosted();
			commentPost.setMessage("The comment was posted successfully");
			commentPost.setCommentPostedOn(new Date());
			
			return new ResponseEntity<>(commentPost,HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(commentPost,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path = "/getAllComments/{postId}")
	public ResponseEntity<List<GetAllCommentsForThePost>> getAllCommentsForThePost(@PathVariable int postId){
		List<GetAllCommentsForThePost> allComments = null;
		try {
			allComments = postCommentServ.getAllComments(postId,path);
			return new ResponseEntity<>(allComments,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(allComments,HttpStatus.BAD_REQUEST);
		
	}
	
}
