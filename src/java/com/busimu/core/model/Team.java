/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import java.util.Collections;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Entity
@Table(name = "TEAM")
public class Team implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_      = "$Revision: $";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;

	@Column(name = "NAME", nullable = false, unique = true)
	private String             name;

	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	private Date               createDate    = new Date();

	@Column(name = "LAST_ALIVE", nullable = false)
	private Date               lastAliveDate = new Date();

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private Type               type          = Type.ANONYM;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private Status             status        = Status.UNLOCK;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserTeamLink> userTeamLinks = new HashSet<UserTeamLink>();
	
	//FIXME add an immediate table?
	@OneToMany(mappedBy = "team")
	private Set<SimCorporation> corporations = new HashSet<SimCorporation>();
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="TEAM_META", 
    	joinColumns={@JoinColumn(name = "TEAM_ID")}
    )
	protected Map<String, Meta> metas = new HashMap<String, Meta>();
	
	protected Team(){
	}
	/**
     * 
     */
	public Team(String name) {
		this.name = name;
		this.type = Type.ANONYM;
	}

	public Team(String name, Type type) {
		this(name);
		this.type = type;
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
	 * @return the teamName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the team name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lastAliveDate
	 */
	public Date getLastAliveDate() {
		return lastAliveDate;
	}

	/**
	 * @param lastAliveDate
	 *            the lastAliveDate to set
	 */
	public void setLastAliveDate(Date lastAliveDate) {
		this.lastAliveDate = lastAliveDate;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
     * @return the userTeamLinks
     */
    protected Set<UserTeamLink> getUserTeamLinks() {
    	return userTeamLinks;
    }

	/**
     * @return the users
     */
    public Set<User> getUsers() {
    	Set<User> users = new HashSet<User>();
    	for(UserTeamLink link : userTeamLinks) {
    		users.add(link.getUser());
    	}
    	return Collections.unmodifiableSet(users);
    }
	
	public void addUser(User u){
    	new UserTeamLink(u, this).save();
	}
	
	public void removeUser(User u){
		new UserTeamLink(u, this).delete();
	}
	
	public void addCorporation(SimCorporation corp) {
		getCorporations().add(corp);
		corp.setTeam(this);
	}
	
	public void removeCorporation(SimCorporation corp) {
		getCorporations().remove(corp);
		corp.setTeam(null);
	}
	
	/**
     * @return the corporations
     */
    public Set<SimCorporation> getCorporations() {
    	return corporations;
    }
	public enum Type {
		ANONYM, SPECIAL
	}

	public enum Status {
		LOCK, UNLOCK
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Team) {
			return getName().equals(((Team)obj).getName());
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
	    return getName().hashCode();
	}

}
