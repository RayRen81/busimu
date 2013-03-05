/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/action/CourseCreateAction.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.busimu.core.model.Campaign;
import com.busimu.core.model.User;
import com.busimu.core.user.CampaignManagement;
import com.busimu.core.util.SessionScopeKey;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author elihuwu
 * @version $Revision: 49 $
 */
public class CourseCreateAction extends ActionSupport implements SessionAware{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 49 $";
	
	private String title;
	
	private int numOfMarket;
	
	private int numOfCorpPerMarket;
	
	private int numOfRound;
	
	private Map<String, Object> sessionMap;
	
	private String campaignId;
	
	
	public String getCampaignId() {
    	return campaignId;
    }

	private void setCampaignId(String campaignId) {
    	this.campaignId = campaignId;
    }

	/**
     * @return the numOfMarket
     */
	@ConversionErrorFieldValidator(
			message = "Integer Required",
			key = "course.create.error.input.numOfMarket.type",
			shortCircuit = true
	)
	@IntRangeFieldValidator(
			message = "The range must be not zero",
			key = "course.create.error.input.numOfMarket.range",
			min = "1",
			shortCircuit = true
	)
    public int getNumOfMarket() {
    	return numOfMarket;
    }

	/**
     * @param numOfMarket the numOfMarket to set
     */
    public void setNumOfMarket(int numOfMarket) {
    	this.numOfMarket = numOfMarket;
    }

	/**
     * @return the numOfCorpPerMarket
     */
    
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
    public int getNumOfCorpPerMarket() {
    	return numOfCorpPerMarket;
    }

	/**
     * @param numOfCorpPerMarket the numOfCorpPerMarket to set
     */
    public void setNumOfCorpPerMarket(int numOfCorpPerMarket) {
    	this.numOfCorpPerMarket = numOfCorpPerMarket;
    }

	/**
     * @return the numOfRound
     */
    @ConversionErrorFieldValidator(
    		message = "Integer Required",
    		key = "course.create.error.input.numOfRound.type",
    		shortCircuit = true
    )
    @IntRangeFieldValidator(
    		message = "The range must be not zero",
    		key = "course.create.error.input.numOfRound.range",
    		min = "1",
    		shortCircuit = true
    )
    public int getNumOfRound() {
    	return numOfRound;
    }

	/**
     * @param numOfRound the numOfRound to set
     */
    public void setNumOfRound(int numOfRound) {
    	this.numOfRound = numOfRound;
    }

	/**
     * @return the title
     */
    @RequiredStringValidator(message = "Title Required", 
			shortCircuit = true,
			key = "course.create.error.input.title.required"
	)
    public String getTitle() {
    	return title;
    }

	/**
     * @param title the title to set
     */
    public void setTitle(String title) {
    	this.title = title;
    }
    
	/**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws Exception {
//    	String errorMsg = "Intenal Error";
    	User user = (User) sessionMap.get(SessionScopeKey.USER);
    	if(user == null || user.getType() != User.Type.TEACHER) {
    		String userName = user == null ? "" : user.getName();
    		addActionError(getText("course.create.error.user.permission", new String[]{userName}));
    		return Action.INPUT;
    	}
    	Campaign campaign = CampaignManagement.getInstance().createNewCampaign(user, title, numOfMarket, numOfCorpPerMarket, numOfRound + 1);
    	setCampaignId(campaign.getCampaignId());
    	return Action.SUCCESS;
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void setSession(Map<String, Object> map) {
	    this.sessionMap = map;
    }
	
	

}
