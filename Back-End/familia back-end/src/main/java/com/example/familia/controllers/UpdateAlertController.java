package com.example.familia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.familia.dtos.UserProfilePostsByFollowersDto;
import com.example.familia.services.UpdateServices;

@RestController
public class UpdateAlertController {
	
	@Value("${project.images.posts}")
	private String path;
	
	@Value("${project.images}")
	private String pathProfilePic;
	
	private final UpdateServices updateServices;
	
	public UpdateAlertController(UpdateServices updateServices) {
		this.updateServices = updateServices;
	}
	
	@GetMapping(path = "/updateFeeds/{userProfileId}/{offSet}")
	public ResponseEntity<List<UserProfilePostsByFollowersDto>> addUpdatesToTheUserProfileFeeds(@PathVariable int userProfileId,@PathVariable int offSet) {
		List<UserProfilePostsByFollowersDto> listOfNewPosts = null;
		try {
			System.out.println(offSet);
			listOfNewPosts = updateServices.getFeedUpdates(userProfileId,offSet, path, pathProfilePic);
			return new ResponseEntity<>(listOfNewPosts,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<UserProfilePostsByFollowersDto>>(listOfNewPosts,HttpStatus.BAD_REQUEST);
	}

}
