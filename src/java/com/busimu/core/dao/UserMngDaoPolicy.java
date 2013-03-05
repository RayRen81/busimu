/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.Campaign;
import com.busimu.core.model.License;
import com.busimu.core.model.LoginHistory;
import com.busimu.core.model.User;

/**
 * @author wang
 * @version $Revision: $
 */
public interface UserMngDaoPolicy {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	boolean isExistedLicense(String licenseCode);
	
	void storeLicenses(Set<License> licenses);
	
	void saveUser(User user);
	
	User activateUser(User user, String license);

	/**
     * @param userName
     * @param passwd
     * @return
     * @throws DataValidationException
     */
    User authenticateUser(String userName, String passwd) throws DataValidationException;
    
    LoginHistory addLoginHistory(User user, Date loginDate, Date logoutDate);
    
    User getUserById(String userId);
    
    List<LoginHistory> getRealLoginHistory(User user, Campaign campaign);

}
