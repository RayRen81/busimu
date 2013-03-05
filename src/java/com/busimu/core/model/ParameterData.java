/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

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
@Table(name = "PARAMETER_DATA")
public class ParameterData implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@Column(name = "NAME", nullable = false)
	private String parameterName;
	
	@Column(name = "VALUE", nullable = false)
	private String parameterValue;
	
	@ManyToOne
	@JoinColumn(name = "MARKET_ID", nullable = false)
	private SimMarket market;
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="PARAMETER_DATA_META", 
    	joinColumns={@JoinColumn(name = "PARAMETER_DATA_ID")}
    )
	protected Map<String, Meta> metas = new HashMap<String, Meta>();

	
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
     * @return the parameterValue
     */
    public String getParameterValue() {
    	return parameterValue;
    }

	/**
     * @param parameterValue the parameterValue to set
     */
    public void setParameterValue(String parameterValue) {
    	this.parameterValue = parameterValue;
    }

	/**
     * @return the id
     */
    public long getId() {
    	return id;
    }

	/**
     * @return the parameterName
     */
    public String getParameterName() {
    	return parameterName;
    }

	/**
     * @return the market
     */
    public SimMarket getMarket() {
    	return market;
    }

}
