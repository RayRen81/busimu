/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/user/CampaignManagement.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.user;

import java.util.Date;
import java.util.List;

import com.busimu.core.dao.CampaignMngDaoPolicy;
import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.Campaign;
import com.busimu.core.model.Round;
import com.busimu.core.model.SimCorporation;
import com.busimu.core.model.SimMarket;
import com.busimu.core.model.User;
import com.busimu.core.model.Campaign.Status;

/**
 * @author elihuwu
 * @version $Revision: 57 $
 */
public class CampaignManagement {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 57 $";
	
	private CampaignMngDaoPolicy policy;
	
	/**
     * @return the policy
     */
    public CampaignMngDaoPolicy getPolicy() {
        return policy;
    }

    private static CampaignManagement instance;
	
	public void init(){
		instance = this;
	}
	
	public static CampaignManagement getInstance() {
    	return instance;
    }

	public void setCampaigMngDaoPolicy(CampaignMngDaoPolicy policy) {
    	this.policy = policy;
    }
	
	public Campaign createNewCampaign(User user, String title, int numOfMarket, int numOfCorpPerMarket, int numOfRound) throws DataValidationException {
		return policy.createNewCampaign(user, title, numOfMarket, numOfCorpPerMarket, numOfRound);
	}
	
	public User registerCampaign(User user, String campaignId) throws DataValidationException {
		return policy.registerCampaign(user, campaignId);
	}
	
	public Campaign getCampaignById(String campaignId){
		return policy.getCampaignById(campaignId);
	}
	
	public SimMarket getMarketById(String marketId) {
		return policy.getMarketById(marketId);
	}
	
	public void assignStudentInCorp(List<String> studentIds, String corpId) {
		policy.assignStudentInCorp(studentIds, corpId);
	}
	
	public void removeStudentFromTeam(String studentId, String teamId) {
		policy.removeStudentFromTeam(studentId, teamId);
	}
	
	public void removeStudentFromCampaign(List<String> studentIds, String campaignId) {
		policy.removeStudentFromCampaign(studentIds, campaignId);
	}
	
	public void saveScheduleEndDate(Campaign campaign, Date startDate, Date[] endDates){
		policy.saveScheduleEndDate(campaign, startDate, endDates);
	}
	
	public void saveScheduleLastTime(Campaign campaign, Date startDate, Long[] times){
		policy.saveScheduleLastTime(campaign, startDate, times);
	}
	
	public void editCampaign(String campaignName, String campaignId) {
		policy.editCampaign(campaignName, campaignId);
	}
	
	public SimMarket addMarketInCampaign(String campaignId, String marketName, int numOfCorp) throws DataValidationException {
		return policy.addMarketInCampaign(campaignId, marketName, numOfCorp);
	}
	
	public void editMarket(String marketId, String marketName) throws DataValidationException {
		policy.editMarket(marketId, marketName);
	}
	
	public SimCorporation addCorpInMarket(String marketId, String corpName) throws DataValidationException {
		return policy.addCorpInMarket(marketId, corpName);
	}
	
	public void editCorpName(String corpId, String corpName) throws DataValidationException {
		policy.editCorpName(corpId, corpName);
	}
	
	public void deleteCorp(String corpId) {
		policy.deleteCorp(corpId);
	}
	
	public void deleteMarket(String marketId) {
		policy.deleteMarket(marketId);
	}
	
	public void deleteCampaign(String campaignId) {
		policy.deleteCampaign(campaignId);
	}
	
	public Round addRound(String campaignId) throws DataValidationException {
		return policy.addRound(campaignId);
	}
	
	public void reduceRound(String campaignId) {
		policy.reduceRound(campaignId);
	}
	
	public void setStatusByTeacher(String campaignId, String status) {
		Campaign.Status newStatus = Campaign.Status.valueOf(status);
		if(newStatus == null) {
			throw new IllegalArgumentException("Status doesn't exist for Campaign: " + status);
		}
		Campaign campaign = policy.getCampaignById(campaignId);
		if(campaign == null) {
			throw new IllegalArgumentException("Campaign doesn't exist: " + campaignId);
		}
		Campaign.Status oldStatus = campaign.getStatus();
		if(oldStatus == Status.PREPARE && newStatus == Status.OPEN) {
			policy.setStatus(campaign, newStatus);
		} else if(oldStatus == Status.OPEN && (newStatus == Status.PREPARE || newStatus == Status.ONGOING)) {
			policy.setStatus(campaign, newStatus);
			if(newStatus == Status.ONGOING) {
				policy.updateCampaignSchedule(campaign);
			}
		} else {
			throw new IllegalArgumentException("Wrong Status to be changed. Old Status: " + oldStatus + " New Status: " + newStatus);
		}
		
	}

}
