/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.taglib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.busimu.core.model.Campaign;
import com.busimu.core.model.LoginHistory;
import com.busimu.core.model.MetaAssociated;
import com.busimu.core.model.SimCorporation;
import com.busimu.core.model.SimMarket;
import com.busimu.core.model.User;
import com.busimu.core.user.UserManagement;

/**
 * @author Ray
 * @version $Revision: $
 */
public class UtilFunction {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	//TODO: Performance Issue?
	public static SimMarket getAssignedMarket(User u, Campaign c) {
		if(u.getType() == User.Type.TEACHER) {
			throw new IllegalArgumentException("User must be student: user id - " + u.getId());
		}
		for(SimMarket m : c.getMarkets()) {
			for(SimCorporation sc : m.getCorporations()) {
				if(sc.getTeam().getUsers().contains(u)) {
					return m;
				}
			}
		}
		return null;
	}
	
	public static Set<User> getUnassignedUsers(Campaign c) {
		Set<User> filterResult = new TreeSet<User>();
		if(c == null) {
			return filterResult;
		}
		filterResult.addAll(c.getUsers());
		for(SimMarket m : c.getMarkets()) {
			for(SimCorporation sc : m.getCorporations()) {
				for(User u : sc.getTeam().getUsers())
						filterResult.remove(u);
			}
		}
		return filterResult;
	}
	
	public static SimCorporation getJoinedCorp(User user, Campaign campaign){
        if(user == null || campaign == null) {
            return null;
        }
        
        for(SimMarket market : campaign.getMarkets()){
            for(SimCorporation corp : market.getCorporations()) {
                if(corp.getTeam().getUsers().contains(user)) {
                    return corp;
                }
            }
        }
        
        return null;
    }
	
	public static Date getMetaAsDate(MetaAssociated meta, String key) {
		if(meta == null || meta.getMeta(key) == null) {
			return null;
		}
		return new Date(Long.parseLong(meta.getMeta(key)));
	}
	
	public static List<LoginHistory> getRealLoginHistory(User user, Campaign campaign) {
		if(user == null || campaign == null) {
			return new ArrayList<LoginHistory>();
		}
		return UserManagement.getInstance().getRealLoginHistory(user, campaign);
	}
	
}
