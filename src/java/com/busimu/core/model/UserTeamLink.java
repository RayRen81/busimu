/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Entity
@Table(name = "USER_TEAM")
public class UserTeamLink {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "USER_ID", updatable = false, nullable = false)
	private User user;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "TEAM_ID", updatable = false, nullable = false)
	private Team team;
	
	
	/**
     * 
     */
    protected UserTeamLink() {
    }
	/**
     * 
     */
    protected UserTeamLink(User user, Team team) {
    	this.user = user;
    	this.team = team;
    }
	/**
     * @return the id
     */
    public long getId() {
    	return id;
    }

	/**
     * @return the user
     */
    public User getUser() {
    	return user;
    }

	/**
     * @return the team
     */
    public Team getTeam() {
    	return team;
    }
    
    protected void save() {
    	user.getUserTeamLinks().add(this);
    	team.getUserTeamLinks().add(this);
    }
    
    protected void delete(){
    	user.getUserTeamLinks().remove(this);
    	team.getUserTeamLinks().remove(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof UserTeamLink){
    		UserTeamLink that = (UserTeamLink)obj;
    		if(getUser().equals(that.getUser()) && getTeam().equals(that.getTeam())){
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
    	return getUser().hashCode()*getTeam().hashCode();
    }


}
