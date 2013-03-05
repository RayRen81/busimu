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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

/**
 * @author ray
 * @version $Revision: $
 */
@Entity
@Table(name = "LICENSE")
public class License implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;
	
	@Column(name = "LICENSE_CODE", nullable = false, unique = true, updatable = false)
	private String             licenseCode;
	
	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	private Date               createDate    = new Date();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private Type           type;
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="LICENSE_META", 
    	joinColumns={@JoinColumn(name = "LICENSE_ID")}
    )
	protected Map<String, Meta> metas = new HashMap<String, Meta>();
	
	@SuppressWarnings("unused")
    private License(){}
	
	public License(String licenseCode, Type type){
		this.licenseCode = licenseCode;
		this.type = type;
		this.createDate = new Date();
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
     * @return the id
     */
    public long getId() {
    	return id;
    }

	/**
     * @return the licenseCode
     */
    public String getLicenseCode() {
    	return licenseCode;
    }

	/**
     * @return the createDate
     */
    public Date getCreateDate() {
    	return createDate;
    }
    
    /**
     * @return the type
     */
    public Type getType() {
    	return type;
    }
    
    public enum Type {
		TEACHER, STUDENT
	}

}
