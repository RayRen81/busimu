/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.report.login;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.busimu.core.model.User;
import com.busimu.core.user.UserManagement;
import com.busimu.core.util.SessionScopeKey;

/**
 * @author Administrator
 * @version $Revision: $
 */
public class LoginListener implements HttpSessionListener{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	/**
     * {@inheritDoc}
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	System.out.println("destroyed");
    	User user = (User)se.getSession().getAttribute(SessionScopeKey.USER);
    	if(user != null && user.getType() == User.Type.STUDENT) {
    		Date loginDate = (Date)se.getSession().getAttribute(SessionScopeKey.LOGIN_DATE);
    		UserManagement.getInstance().addLoginHistory(user, loginDate, new Date());
    	}
    }

}
