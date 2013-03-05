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
import java.util.LinkedHashSet;
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

import org.springframework.core.style.ToStringCreator;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
@Entity
@Table(name = "USER")
public class User implements MetaAssociated, Comparable<User>{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long               id;

	@Column(name = "EMAIL", nullable = false, unique=true)
	private String             email;

	@Column(name = "PASSWORD", nullable = false)
	private String             passwd;

	@Column(name = "NAME")
	private String             name;

	@Column(name = "REGDATE", nullable = false, updatable = false)
	private Date               regDate = new Date();

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private Type           type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private Status             status   = Status.INACTIVE;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserTeamLink> userTeamLinks = new HashSet<UserTeamLink>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserCampaignLink> userCampaignLinks = new HashSet<UserCampaignLink>();
	
	@OneToMany(mappedBy = "holder", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Campaign> campaigns = new LinkedHashSet<Campaign>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LoginHistory> loginHistory = new LinkedHashSet<LoginHistory>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<DecisionData> decisionDatas = new LinkedHashSet<DecisionData>();
	
	@ElementCollection
	@MapKeyColumn(name="NAME", updatable = false)
    @CollectionTable(
    	name="USER_META", 
    	joinColumns={@JoinColumn(name = "USER_ID")}
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
     * @return the decisionDatas
     */
    protected Set<DecisionData> getDecisionDatas() {
    	return decisionDatas;
    }

	/**
     * @return the campaigns
     */
    public Set<Campaign> getCreatedCampaigns() {
    	return Collections.unmodifiableSet(getCampaigns());
    }
    
    

	/**
     * @return the userTeamLinks
     */
    protected Set<UserTeamLink> getUserTeamLinks() {
    	return userTeamLinks;
    }
    
	/**
     * @return the userCampaignLinks
     */
    protected Set<UserCampaignLink> getUserCampaignLinks() {
    	return userCampaignLinks;
    }

    
    protected User(){
    }
    
    /**
     * @return the campaigns
     */
    protected Set<Campaign> getCampaigns() {
	    return campaigns;
    }

	/**
     * @return the teams
     */
    public Set<Team> getTeams() {
    	Set<Team> teams = new HashSet<Team>();
    	for(UserTeamLink link : getUserTeamLinks()) {
    		teams.add(link.getTeam());
    	}
    	return Collections.unmodifiableSet(teams);
    }
    
    public void addTeam(Team t){
    	new UserTeamLink(this, t).save();
	}
    
	public void removeTeam(Team t){
		new UserTeamLink(this, t).delete();
	}
	
	public Set<Campaign> getJoinedCampaigns() {
		Set<Campaign> campaigns = new HashSet<Campaign>();
    	for(UserCampaignLink link : getUserCampaignLinks()) {
    		campaigns.add(link.getCampaign());
    	}
    	return Collections.unmodifiableSet(campaigns);
	}
	
	public void addCampaign(Campaign c) {
    	new UserCampaignLink(this, c).save();
    }
	
	public void removeCampaign(Campaign c) {
		new UserCampaignLink(this, c).delete();
	}
	
	public Campaign createCampagin(String title, Campaign.Type type){
		Campaign campaign = new Campaign(title, Campaign.Type.COURSE, this);
		getCampaigns().add(campaign);
		return campaign;
	}
	
	public void deleteCampaign(Campaign campaign) {
		getCampaigns().remove(campaign);
	}
	
	public Set<LoginHistory> getLoginHistory() {
    	return loginHistory;
    }
	
	public LoginHistory addLoginHistory(Date loginDate, Date logoutDate) {
		LoginHistory history = new LoginHistory(this, loginDate, logoutDate);
		getLoginHistory().add(history);
		return history;
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

	public User(String email, String passwd, String name, Type type) {
		this.email = email;
		this.passwd = passwd;
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @return the regDate
	 */
	public Date getRegDate() {
		return regDate;
	}
	
	public enum Type {
		UNKNOWN, ADMIN, TEACHER, ORGANIZER, STUDENT;
	}

	public enum Status {
		ACTIVE, INACTIVE
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			return getEmail().equals(((User)obj).getEmail());
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return getEmail().hashCode();
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(User u) {
    	return getName().compareTo(u.getName());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringCreator(this).toString();
    }
}
