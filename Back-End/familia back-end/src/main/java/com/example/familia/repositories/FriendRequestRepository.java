package com.example.familia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.familia.dtos.UserProfileIdOfFriendRequest;
import com.example.familia.entities.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
	
	List<FriendRequest> findByRequestToId(int requestToId);
	
	FriendRequest findByRequestToIdAndRequestById(int requestToId,int requestById);

}
