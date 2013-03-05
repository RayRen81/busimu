/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/util/ValidationCode.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.util;

/**
 * @author elihuwu
 * @version $Revision: 29 $
 */
public class ValidationCode {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 29 $";
	
	private byte[] imageBytes;
	
	private String code;
	
	/**
     * 
     */
    public ValidationCode(String code, byte[] imageBytes) {
    	this.code = code;
    	this.imageBytes = imageBytes;
    }
	
	/**
     * @return the imageBytes
     */
    public byte[] getImageBytes() {
    	return imageBytes;
    }

	/**
     * @return the code
     */
    public String getCode() {
    	return code;
    }

}
