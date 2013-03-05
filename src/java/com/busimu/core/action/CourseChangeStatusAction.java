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
import com.busimu.core.user.CampaignManagement;
import com.busimu.core.util.SessionScopeKey;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * @version $Revision: $
 */
public class CourseChangeStatusAction extends ActionSupport implements SessionAware{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String campaignId;
	
	private String courseStatus;
	
	private Map<String, Object> sessionMap;
	
	public void setCampaignId(String campaignId) {
    	this.campaignId = campaignId;
    }
	
	public String getCampaignId() {
    	return campaignId;
    }
	
	public String getCourseStatus() {
    	return courseStatus;
    }

	public void setCourseStatus(String courseStatus) {
    	this.courseStatus = courseStatus;
    }
	
	/**
     * {@inheritDoc}
     */
    @Override
    public void setSession(Map<String, Object> map) {
	    this.sessionMap = map;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws Exception {
    	User user = (User) sessionMap.get(SessionScopeKey.USER);
		if(user == null || user.getType() != User.Type.TEACHER) {
			throw new RuntimeException("No permission to delete corporation, current user: " + user);
		}
		CampaignManagement.getInstance().setStatusByTeacher(campaignId, courseStatus);
        return Action.SUCCESS;
    }

}