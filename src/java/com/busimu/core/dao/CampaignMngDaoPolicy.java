/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/dao/CampaignMngDaoPolicy.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.dao;

import java.util.Date;
import java.util.List;

import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.Campaign;
import com.busimu.core.model.Round;
import com.busimu.core.model.SimCorporation;
import com.busimu.core.model.SimMarket;
import com.busimu.core.model.User;

/**
 * @author elihuwu
 * @version $Revision: 57 $
 */
public interface CampaignMngDaoPolicy {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 57 $";
	
	Campaign createNewCampaign(User user, String title, int numOfMarket, int numOfCorpPerMarket, int numOfRound) throws DataValidationException;
	
	User registerCampaign(User user, String campaignId) throws DataValidationException;
	
	Campaign getCampaignById(String campaignId);
	
	SimMarket getMarketById(String marketId);
	
	void assignStudentInCorp(List<String> studentIds, String corpId);
	
	void removeStudentFromTeam(String studentId, String teamId);
	
	void removeStudentFromCampaign(List<String> studentIds, String campaignId);
	
	void saveScheduleEndDate(Campaign campaign, Date startDate, Date[] endDates);
	
	void saveScheduleLastTime(Campaign campaign, Date startDate, Long[] times);
	
	void editCampaign(String campaignName, String campaignId);
	
	SimMarket addMarketInCampaign(String campaignId, String marketName, int numOfCorp) throws DataValidationException;
	
	void editMarket(String marketId, String marketName) throws DataValidationException;
	
	SimCorporation addCorpInMarket(String marketId, String corpName) throws DataValidationException;
	
	void editCorpName(String corpId, String corpName) throws DataValidationException;
	
	void deleteCorp(String corpId);
	
	void deleteMarket(String marketId);
	
	void deleteCampaign(String campaignId);
	
	Round addRound(String campaignId) throws DataValidationException;
	
	void reduceRound(String campaignId);
	
	void setStatus(Campaign campaign, Campaign.Status status);
	
	void updateCampaignSchedule(Campaign campaign);

    /**
     * @param roundId
     * @return
     */
    Round getRoundById(String roundId);

}
