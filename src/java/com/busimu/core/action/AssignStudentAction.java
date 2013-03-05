/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import java.util.List;
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
public class AssignStudentAction extends ActionSupport implements SessionAware {

	/**
     * 
     */
	private static final long   serialVersionUID = 1L;

	/** Class revision */
	public static final String  _REV_ID_         = "$Revision: $";

	/**
	 * {@inheritDoc}
	 */

	private List<String>        studentIds;

	private String              operation;

	private String              corpId;

	private String              campaignId;

	private String              marketId;

	private Map<String, Object> sessionMap;

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setStudentIds(List<String> studentIds) {
		this.studentIds = studentIds;
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

	@Override
	public String execute() throws Exception {
		User user = (User) sessionMap.get(SessionScopeKey.USER);
		if (operation != null) {
			if(studentIds == null) {
				return Action.SUCCESS;//Do nothing
			}
			if (user.getType() == User.Type.TEACHER
			        || (studentIds.size() == 1 && String.valueOf(user.getId()).equals(studentIds.get(0)))) {
				if (operation.equals("assign")) {
					CampaignManagement.getInstance().assignStudentInCorp(studentIds, corpId);
				} else if (operation.equals("delete")) {
					CampaignManagement.getInstance().removeStudentFromCampaign(studentIds, campaignId);
					if(user.getType() == User.Type.STUDENT) {
						sessionMap.remove(SessionScopeKey.CAMPAIGN);
						return "home";
					}
				}
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

}
