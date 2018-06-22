package com.qless.api.monitor.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qless.api.monitor.dto.ErrorResponseDTO;
import com.qless.api.monitor.exceptions.EntityNotFoundException;

@RestControllerAdvice
public class CustomMonitorAdvice {
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorResponseDTO handleEmptyResultDataAccessException(EntityNotFoundException exception) {
		ErrorResponseDTO response = new ErrorResponseDTO();
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setMessage("Resource Not Found: "+ exception.getMessage());
		
		return response;
	}
	
}
