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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.familia.dtos.UserProfilePostsByFollowersDto;
import com.example.familia.dtos.UserProfilePostsDto;
import com.example.familia.responses.PostCreatedResponse;
import com.example.familia.responses.PostImageSaveInFile;
import com.example.familia.services.UserProfilePostsService;

@RestController
public class UserProfilePostsController {
	
	@Value("${project.images.posts}")
	private String path;
	
	@Value("${project.images}")
	private String pathProfilePic;
	
	private final UserProfilePostsService userProfilePostsServ;
	
	public UserProfilePostsController(UserProfilePostsService userProfilePostsServ) {
		this.userProfilePostsServ = userProfilePostsServ;
	}
	
	@PostMapping(path = "/addPostImageInFile")
	public ResponseEntity<PostImageSaveInFile> getPostImage(@RequestParam MultipartFile file,@RequestParam int userProfileId) {	
		
		PostImageSaveInFile imgSaveInFile = null;
		
		try {
			String imgPathResponse = userProfilePostsServ.addPostImageToFile(file,userProfileId,path);
			
			if(imgPathResponse != null) {
				imgSaveInFile = new PostImageSaveInFile();
				
				imgSaveInFile.setMessage("The image has been successfully uploaded in the file-System.");
				imgSaveInFile.setPostPicName(imgPathResponse);
				imgSaveInFile.setSaveDate(new Date());
				
				return new ResponseEntity<>(imgSaveInFile,HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return new ResponseEntity<>(imgSaveInFile,HttpStatus.BAD_REQUEST);
	}
	

	@PostMapping(path = "/createPost")
	public ResponseEntity<PostCreatedResponse> postingUsersPostInDB(@RequestBody UserProfilePostsDto userPost) {
		
		PostCreatedResponse createdResponse = new PostCreatedResponse();;
		
		try {
			userProfilePostsServ.addPostByUser(userPost);
			
			createdResponse.setMessage("The post was created successfully.");
			createdResponse.setPostedOn(new Date());
			
			return new ResponseEntity<>(createdResponse,HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		createdResponse.setMessage("The post wasn't created successfully. Please try again after some time.");
		createdResponse.setPostedOn(new Date());
		
		return new ResponseEntity<PostCreatedResponse>(createdResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping(path = "/getPostsFromFollowers/{userProfileId}")
	public ResponseEntity<List<UserProfilePostsByFollowersDto>> getPostsFromFollowers(@PathVariable int userProfileId) {
		
		List<UserProfilePostsByFollowersDto> allPosts = null;
		
		try {
			allPosts = userProfilePostsServ.getAllPostsFromFollowers(userProfileId, path, pathProfilePic);
			
			return new ResponseEntity<>(allPosts,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(allPosts,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path = "/getAllThePosts")
	public ResponseEntity<List<UserProfilePostsByFollowersDto>> getAllPostsPublic() {
		List<UserProfilePostsByFollowersDto> result = null;
		try {
			result =userProfilePostsServ.getAllPostsFromFollowersPublic(path, pathProfilePic);
			
			return new ResponseEntity<>(result,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}
	
}
