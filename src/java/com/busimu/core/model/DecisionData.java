/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import javax.persistence.Table;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Entity
@Table(name = "DECISION_DATA")
public class DecisionData implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	//FIXME change to map, refer parameter data
	@Column(name = "NAME", nullable = false)
	private String dataName;
	
	@Column(name = "VALUE", nullable = false)
	private String dataValue;
	
	@Column(name = "MODIFY_DATE")
	private Date               modifyTime;
	
	@ManyToOne
	@JoinColumn(name = "CORPORATION_ID", updatable = false, nullable = false)
	private SimCorporation corporation;
	
	@ManyToOne
	@JoinColumn(name = "ROUND_ID", updatable = false, nullable = false)
	private Round round;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", updatable = false, nullable = false)
	private User user;
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="DECISION_DATA_META", 
    	joinColumns={@JoinColumn(name = "DECISION_DATA_ID")}
    )
	private Map<String, Meta> metas = new HashMap<String, Meta>();
	
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
     * @return the dataValue
     */
    public String getDataValue() {
    	return dataValue;
    }

	/**
     * @param dataValue the dataValue to set
     */
    public void setDataValue(String dataValue) {
    	this.dataValue = dataValue;
    }

	/**
     * @return the modifyTime
     */
    public Date getModifyTime() {
    	return modifyTime;
    }

	/**
     * @param modifyTime the modifyTime to set
     */
    public void setModifyTime(Date modifyTime) {
    	this.modifyTime = modifyTime;
    }

	/**
     * @return the id
     */
    public long getId() {
    	return id;
    }

	/**
     * @return the dataName
     */
    public String getDataName() {
    	return dataName;
    }

	/**
     * @return the corporation
     */
    public SimCorporation getCorporation() {
    	return corporation;
    }

	/**
     * @return the round
     */
    public Round getRound() {
    	return round;
    }

	/**
     * @return the user
     */
    public User getUser() {
    	return user;
    }

}
