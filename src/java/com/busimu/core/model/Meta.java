/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Embeddable
public class Meta {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Column(name = "VALUE", length=2000)
	private String value;
	
	protected Meta(String value){
		this.value = value;
	}
	
	Meta(){
	}

	/**
     * @return the value
     */
    public String getValue() {
    	return value;
    }

}
