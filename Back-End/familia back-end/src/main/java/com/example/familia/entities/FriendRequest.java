package com.example.familia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int requestId;
	
	private int requestById;
	
	private String requestByName;
	
	private int requestToId;
	
	private String requestToName;

	public FriendRequest() {
		super();
	}

	public FriendRequest(int requestId, int requestById, String requestByName, int requestToId, String requestToName) {
		super();
		this.requestId = requestId;
		this.requestById = requestById;
		this.requestByName = requestByName;
		this.requestToId = requestToId;
		this.requestToName = requestToName;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getRequestById() {
		return requestById;
	}

	public void setRequestById(int requestById) {
		this.requestById = requestById;
	}

	public String getRequestByName() {
		return requestByName;
	}

	public void setRequestByName(String requestByName) {
		this.requestByName = requestByName;
	}

	public int getRequestToId() {
		return requestToId;
	}

	public void setRequestToId(int requestToId) {
		this.requestToId = requestToId;
	}

	public String getRequestToName() {
		return requestToName;
	}

	public void setRequestToName(String requestToName) {
		this.requestToName = requestToName;
	}

	@Override
	public String toString() {
		return "RequestId [requestId=" + requestId + ", requestById=" + requestById + ", requestByName=" + requestByName
				+ ", requestToId=" + requestToId + ", requestToName=" + requestToName + "]";
	}
	
}
