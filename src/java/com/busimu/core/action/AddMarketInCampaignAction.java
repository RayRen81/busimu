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
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author Administrator
 * @version $Revision: $
 */
public class AddMarketInCampaignAction extends ActionSupport implements SessionAware{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String marketName;
	
	private int numOfCorp;
	
	private String campaignId;
	
	private Map<String, Object> sessionMap;
	
	
	public String getCampaignId() {
    	return campaignId;
    }

	public void setCampaignId(String campaignId) {
    	this.campaignId = campaignId;
    }

	@ConversionErrorFieldValidator(
    		message = "Integer Required",
    		key = "course.create.error.input.numOfCorpPerMarket.type",
    		shortCircuit = true
    )
    @IntRangeFieldValidator(
    		message = "The range must be not zero",
    		key = "course.create.error.input.numOfCorpPerMarket.range",
    		min = "1",
    		max = "12",
    		shortCircuit = true
    )
	public int getNumOfCorp() {
    	return numOfCorp;
    }

	public void setNumOfCorp(int numOfCorp) {
    	this.numOfCorp = numOfCorp;
    }

	/**
     * @return the title
     */
    @RequiredStringValidator(message = "Name Required", 
			shortCircuit = true,
			key = "course.edit.error.input.market.name.required"
	)
	public String getMarketName() {
    	return marketName;
    }

	public void setMarketName(String marketName) {
    	this.marketName = marketName;
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
		if(user == null || user.getType() != User.Type.TEACHER) {
			throw new RuntimeException("No permission to add market, current user: " + user);
		}
		try {
			CampaignManagement.getInstance().addMarketInCampaign(campaignId, marketName, numOfCorp);
			return Action.SUCCESS;
		} catch (DataValidationException e) {
			if(e.getErrorCode() == DataValidationErrorCode.MARKET_NAME_ALREADY_EXIST) {
				addFieldError("marketName", getText("course.edit.error.input.market.name.duplicate", new String[]{marketName}));
			} else {
				throw new RuntimeException(e);
			}
		}
		return Action.INPUT;
	}

}
