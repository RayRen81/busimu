/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/action/RegisterCourseAction.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.busimu.core.exception.DataValidationErrorCode;
import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.User;
import com.busimu.core.user.CampaignManagement;
import com.busimu.core.util.SessionScopeKey;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author elihuwu
 * @version $Revision: 39 $
 */
public class RegisterCourseAction extends ActionSupport implements SessionAware{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 39 $";
	
	private String courseId;
	
	private Map<String, Object> sessionMap;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSession(Map<String, Object> map) {
	    this.sessionMap = map;
    }
    
    /**
     * @return the courseId
     */
    @RequiredStringValidator(message = "Please input username", 
			shortCircuit = true,
			key = "login.username.validation.required"
	)
    public String getCourseId() {
    	return courseId;
    }

	/**
     * @param courseId the courseId to set
     */
    public void setCourseId(String courseId) {
    	this.courseId = courseId;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws Exception {
    	User user = (User) sessionMap.get(SessionScopeKey.USER);
    	try {
    		user = CampaignManagement.getInstance().registerCampaign(user, courseId);
    		sessionMap.put(SessionScopeKey.USER, user);
    		return Action.SUCCESS;
    	} catch(DataValidationException e){
    		if(e.getErrorCode() == DataValidationErrorCode.COURSE_ID_INVALID) {
    			addFieldError("courseId", getText("user.index.student.registerCourse.error.input.courseId.invalid"));
    		}
    	}
    	return Action.INPUT;
    }
	
	

}
