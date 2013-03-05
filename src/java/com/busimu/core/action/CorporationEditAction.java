/*
 * $HeadURL:  $
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
 * @author Administrator
 * @version $Revision: $
 */
public class CorporationEditAction extends ActionSupport implements SessionAware{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String campaignId;
	
	private String marketId;
	
	private String corpName;
	
	private String corpId;
	
	private Map<String, Object> sessionMap;
	
	public String getCorpId() {
    	return corpId;
    }

	public void setCorpId(String corpId) {
    	this.corpId = corpId;
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
     * @return the title
     */
    @RequiredStringValidator(message = "Name Required", 
			shortCircuit = true,
			key = "market.edit.error.input.corp.name.required"
	)
	public String getCorpName() {
    	return corpName;
    }

	public void setCorpName(String corpName) {
    	this.corpName = corpName;
    }

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
		if(user == null) {
			throw new RuntimeException("No permission to change corporation name, current user: " + user);
		}
		try {
			CampaignManagement.getInstance().editCorpName(corpId, corpName);
			return Action.SUCCESS;
		} catch (DataValidationException e) {
			if(e.getErrorCode() == DataValidationErrorCode.CORP_NAME_ALREADY_EXIST) {
				addFieldError("corpName", getText("market.edit.error.input.corp.name.duplicate", new String[]{getCorpName()}));
			} else {
				throw new RuntimeException(e);
			}
		}
		return Action.INPUT;
		
    }

}
