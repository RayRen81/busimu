/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.busimu.core.exception.DataValidationErrorCode;
import com.busimu.core.exception.DataValidationException;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Entity
@Table(name = "CAMPAIGN")
public class Campaign implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@Column(name = "NAME", nullable = false)
	private String             name;
	
	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	private Date               createDate    = new Date();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private Type  type;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private Status             status   = Status.PREPARE;
	
	@ManyToOne
	@JoinColumn(name = "HOLDER_ID", updatable = false, nullable = false)
	private User holder;
	
	@OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SimMarket> markets = new LinkedHashSet<SimMarket>();
	
	@OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserCampaignLink> userCampaignLinks = new HashSet<UserCampaignLink>();
	
	@OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Round> rounds = new LinkedHashSet<Round>();
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="CAMPAIGN_META", 
    	joinColumns={@JoinColumn(name = "CAMPAIGN_ID")}
    )
	private Map<String, Meta> metas = new HashMap<String, Meta>();

	protected Campaign(){
	}
	
	protected Campaign(String name, Type type, User holder){
		this.name = name;
		this.type = type;
		this.holder = holder;
		this.status = Status.PREPARE;
	}
	
	/**
     * {@inheritDoc}
     */
    @Override
    public String getMeta(String key) {
	    return new EntityWithMeta(getMetas()).getMeta(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public Meta setMeta(String key, String value) {
    	return new EntityWithMeta(getMetas()).setMeta(key, value);
	}
    

	protected Map<String, Meta> getMetas() {
    	return metas;
    }
	
	 /**
     * @return the status
     */
    public Status getStatus() {
    	return status;
    }

	/**
     * @param status the status to set
     */
    public void setStatus(Status status) {
    	this.status = status;
    }
	/**
     * @return the rounds
     */
    public List<Round> getSortedRoundsAsList() {
    	List<Round> roundLists = new ArrayList<Round>(rounds);
    	Collections.sort(roundLists);
    	return Collections.unmodifiableList(roundLists);
    }
    
    public Round addRound() throws DataValidationException{
    	Round round = new Round(this);
    	if(!getRounds().add(round)){
    		//impossible clause
    		throw new DataValidationException("Round " + round.getSequenceNum() +" already exist", DataValidationErrorCode.ROUND_SEQ_ALREADY_EXIST);
    	}
    	return round;
    }
    
    /**
     * 
     */
    public Set<Round> getRounds() {
    	return rounds;
    }
	
	public void addUser(User u){
    	new UserCampaignLink(u, this).save();
	}
	
	public void removeUser(User u){
		new UserCampaignLink(u, this).delete();
	}
	
	/**
     * @return the users
     */
    public Set<User> getUsers() {
    	Set<User> users = new HashSet<User>();
    	for(UserCampaignLink link : userCampaignLinks) {
    		users.add(link.getUser());
    	}
    	return Collections.unmodifiableSet(users);
    }
	
	/**
     * @return the userTeamLinks
     */
    protected Set<UserCampaignLink> getUserCampaignLinks() {
    	return userCampaignLinks;
    }

	/**
     * @return the markets
     */
    public Set<SimMarket> getMarkets() {
    	return this.markets; 
    }
	/**
     * @return the id
     */
    public long getId() {
    	return id;
    }
    
    /**
     * TODO: change id defintion later
     * @return
     */
    public String getCampaignId(){
    	return String.valueOf(getId());
    }
    
	/**
     * @return the name
     */
    public String getName() {
    	return name;
    }

	/**
     * @param name the name to set
     */
    public void setName(String name) {
    	this.name = name;
    }
    

	/**
     * @return the createDate
     */
    public Date getCreateDate() {
    	return createDate;
    }


	/**
     * @return the holder
     */
    public User getHolder() {
    	return holder;
    }
    
    /**
     * @return the type
     */
    public Type getType() {
    	return type;
    }

	/**
     * @param type the type to set
     */
    public void setType(Type type) {
    	this.type = type;
    }
    
    public SimMarket addMarket(String name) throws DataValidationException{
    	SimMarket market = new SimMarket(name, this);
    	if(!this.markets.add(market)){
    		throw new DataValidationException("Market name " + name + " already exist in Campaign " + getCampaignId(), DataValidationErrorCode.MARKET_NAME_ALREADY_EXIST);
    	}
		return market;
    }
    
    public void removeMarket(SimMarket market) {
    	getMarkets().remove(market);
    }
    
    public enum Type {
		COURSE;
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Campaign) {
    		return getCampaignId().equals(((Campaign)obj).getCampaignId());
    	} else {
    		return false;
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getCampaignId().toString().hashCode();
    }
    
    public enum Status{
		PREPARE, OPEN, ONGOING, LOCK, CLOSE, FINISH
	}

}
