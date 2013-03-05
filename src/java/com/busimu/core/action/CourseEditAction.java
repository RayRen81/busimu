/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import com.busimu.core.user.CampaignManagement;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author Administrator
 * @version $Revision: $
 */
public class CourseEditAction extends ActionSupport {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private String campaignId;
	
	private String campaignName;
	
	public void setCampaignId(String campaignId) {
    	this.campaignId = campaignId;
    }
	
	public String getCampaignId() {
    	return campaignId;
    }
	
	@RequiredStringValidator(message = "Name Required", 
			shortCircuit = true,
			key = "course.create.error.input.title.required"
	)
	public String getCampaignName() {
    	return campaignName;
    }

	public void setCampaignName(String campaignName) {
    	this.campaignName = campaignName;
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute() throws Exception {
		CampaignManagement.getInstance().editCampaign(campaignName, campaignId);
		return Action.SUCCESS;
	}

}
