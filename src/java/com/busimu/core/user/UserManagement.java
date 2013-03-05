/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.user;

import java.util.Date;
import java.util.List;

import com.busimu.core.dao.UserMngDaoPolicy;
import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.Campaign;
import com.busimu.core.model.LoginHistory;
import com.busimu.core.model.User;

/**
 * @author Ray
 * @version $Revision: $
 */
//TODO merge licenseGenerator to UserManagement, 
//TODO extract the interface, build a BeanEnv to refer it
public class UserManagement {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private UserMngDaoPolicy policy;
	
	private static UserManagement instance;
	
	public void init(){
		instance = this;
	}
	
	public static UserManagement getInstance() {
    	return instance;
    }

	public void setUserMngDaoPolicy(UserMngDaoPolicy policy) {
    	this.policy = policy;
    }

	public User registerNewUser(String email, String passwd, String name) {
		//TODO 检测已有用户
		User user = new User(email, passwd, name, User.Type.UNKNOWN);
		policy.saveUser(user);
		return user;
	}
	
	public User activateUser(User user, String licenseCode){
		return policy.activateUser(user, licenseCode);
	}
	
	/**
	 * 
	 * @param userName
	 * @param passwd
	 * @return 
	 * @throws DataValidationException 
	 */
	public User authenticateUser(String userName, String passwd) throws DataValidationException{
		return policy.authenticateUser(userName, passwd);
	}
	
	public LoginHistory addLoginHistory(User user, Date loginDate, Date logoutDate) {
		return policy.addLoginHistory(user, loginDate, logoutDate);
	}
	
	public User getUserById(String userId) {
		return policy.getUserById(userId);
	}
	
	public List<LoginHistory> getRealLoginHistory(User user, Campaign campaign) {
		return policy.getRealLoginHistory(user, campaign);
	}

}
