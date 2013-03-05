/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.util;

/**
 * @author Administrator
 * @version $Revision: $
 */
public class CommonConfig {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String EndDatePatternForRound;
	
	private static CommonConfig instance;
	
	public void init(){
		instance = this;
	}
	
	public static CommonConfig getInstance() {
    	return instance;
    }

	public String getEndDatePatternForRound() {
    	return EndDatePatternForRound;
    }

	public void setEndDatePatternForRound(String endDatePatternForRound) {
    	EndDatePatternForRound = endDatePatternForRound;
    }

}
