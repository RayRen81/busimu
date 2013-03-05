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
public class DataValidationException extends Exception{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private int errorCode = 0;
	
    public int getErrorCode() {
    	return errorCode;
    }

	public DataValidationException(String message, int errorCode) {
    	super(message);
    	this.errorCode = errorCode;
    }
    
    public DataValidationException(Throwable cause, int errorCode){
    	super(cause);
    	this.errorCode = errorCode;
    }
    
    public DataValidationException(String message, Throwable cause, int errorCode){
    	super(message, cause);
    	this.errorCode = errorCode;
    }

}
