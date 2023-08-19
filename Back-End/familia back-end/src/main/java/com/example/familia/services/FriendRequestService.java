package com.example.familia.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.example.familia.dtos.FriendRequestDto;
import com.example.familia.dtos.RequestFriendDto;
import com.example.familia.dtos.UserProfileIdOfFriendRequest;
import com.example.familia.entities.Followers;
import com.example.familia.entities.Following;
import com.example.familia.entities.FriendRequest;
import com.example.familia.entities.UserProfile;
import com.example.familia.repositories.FollowersRepository;
import com.example.familia.repositories.FollowingRepository;
import com.example.familia.repositories.FriendRequestRepository;
import com.example.familia.repositories.UserProfileRepository;

@Service
public class FriendRequestService {
	
	private final FriendRequestRepository friendReqRepo;
	
	private final FollowingRepository followingRepo;
	
	private final FollowersRepository followerRepo;
	
	private final UserProfileRepository userProfileRepo;
	
	public FriendRequestService(FriendRequestRepository friendReqRepo,FollowingRepository followingRepo,UserProfileRepository userProfileRepo,FollowersRepository followerRepo) {
		this.friendReqRepo = friendReqRepo;
		this.followingRepo = followingRepo;
		this.userProfileRepo = userProfileRepo;
		this.followerRepo = followerRepo;
	}

	public void sendRequestTo(RequestFriendDto friendRequest) {
		
		FriendRequest friendReq = new FriendRequest();
		
		friendReq.setRequestById(friendRequest.getRequestById());
		friendReq.setRequestByName(friendRequest.getRequestByName());
		
		friendReq.setRequestToId(friendRequest.getRequestToId());
		friendReq.setRequestToName(friendRequest.getRequestToName());
		
		friendReqRepo.save(friendReq);
		
		//To save as a followed person
		Set<UserProfile> userProfiles = new HashSet<>();
		
		Following following = new Following();
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUserProfileId(friendRequest.getRequestById());
		userProfiles.add(userProfile);
		
		following.setUserProfileIdFollowing(friendRequest.getRequestToId());
		following.setFollowingName(friendRequest.getRequestToName());
		
		following.setUserProfile(userProfiles);
		
		followingRepo.save(following);
		
		//To save as a follower to the person being followed
		Set<UserProfile> userProfilesFollower = new HashSet<>();
		
		Followers followers = new Followers();
		
		UserProfile userProfileFollower = new UserProfile();
		userProfileFollower.setUserProfileId(friendRequest.getRequestToId());
		userProfilesFollower.add(userProfileFollower);
		
		followers.setUserProfileIdFollower(friendRequest.getRequestById());
		followers.setFollowerName(friendRequest.getRequestByName());
		
		followers.setUserProfile(userProfilesFollower);
		
		followerRepo.save(followers);
		
	}

	public List<FriendRequestDto> showEveryRequest(int userProfileId, String path) {
		
		List<FriendRequestDto> friendRequestsToUser = new ArrayList<>();
		
		List<FriendRequest> requests = friendReqRepo.findByRequestToId(userProfileId);
		
		for(FriendRequest request:requests) {
			int followerProfileId = request.getRequestById();
			
			UserProfile userWhoFollowed = userProfileRepo.findById(followerProfileId).get();
			
			//Getting the image and transforming it to a string using Base64 encoder
			byte[] imgBytes = getImageOfTheFollower(userWhoFollowed,path);
			
			String base64Encoding = Base64.encodeBase64String(imgBytes);
			
			//Get the appropriate image extension
			int imageLength = userWhoFollowed.getProfilePicName().length();
			String imageExtension = userWhoFollowed.getProfilePicName().substring(imageLength-4, imageLength);
			
			FriendRequestDto friendRequests = new FriendRequestDto();
			friendRequests.setBase64ProfilePicture(base64Encoding);
			friendRequests.setRequestById(userWhoFollowed.getUserProfileId());
			friendRequests.setRequestByName(userWhoFollowed.getDisplayName());
			
			friendRequestsToUser.add(friendRequests);
			
		}
		
		return friendRequestsToUser;
		
	}

	private byte[] getImageOfTheFollower(UserProfile userWhoFollowed, String path) {
		
		int userProfileId = userWhoFollowed.getUserProfileId();
		String userProfileImage = userWhoFollowed.getProfilePicName();
		
		String fileName = userProfileId+""+'/'+ userProfileImage;
		
		Path imagePath = Paths.get(path+fileName);
		
		try {
			return Files.readAllBytes(imagePath);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
		//This approach is slow
//		byte [] imgBytes = null;
//		
//		try {
//			File file = new File(path+fileName);
//			
//			FileInputStream inputStream = new FileInputStream(file);
//			
//			imgBytes = new byte[(int)file.length()];
//			
//			int track = 0;
//			while(true) {
//				int i = inputStream.read();
//				
//				if(i == -1) {
//					break;
//				}else {
//					imgBytes[track] = (byte)i;
//					track++;
//				}
//			}
//			
//			return imgBytes;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return imgBytes;
	}

	public void deleteTheRequestAndAcceptTheRequest(int to, int from) {
		
		FriendRequest request = friendReqRepo.findByRequestToIdAndRequestById(to, from);
		
		//Adding from as a follower in to's following list
		Set<UserProfile> userProfiles = new HashSet<>();
		UserProfile profile = new UserProfile();
		profile.setUserProfileId(to);
		userProfiles.add(profile);
		
		Following following = new Following();
		following.setUserProfileIdFollowing(from);
		following.setFollowingName(request.getRequestByName());
		following.setUserProfile(userProfiles);
		
		followingRepo.save(following);
		
		//Adding to as a following in from's followers list
		Set<UserProfile> userProfilesFollowers = new HashSet<>();
		UserProfile profileFollower = new UserProfile();
		profileFollower.setUserProfileId(from);
		userProfilesFollowers.add(profileFollower);
		
		Followers follower = new Followers();
		follower.setUserProfileIdFollower(to);
		follower.setFollowerName(request.getRequestToName());
		follower.setUserProfile(userProfilesFollowers);
		
		followerRepo.save(follower);
		
		//Delete the friend Request
		friendReqRepo.delete(request);
		
	}

	public void deleteTheRequestAndRejectTheRequest(int to, int from) {
		
		FriendRequest request = friendReqRepo.findByRequestToIdAndRequestById(to, from);
		friendReqRepo.delete(request);
		
	}

	public List<UserProfileIdOfFriendRequest> getProfileIdRequests(int userProfileId) {
		
		List<FriendRequest> requests = friendReqRepo.findByRequestToId(userProfileId);
		
		List<UserProfileIdOfFriendRequest> requestsToUser = new ArrayList<>();
		
		for(FriendRequest request:requests) {
			UserProfileIdOfFriendRequest userRequest = new UserProfileIdOfFriendRequest();
			
			userRequest.setUserProfileId(request.getRequestById());
			
			requestsToUser.add(userRequest);
		}
		
		return requestsToUser;
	}
	
	

}
