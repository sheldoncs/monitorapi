package com.qless.api.monitor.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponseDTO {
	
	private HttpStatus status;
	private int statusId;
	private String message = null;
	
	public int getStatusId() {
		return statusId;
	
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
