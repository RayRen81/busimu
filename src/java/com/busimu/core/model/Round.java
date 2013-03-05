/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Entity
@Table(name = "ROUND")
public class Round implements MetaAssociated, Comparable<Round>{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@Column(name = "SEQ_NUM", nullable = false, updatable = false)
	private int sequenceNum; 
	
	@Column(name = "END_DATE")
	private Date               endDate;
	
	@Column(name = "LAST_TIME")
	private long lastTime = 0;
	
	@ManyToOne
	@JoinColumn(name = "CAMPAIGN_ID", updatable = false, nullable = false)
	private Campaign campaign;
	
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<DecisionData> decisionDatas = new HashSet<DecisionData>();
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="ROUND_META", 
    	joinColumns={@JoinColumn(name = "ROUND_ID")}
    )
	protected Map<String, Meta> metas = new HashMap<String, Meta>();
	
	/**
     * 
     */
    protected Round() {
    }
    
    protected Round(Campaign campaign) {
    	this.campaign = campaign;
    	this.sequenceNum = getMaxRoundSeq() + 1;
    }
    
    protected Map<String, Meta> getMetas() {
    	return metas;
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
	
    private int getMaxRoundSeq() {
    	Set<Round> rounds = getCampaign().getRounds();
    	int maxSeq = 0;
    	for(Round r : rounds) {
    		maxSeq = maxSeq < r.getSequenceNum()? r.getSequenceNum():maxSeq;
    	}
    	return maxSeq;
    }
    
	/**
     * @return the decisionDatas
     */
    public Set<DecisionData> getDecisionDatas() {
    	return decisionDatas;
    }

	/**
     * @return the endDate
     */
    public Date getEndDate() {
    	return endDate;
    }

	/**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }
    
    public long getLastTime() {
    	return lastTime;
    }

	public void setLastTime(long lastTime) {
    	this.lastTime = lastTime;
    }

	/**
     * @return the id
     */
    public long getId() {
    	return id;
    }

	/**
     * @return the sequenceNum
     */
    public int getSequenceNum() {
    	return sequenceNum;
    }
    
    public Campaign getCampaign() {
    	return campaign;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Round) {
    		Round that = (Round)obj;
    		if(getCampaign().equals(that.getCampaign()) && getSequenceNum() == that.getSequenceNum()) {
    			return true;
    		} 
    	}
    	return false;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
    	return getCampaign().hashCode()*getSequenceNum();
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Round o) {
	    return getSequenceNum() - o.getSequenceNum();
    }
    
    public enum Status {
    	NOT_START, ONGOING, LOCK, CLOSED
    }
    
}
