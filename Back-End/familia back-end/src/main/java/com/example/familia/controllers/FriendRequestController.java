package com.example.familia.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.familia.dtos.FriendRequestDto;
import com.example.familia.dtos.RequestFriendDto;
import com.example.familia.dtos.UserProfileIdOfFriendRequest;
import com.example.familia.responses.FriendRequestByResponse;
import com.example.familia.responses.FriendRequestResponse;
import com.example.familia.services.FriendRequestService;

@RestController
public class FriendRequestController {
	
	@Value("${project.images}")
	private String path;
	
	private final FriendRequestService friendServ;
	
	public FriendRequestController(FriendRequestService friendServ) {
		this.friendServ = friendServ;
	}

	@PostMapping(path = "/sendRequestTo")
	public ResponseEntity<FriendRequestResponse> sendRequestToAnotherUser(@RequestBody RequestFriendDto friendRequest) {
		
		FriendRequestResponse requestResponse = new FriendRequestResponse();
		
		try {
			friendServ.sendRequestTo(friendRequest);
			requestResponse.setMessage("Friend request has been sent successfully.");
			requestResponse.setRequestSentOn(new Date());
			return new ResponseEntity<>(requestResponse,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		requestResponse.setMessage("Friend request wasn't sent.");
		requestResponse.setRequestSentOn(new Date());
		return new ResponseEntity<>(requestResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping(path = "/showUserProfileRequests/{userProfileId}")
	public ResponseEntity<List<FriendRequestDto>> showAllRequests(@PathVariable int userProfileId) {
		List<FriendRequestDto> friendRequests = null;
		
		try {
			friendRequests = friendServ.showEveryRequest(userProfileId,path);
			
			return new ResponseEntity<>(friendRequests,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(friendRequests,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path = "/getUserProfileRequestsUserProfileId/{userProfileId}")
	public ResponseEntity<List<UserProfileIdOfFriendRequest>> getUserProfileIdOfPeople(@PathVariable int userProfileId) {
		List<UserProfileIdOfFriendRequest> requests = null;
		
		try {
			requests = friendServ.getProfileIdRequests(userProfileId);
			
			return new ResponseEntity<>(requests, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(requests,HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(path = "/deleteUserProfileRequestsAccept/{to}/{from}")
	public ResponseEntity<FriendRequestByResponse> deleteTheFriendRequestAccept(@PathVariable int to, @PathVariable int from) {
		
		FriendRequestByResponse response =  null;
		
		try {
			friendServ.deleteTheRequestAndAcceptTheRequest(to,from);
			
			response = new FriendRequestByResponse();
			response.setMessage("The Request has been processed successfully");
			response.setRequestSentOn(new Date());
			
			return new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(path = "/deleteUserProfileRequestsReject/{to}/{from}")
	public ResponseEntity<FriendRequestByResponse> deleteTheFriendRequestReject(@PathVariable int to, @PathVariable int from) {
		FriendRequestByResponse response =  null;
		try {
			friendServ.deleteTheRequestAndRejectTheRequest(to,from);
			
			response = new FriendRequestByResponse();
			response.setMessage("The Request has been processed successfully");
			response.setRequestSentOn(new Date());
			
			return new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
