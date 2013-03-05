/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import java.awt.Color;
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
import javax.persistence.MapKey;
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
@Table(name = "MARKET")
public class SimMarket implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@Column(name = "NAME", nullable = false)
	private String             name;
	
	@ManyToOne
	@JoinColumn(name = "CAMPAIGN_ID", updatable = false, nullable = false)
	private Campaign campaign;
	
	@OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SimCorporation> corporations = new HashSet<SimCorporation>();
	
	@MapKey(name = "parameterName")
	@OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
	private Map<String, ParameterData> parameterDatas = new HashMap<String, ParameterData>();
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="MARKET_META", 
    	joinColumns={@JoinColumn(name = "MARKET_ID")}
    )
	protected Map<String, Meta> metas = new HashMap<String, Meta>();
	
	protected SimMarket(){
	}
	
	protected SimMarket(String name, Campaign campaign) {
		this.name = name;
		this.campaign = campaign;
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
	
	
	public ParameterData getParameterData(String key){
		return parameterDatas.get(key);
	}
	
	public ParameterData setParameterData(String key, ParameterData data) {
		return parameterDatas.put(key, data);
	}

	/**
     * @return the markets
     */
    public Set<SimCorporation> getCorporations() {
    	return corporations;
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
    public void setName(String name) throws DataValidationException{
    	if(!getCampaign().getMarkets().contains(new SimMarket(name, getCampaign()))) {
    		this.name = name;
    	} else {
    		throw new DataValidationException("Market name " + name + " already exist in Campaign " + getCampaign().getCampaignId(), DataValidationErrorCode.MARKET_NAME_ALREADY_EXIST);
    	}
    }

	/**
     * @return the id
     */
    public long getId() {
    	return id;
    }

	/**
     * @return the campaign
     */
    public Campaign getCampaign() {
    	return campaign;
    }
    
    public SimCorporation addCorporation (String name, Color color) throws DataValidationException{
    	SimCorporation corportation = new SimCorporation(name, color, this);
    	if(!getCorporations().add(corportation)) {
    		throw new DataValidationException("Corporation name " + name + "already exist in Market "+ getName() + " of Campaign " + getCampaign().getCampaignId(), DataValidationErrorCode.CORP_NAME_ALREADY_EXIST);
    	}
    	return corportation;
    }
    
    public void removeCorporation(SimCorporation corp) {
    	getCorporations().remove(corp);
    }
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SimMarket){
			SimMarket that = (SimMarket)obj;
			if(getCampaign().equals(that.getCampaign()) && getName().equals(that.getName())) {
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
		return getCampaign().hashCode() * getName().hashCode();
	}

}
