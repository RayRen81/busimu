/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ray
 * @version $Revision: $
 */
@Entity
@Table(name = "LOGIN_HISTORY")
public class LoginHistory {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@Column(name = "LOGIN_DATE", nullable = false, updatable = false)
	private Date loginDate;
	
	@Column(name = "LOGOUT_DATE", nullable = false, updatable = false)
	private Date logoutDate;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", updatable = false, nullable = false)
	private User user;
	
	protected LoginHistory(){
		
	}
	/**
     * 
     */
    public LoginHistory(User user, Date loginDate, Date logoutDate) {
    	this.user = user;
    	this.loginDate = loginDate;
    	this.logoutDate = logoutDate;
    }
	
	public long getId() {
    	return id;
    }

	public Date getLoginDate() {
    	return loginDate;
    }

	public Date getLogoutDate() {
    	return logoutDate;
    }
	
	public User getUser() {
    	return user;
    }
	

}
