package com.example.familia.dtos;

public class RequestFriendDto {
	
	private int requestById;
	
	private String requestByName;
	
	private int requestToId;
	
	private String requestToName;

	public RequestFriendDto() {
		super();
	}

	public RequestFriendDto(int requestById, String requestByName, int requestToId, String requestToName) {
		super();
		this.requestById = requestById;
		this.requestByName = requestByName;
		this.requestToId = requestToId;
		this.requestToName = requestToName;
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
		return "RequestFriendDto [requestBy=" + requestById + ", requestByName=" + requestByName + ", requestTo="
				+ requestToId + ", requestToName=" + requestToName + "]";
	}

}
