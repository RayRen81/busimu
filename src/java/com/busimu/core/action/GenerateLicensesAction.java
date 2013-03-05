/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.busimu.core.license.LicenseGenerator;
import com.busimu.core.model.License;
import com.opensymphony.xwork2.Action;

/**
 * @author Ray
 * @version $Revision: $
 */
public class GenerateLicensesAction implements Action{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private int number;
	
	/**
     * @param number the number to set
     */
    public void setNumber(int number) {
    	this.number = number;
    }
    
    private License.Type type;
    

	public void setType(String type) {
    	this.type = License.Type.valueOf(type.toUpperCase());
    }
	
	public String getFileName(){
		return type.name().toLowerCase()+ "_" + number + "_licenses";
	}

	/**
     * {@inheritDoc}
     */
	//TODO very slow due to db
	public InputStream getLicenseFile(){
		StringBuffer licenses = LicenseGenerator.getInstance().getLicenses(number, type);
		try {
	        return new ByteArrayInputStream(licenses.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
        }
	}
	
    @Override
    public String execute() throws Exception {
	    return Action.SUCCESS;
    }
    
}
