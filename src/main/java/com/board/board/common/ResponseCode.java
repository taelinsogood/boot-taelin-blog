package com.board.board.common;

public interface ResponseCode {
	// HTTP Status 200
	String SUCCESS = "SU";
	
	// HTTP Status 400
	String VALIDATION_FAILED = "VF";
	String DUPLICATE_EMAIL = "DE";
	String DUPLICATE_TEL_NUMBER = "DT";
	String DUPLICATE_NICKNAME = "DN";
	String NOT_EXISTED_USER = "NU";
	String NOT_EXISTED_BOARD = "NB";
	
	// HTTP Status 200
	String SIGN_IN_FAIL = "SF";
	String AUTHORIZATION_FAIL = "AF";
	
	// HTTP Status 200
	String N0_PERMISSION = "NP";
	
	// HTTP Status 200
	String DATEBASE_ERROR = "DBE";
}
