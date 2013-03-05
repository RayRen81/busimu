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
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.NotSupportedException;

import com.busimu.core.exception.DataValidationErrorCode;
import com.busimu.core.exception.DataValidationException;
import com.busimu.core.util.CommonUtil;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Entity
@Table(name = "CORPORATION")
public class SimCorporation implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	public static final Color[] PREDEFINED_COLORS = {
		Color.BLUE,
		new Color(128,128,255),
		Color.CYAN,
		new Color(128,255,255),
		Color.GREEN,
		new Color(128,255,128),
		Color.MAGENTA,
		new Color(255,128,255),
		Color.ORANGE,
		Color.ORANGE.brighter().brighter(),
		Color.PINK.brighter().brighter(),
		Color.PINK.darker().darker(),
		Color.RED,
		new Color(255,128,128),
		Color.YELLOW,
		new Color(255,255,128),
	};
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@Column(name = "NAME", nullable = false)
	private String             name;
	
	@Column(name = "COLOR", nullable = false)
	private String color;
	
	@ManyToOne
	@JoinColumn(name = "MARKET_ID", updatable = false, nullable = false)
	private SimMarket market;
	
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;
	
	@OneToMany(mappedBy = "corporation", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<DecisionData> decisionDatas = new HashSet<DecisionData>();
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="CORPORATION_META", 
    	joinColumns={@JoinColumn(name = "CORPORATION_ID")}
    )
	protected Map<String, Meta> metas = new HashMap<String, Meta>();
	
	protected SimCorporation(){
	}
	
	protected SimCorporation(String name, Color color, SimMarket market){
		this.name = name;
		this.color = CommonUtil.covertToHtmlColor(color);
		this.market = market;
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
	
	
	/**
     * @return the decision data
     */
    public Set<DecisionData> getDecisionDatas() {
    	return decisionDatas;
    }

	/**
     * @return the team
     */
    public Team getTeam() {
    	return team;
    }

	/**
     * @param team the team to set
     */
    protected void setTeam(Team team) {
    	this.team = team;
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
    	if(getMarket().getCorporations().contains(new SimCorporation(name, null, getMarket()))){
    		throw new DataValidationException("Corporation name " + name + "already exist in Market "+ getMarket().getName() + " of Campaign " + getMarket().getCampaign().getCampaignId(), DataValidationErrorCode.CORP_NAME_ALREADY_EXIST);
    	}
    	this.name = name;
    }

	/**
     * @return the color
     */
    public String getHtmlColor() {
    	return color;
    }
    
	/**
     * @param color the color to set
     */
    public void setHtmlColor(String color) {
    	this.color = color;
    }
    
    public Color getColor() throws NotSupportedException {
    	throw new NotSupportedException("This method should convert html color to java.awt.Color");
    }

	/**
     * @return the id
     */
    public long getId() {
    	return id;
    }

	/**
     * @return the market
     */
    public SimMarket getMarket() {
    	return market;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof SimCorporation) {
    		SimCorporation that = (SimCorporation)obj;
    		if(getMarket().equals(that.getMarket()) && getName().equals(that.getName())) {
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
        return getMarket().hashCode()*getName().hashCode();
    }
    
}
