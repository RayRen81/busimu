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
 * @author Ray
 * @version $Revision: $
 */
public class UnassignStudentAction extends ActionSupport implements SessionAware{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String studentId;
	

	private String teamId;
	
	private String campaignId;
	
	private String marketId;
	
	private Map<String, Object> sessionMap;
	
	public void setStudentId(String studentId) {
    	this.studentId = studentId;
    }

	public void setTeamId(String teamId) {
    	this.teamId = teamId;
    }
	
	public String getCampaignId() {
    	return campaignId;
    }

	public void setCampaignId(String campaignId) {
    	this.campaignId = campaignId;
    }
	
	public String getMarketId() {
    	return marketId;
    }

	public void setMarketId(String marketId) {
    	this.marketId = marketId;
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute() throws Exception {
		User user = (User) sessionMap.get(SessionScopeKey.USER);
		if(user.getType().equals(User.Type.TEACHER) || String.valueOf(user.getId()).equals(studentId)) {
			CampaignManagement.getInstance().removeStudentFromTeam(studentId, teamId);
			return Action.SUCCESS;
		} else {
			return Action.ERROR;
		}
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public void setSession(Map<String, Object> sessionMap) {
	    this.sessionMap = sessionMap;
    }

}
