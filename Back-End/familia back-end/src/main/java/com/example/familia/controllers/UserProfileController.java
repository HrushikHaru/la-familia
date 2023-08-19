package com.example.familia.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.familia.dtos.UpdateProfileDtoWithPic;
import com.example.familia.dtos.UpdateUserProfileDto;
import com.example.familia.dtos.UserProfileDto;
import com.example.familia.repositories.UserProfileRepository;
import com.example.familia.responses.UpdateProfileResponse;
import com.example.familia.responses.UploadImageResponse;
import com.example.familia.responses.UserProfileDetailsResponse;
import com.example.familia.responses.UserProfileUploadResponse;
import com.example.familia.responses.UsersCount;
import com.example.familia.services.UserProfileService;
import com.example.familia.utils.LocationGetter;

@RestController
public class UserProfileController {
	
	@Value("${project.images}")
	private String path;
	
	private final UserProfileService userServ;
	
	private final LocationGetter locationGet;
	
	private final UserProfileRepository userProfileRepo;
	
	public UserProfileController(UserProfileService userServ,LocationGetter locationGet,UserProfileRepository userProfileRepo) {
		this.userServ = userServ;
		this.locationGet = locationGet;
		this.userProfileRepo = userProfileRepo;
	}
	
	@PostMapping("/uploadImage")
	public ResponseEntity<UploadImageResponse> uploadImage(@RequestParam int userId,@RequestParam MultipartFile file){
		
		UploadImageResponse response = null;
		
		try {
			String profileImgName = userServ.uploadImageService(file,userId,path);
			
			response = new UploadImageResponse();
			
			response.setMessage("The File has been successfully uploaded");
			
			Date date = new Date();
			response.setUploadedOn(date);
			
			response.setImgName(profileImgName);
			
			return new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/addUserInfo")
	public ResponseEntity<UserProfileUploadResponse> uploadUserInfo(@RequestBody UserProfileDto profileDto){
		
		UserProfileUploadResponse userUploadResponse = null;
	
		try {
			userServ.uploadUserProfile(profileDto);
			
			userUploadResponse = new UserProfileUploadResponse();
			
			userUploadResponse.setResponseMessage("The info has been saved successfully");
			
			Date date = new Date();
			
			userUploadResponse.setPublishedDate(date);
			
			return new ResponseEntity<>(userUploadResponse,HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(userUploadResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/getUserProfilePic/{userProfileId}")
	public ResponseEntity<Resource> sendUserProfileToPic(@PathVariable int userProfileId) {
		Resource resource = null;
		
		try {
			return userServ.sendProfileImageToFrontEnd(userProfileId, path);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(resource,HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/getUserProfile/{userId}")
	public ResponseEntity<UserProfileDetailsResponse> sendUserProfileToFrontEnd(@PathVariable int userId) {
		
		UserProfileDetailsResponse response = null;
		
		try {
			response = userServ.getUserProfileId(userId);
			
			return new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		 
	}
	
	@GetMapping("/getAllUserProfile/{userProfileId}")
	public ResponseEntity<List<Map<String, Object>>> getAllUsers(@PathVariable int userProfileId){
		
		List<Map<String, Object>> responses = null;
		try {
			responses = userServ.getAllUsers(userProfileId, path);
			
			return new ResponseEntity<>(responses,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(responses,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getAllUsersCount")
	public ResponseEntity<UsersCount> getUsersCount(){
		
		UsersCount userCount = null;
		
		try {
			int count = (int)userProfileRepo.count();
			userCount = new UsersCount();
			userCount.setUserCounts(count);
			
			return new ResponseEntity<>(userCount,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(userCount,HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping(path = "/updateWithouPic")
	public ResponseEntity<UpdateProfileResponse> updateInfo(@RequestBody UpdateUserProfileDto userProfile) {
		UpdateProfileResponse response = new UpdateProfileResponse();
		
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatted = sdf.format(date);
		
		response.setUpdatedOn(formatted);
		
		try {
			userServ.updateUserInfoWithoutPic(userProfile);
			
			response.setResponse("Updated successfully!");
			
			return new ResponseEntity<>(response,HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		response.setResponse("Update wasn't successful.");
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(path = "/updateWithPic")
	public ResponseEntity<UpdateProfileResponse> updateInfoWithPic(@RequestBody UpdateProfileDtoWithPic userProfile) {
		UpdateProfileResponse response = new UpdateProfileResponse();
		
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatted = sdf.format(date);
		
		response.setUpdatedOn(formatted);
		
		try {
			userServ.updateUserInfoWithPic(userProfile);
			
			response.setResponse("Updated successfully!");
			
			return new ResponseEntity<>(response,HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		response.setResponse("Update wasn't successful.");
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

}
