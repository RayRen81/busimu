/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/model/UserCampaignLink.java $
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
 * @author elihuwu
 * @version $Revision: 43 $
 */
@Entity
@Table(name = "USER_CAMPAIGN")
public class UserCampaignLink {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 43 $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "USER_ID", updatable = false, nullable = false)
	private User user;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "CAMPAIGN_ID", updatable = false, nullable = false)
	private Campaign campaign;
	
	
	/**
     * 
     */
    protected UserCampaignLink() {
    }
	/**
     * 
     */
    protected UserCampaignLink(User user, Campaign campaign) {
    	this.user = user;
    	this.campaign = campaign;
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
    public Campaign getCampaign() {
    	return campaign;
    }
    
    protected void save() {
    	user.getUserCampaignLinks().add(this);
    	campaign.getUserCampaignLinks().add(this);
    }
    
    protected void delete(){
    	user.getUserCampaignLinks().remove(this);
    	campaign.getUserCampaignLinks().remove(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof UserCampaignLink){
    		UserCampaignLink that = (UserCampaignLink)obj;
    		if(getUser().equals(that.getUser()) && getCampaign().equals(that.getCampaign())){
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
    	return getUser().hashCode()*getCampaign().hashCode();
    }


}
