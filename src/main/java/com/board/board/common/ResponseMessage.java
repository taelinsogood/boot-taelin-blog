package com.board.board.common;

public interface ResponseMessage {
	// HTTP Status 200
	String SUCCESS = "Success";
	
	// HTTP Status 400
	String VALIDATION_FAILED = "Validation failed";
	String DUPLICATE_EMAIL = "Duplicate email";
	String DUPLICATE_TEL_NUMBER = "Duplicate tel number";
	String DUPLICATE_NICKNAME = "Duplicate nickname";
	String NOT_EXISTED_USER = "No Existed user";
	String NOT_EXISTED_BOARD = "No Existed board";
	
	// HTTP Status 200
	String SIGN_IN_FAIL = "Login information mismatch";
	String AUTHORIZATION_FAIL = "Authorization Failed";
	
	// HTTP Status 200
	String N0_PERMISSION = "No Permission";
	
	// HTTP Status 200
	String DATEBASE_ERROR = "Database Error";
}
