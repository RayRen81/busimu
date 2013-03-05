/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.busimu.core.model.Campaign;
import com.busimu.core.model.User;
import com.busimu.core.user.CampaignManagement;
import com.busimu.core.util.CommonConfig;
import com.busimu.core.util.CommonUtil;
import com.busimu.core.util.SessionScopeKey;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Ray
 * @version $Revision: $
 */
public class SaveScheduleAction extends ActionSupport implements ParameterAware, SessionAware {

	/**
     * 
     */
	private static final long     serialVersionUID = 1L;

	/** Class revision */
	public static final String    _REV_ID_         = "$Revision: $";

	private Map<String, String[]> params;

	private String                campaignId;

	private Map<String, Object>   sessionMap;
	
	private String startDate;

	public String getStartDate() {
    	return startDate;
    }

	public void setStartDate(String startDate) {
    	this.startDate = startDate;
    }

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute() throws Exception {
		String fieldName = null;
		String dateFormat = CommonConfig.getInstance().getEndDatePatternForRound();
		try {
			User user = (User) sessionMap.get(SessionScopeKey.USER);
			if (user != null && user.getType() == User.Type.TEACHER) {
				Campaign campaign = CampaignManagement.getInstance().getCampaignById(campaignId);

				fieldName = "startDate";
				Date start = CommonUtil.parseDate(startDate, dateFormat);
				if (params.keySet().contains("endDate1")) {
					List<Date> dateLists = new ArrayList<Date>();
					int i = 1;
					while (true) {
						fieldName = "endDate" + i++;
						if (params.get(fieldName) == null) {
							break;
						} else {
							dateLists.add(CommonUtil.parseDate(params.get(fieldName)[0], dateFormat));
						}
					}
					
					CampaignManagement.getInstance().saveScheduleEndDate(campaign, start, dateLists.toArray(new Date[0]));
					
				}

				if (params.keySet().contains("lastTime1")) {
					List<Long> timeLists = new ArrayList<Long>();
					int i = 1;
					while(true) {
						fieldName = "lastTime" + i++;
						if (params.get(fieldName) == null) {
							break;
						} else {
							timeLists.add(Long.parseLong(params.get(fieldName)[0]));
						}
					}
					fieldName = "startDate";
					CampaignManagement.getInstance().saveScheduleLastTime(campaign, start, timeLists.toArray(new Long[0]));
					
				}
			} else {
				throw new RuntimeException("No permission to save schedule, current user: " + user);
			}
		} catch (ParseException e) {
			addFieldError(fieldName, getText("course.schedule.error.input.round.endDate.format", new String[] {
			        params.get(fieldName)[0], CommonUtil.formatDate(new Date(), dateFormat) }));
		} catch (NumberFormatException e) {
			addFieldError(fieldName, getText("course.schedule.error.input.round.lastTime.format", new String[] {
			        params.get(fieldName)[0] }));
		}

		if (!hasFieldErrors() && !hasActionErrors()) {
			return Action.SUCCESS;
		}
		return Action.INPUT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setParameters(Map<String, String[]> params) {
		this.params = params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

}
