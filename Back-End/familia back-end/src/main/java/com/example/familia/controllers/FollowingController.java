package com.example.familia.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.familia.dtos.ListOfPeopleFollowingDto;
import com.example.familia.responses.FollowingCount;
import com.example.familia.responses.FollowingOtherUsersResponse;
import com.example.familia.responses.UnfollowingPeopleResponse;
import com.example.familia.services.FollowingService;

@RestController
public class FollowingController {
	
	private final FollowingService followingServ;
	
	public FollowingController(FollowingService followingServ) {
		this.followingServ = followingServ;
	}
	
	@GetMapping("/getAllFollowingList/{userId}")
	public ResponseEntity<List<ListOfPeopleFollowingDto>> getUsersFollowingList(@PathVariable int userId) {
		
		List<ListOfPeopleFollowingDto> followingList = null;
		
		try {
			followingList = followingServ.getFollowingList(userId);
			
			return new ResponseEntity<>(followingList,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(followingList,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getAllFollowingListCount/{userId}")
	public ResponseEntity<FollowingCount> getFollowingCount(@PathVariable int userId){
		FollowingCount count = null;
		
		try {
			count = followingServ.getFollowingCountByParticularUser(userId);
			
			return new ResponseEntity<>(count,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(count,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path = "/checkIfUserFollowsTheOtherUser/{userProfileId}/{otherUserId}")
	public ResponseEntity<FollowingOtherUsersResponse> getIfTheOtherUserFollowsTheUserInConsideration(@PathVariable int userProfileId, @PathVariable int otherUserId) {
		FollowingOtherUsersResponse followingResponse = null;
		
		try {
			followingResponse = followingServ.checkIfUserFollowsTheOtherUser(userProfileId,otherUserId);
			
			return new ResponseEntity<>(followingResponse,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(followingResponse,HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(path = "/unfollowTheOtherUserFromList/{userProfileId}/{otherUserId}")
	public ResponseEntity<UnfollowingPeopleResponse> unfollowAUser(@PathVariable int userProfileId, @PathVariable int otherUserId) {
		UnfollowingPeopleResponse response = null;
		try {
			followingServ.unfollowTheOtherUserFromList(userProfileId,otherUserId);
			
			Date date = new Date();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String dateFormat = sdf.format(date);
			
			response = new UnfollowingPeopleResponse();
			
			response.setResponse("The User has been Unfollowed.");
			response.setUnfollowedOn(dateFormat);
			
			return new ResponseEntity<>(response,HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

}
