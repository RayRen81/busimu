/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.busimu.core.model.User;
import com.busimu.core.user.UserManagement;
import com.busimu.core.util.SessionScopeKey;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Ray
 * @version $Revision: $
 */
public class ActivateAction extends ActionSupport implements SessionAware{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";

	private String license;
	
	public void setLicense(String license) {
    	this.license = license;
    }
	
	private Map<String, Object> sessionMap;
	
	/**
     * {@inheritDoc}
     */
    @Override
    public void setSession(Map<String, Object> sessionMap) {
    	this.sessionMap = sessionMap;
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws Exception {
    	User user = (User) sessionMap.get(SessionScopeKey.USER);
    	if(user != null && user.getType() == User.Type.UNKNOWN) {
    		user = UserManagement.getInstance().activateUser(user, license);
    		if(user.getType() != User.Type.UNKNOWN){
    			sessionMap.put(SessionScopeKey.USER, user);
    			return Action.SUCCESS;
    		} else {
    			addFieldError("license", "The license is not valid");
    			return Action.ERROR;
    		}
    	}else{
    		//TODO log this abnormal behavior
    		return Action.NONE;
    	}
    }

}
