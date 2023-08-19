package com.example.familia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.familia.dtos.GetDetailsForUserDeatilsPost;
import com.example.familia.dtos.UserDetailsDto;
import com.example.familia.services.UserProfileDetailsService;

@RestController
public class UserProfileDetailsController {
	
	@Value("${project.images.posts}")
	private String postImagePath;
	
	private final UserProfileDetailsService userDetailsServ;
	
	public UserProfileDetailsController(UserProfileDetailsService userDetailsServ) {
		this.userDetailsServ = userDetailsServ;
	}
	
	@GetMapping(path = "/getAllDetailsOfUser/{userProfileId}")
	public ResponseEntity<UserDetailsDto> getAllDetailsOfUser(@PathVariable int userProfileId) {
		UserDetailsDto userDetails = null;
		
		try {
			userDetails = userDetailsServ.getAllDetails(userProfileId);
			return new ResponseEntity<>(userDetails,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(userDetails,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path = "/getAllThePostsOfTheUser/{userProfileId}")
	public ResponseEntity<List<GetDetailsForUserDeatilsPost>> getAllPosts(@PathVariable int userProfileId) {
		List<GetDetailsForUserDeatilsPost> result = null;
		try {
			result = userDetailsServ.getAllPostsOfTheUser(userProfileId, postImagePath);
			
			return new ResponseEntity<>(result,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
	}

}
