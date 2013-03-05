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
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

/**
 * @author Ray
 * @version $Revision: $
 */
public class RegisterAction extends ActionSupport implements SessionAware {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";

	private String email;
	
	private String name;
	
	private String password;
	
	private String verifyPassword;
	
	private Map<String, Object> sessionMap;
	
	
	public void setUsername(String name) {
    	this.name = name;
    }
	
	/**
     * @return the name
     */
    @RequiredStringValidator(
			message = "username can not be empty",
			shortCircuit = true
	)
    public String getUsername() {
    	return name;
    }

	public void setPassword(String password) {
    	this.password = password;
    }
	
	/**
     * @return the password
     */
    @RequiredStringValidator(
			message = "password can not be empty",
			shortCircuit = true
	)
	@StringLengthFieldValidator(
			message = "password can not less than 6 charactors",
			shortCircuit = true,
			minLength = "6"
	)
    public String getPassword() {
    	return password;
    }
	
	public void setEmail(String email) {
    	this.email = email;
    }
	
	/**
     * @return the verifyPassword
     */
	@RequiredStringValidator(
			message = "verify password can not be empty",
			shortCircuit = true
	)
	@FieldExpressionValidator(
			message = "password and verification passowrd must be identical",
			expression = "password == verifyPassword",
			shortCircuit = true
	)
    public String getVerifyPassword() {
    	return verifyPassword;
    }

	/**
     * @param verifyPassword the verifyPassword to set
     */
    public void setVerifyPassword(String verifyPassword) {
    	this.verifyPassword = verifyPassword;
    }
	
	/**
     * @return the email
     */
	@RequiredStringValidator(
			message = "email can not be empty",
			shortCircuit = true
	)
	@EmailValidator(
			message="the format of email adress is not correct",
			shortCircuit=true
	)
    public String getEmail() {
    	return email;
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws Exception {
	    User user = UserManagement.getInstance().registerNewUser(email, password, name);
	    sessionMap.put(SessionScopeKey.USER, user);
	    return Action.SUCCESS;
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void setSession(Map<String, Object> sessionMap) {
    	this.sessionMap = sessionMap;
    }

}
