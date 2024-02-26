package com.khit.recruit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//사용자 Exception은 RuntimeException을 상속 받음
//@ResponseStatus - 404 오류를 표시하는 클래스
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BootBoardException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BootBoardException(String message) {
		super(message);
	}
	
}
