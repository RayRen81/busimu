/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.taglib;

import com.busimu.core.model.Campaign;
import com.busimu.core.model.Round;
import com.busimu.core.model.SimMarket;
import com.busimu.core.model.User;
import com.busimu.core.user.CampaignManagement;
import com.busimu.core.user.UserManagement;

/**
 * @author Ray
 * @version $Revision: $
 */
public class EntityLookupFunction {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	public static Campaign getCampaignById(String campaignId) {
		return CampaignManagement.getInstance().getCampaignById(campaignId);
	}
	
	public static SimMarket getMarketById(String marketId) {
		return CampaignManagement.getInstance().getMarketById(marketId);
	}
	
	public static User getUserById(String userId) {
		return UserManagement.getInstance().getUserById(userId);
	}
	
	public static Round getRoundById(String roundId) {
        return CampaignManagement.getInstance().getPolicy().getRoundById(roundId);
    }

}
