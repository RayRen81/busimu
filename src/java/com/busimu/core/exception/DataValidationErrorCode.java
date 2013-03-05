/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.exception;

/**
 * @author Ray
 * @version $Revision: $
 */
public class DataValidationErrorCode {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	public static final int UNKOWN_ERROR = 0;
	
	public static final int NONE_EXISTED_USER = 1;
	
	public static final int WRONG_PASSWORD = 2;
	
	public static final int USER_NAME_ALREADY_EXIST = 3;
	
	public static final int MARKET_NAME_ALREADY_EXIST = 4;
	
	public static final int CORP_NAME_ALREADY_EXIST = 5;
	
	public static final int ROUND_SEQ_ALREADY_EXIST = 6;
	
	public static final int COURSE_ID_INVALID = 7;
	
//	public String getErrorMessage(int errorCode){
//		String errorMessage = null;
//		switch(errorCode){
//		case NONE_EXISTED_USER : 
//			errorMessage = "There is NO such a user. ";
//			break;
//		case WRONG_PASSWORD : 
//			errorMessage = "Password is NOT right";
//			break;
//		default :
//			errorMessage = "UNKOWN ERROR";
//		}
//		return errorMessage;
//	}

}
