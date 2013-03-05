/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/dao/impl/CampaignMngDaoPolicyJpaImpl.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.dao.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.math.random.JDKRandomGenerator;
import org.apache.commons.math.random.RandomGenerator;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.busimu.core.dao.CampaignMngDaoPolicy;
import com.busimu.core.exception.DataValidationErrorCode;
import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.Campaign;
import com.busimu.core.model.Round;
import com.busimu.core.model.SimCorporation;
import com.busimu.core.model.SimMarket;
import com.busimu.core.model.Team;
import com.busimu.core.model.User;
import com.busimu.core.util.CommonUtil;
import com.busimu.core.util.MetaKey;

/**
 * @author elihuwu
 * @version $Revision: 63 $
 */
public class CampaignMngDaoPolicyJpaImpl implements CampaignMngDaoPolicy{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 63 $";
	
	private EntityManagerFactory emf;
	
	
	
	/**
     * @param emf the emf to set
     */
	@Required
    public void setEntityManagerFactory(EntityManagerFactory emf) {
    	this.emf = emf;
    }
	
	public Campaign createNewCampaign(User user, String title, int numOfMarket, int numOfCorpPerMarket, int numOfRound)
	        throws DataValidationException {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
		        .getEntityManager();
		EntityTransaction tx = em.getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}
		Campaign c = user.createCampagin(title, Campaign.Type.COURSE);
		for (int i = 0; i < numOfMarket; i++) {
			SimMarket m = c.addMarket("market" + (i + 1));
			addCorpsInMarket(m, numOfCorpPerMarket);
		}
		for (int k = 1; k <= numOfRound; k++) {
			c.addRound();
		}
		em.persist(c);
		for(SimMarket m : c.getMarkets()) {
			int j = 0;
			for(SimCorporation corp : m.getCorporations()) {
				Team t = addTeamForCorp(corp, "team"+c.getCampaignId()+m.getId()+ ++j);
				em.persist(t);
			}
		}
		tx.commit();
		return c;
	}

	/**
     * {@inheritDoc}
	 * @throws DataValidationException 
     */
	@Override
	public User registerCampaign(User user, String campaignId) throws DataValidationException {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
		        .getEntityManager();
		EntityTransaction tx = em.getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}
		user = em.merge(user);
		Campaign c = getCampaignById(em, campaignId);
		if (c != null && c.getStatus() == Campaign.Status.OPEN) {
			c.addUser(user);
			em.persist(c);
		} else {
			throw new DataValidationException("invalid course id", DataValidationErrorCode.COURSE_ID_INVALID);
		}
		tx.commit();
		return user;
	}
	
    private Campaign getCampaignById(EntityManager em, String campaignId) {
    	Query q = em.createQuery("select c from Campaign c where c.id = '" + campaignId +"'");
    	List<?> resultList = q.getResultList();
    	return resultList.size() == 0 ? null : (Campaign)resultList.get(0);
    }
    
    private SimCorporation getCorporationById(EntityManager em, String corpId) {
    	Query q = em.createQuery("select c from SimCorporation c where c.id = '" + corpId +"'");
    	List<?> resultList = q.getResultList();
    	return resultList.size() == 0 ? null : (SimCorporation)resultList.get(0);
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public Campaign getCampaignById(String campaignId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	return getCampaignById(em, campaignId);
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public SimMarket getMarketById(String marketId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	Query q = em.createQuery("select m from SimMarket m where m.id = '" + marketId +"'");
    	List<?> resultList = q.getResultList();
    	return resultList.size() == 0 ? null : (SimMarket)resultList.get(0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Round getRoundById(String roundId) {
        try {
            EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
                    .getEntityManager();
            Long id = Long.parseLong(roundId);
            Round round = em.find(Round.class, id);
            return round;
        } catch (NumberFormatException e) {
            return null;
        }
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void assignStudentInCorp(List<String> studentIds, String corpId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	Query q1 = em.createQuery("select u from User u where u.id in " + studentIds.toString().replace('[', '(').replace(']', ')') + " and u.type = 'STUDENT'");
    	List<?> students = q1.getResultList();
    	Query q2 = em.createQuery("select c from SimCorporation c where c.id = '" + corpId + "'");
    	List<?> corporations = q2.getResultList();
    	if(students.size() != 0 && corporations.size() != 0) {
    		SimCorporation c = (SimCorporation) corporations.get(0);
    		Team t = c.getTeam();
			if (t != null) {
				for (Object student : students) {
					if(c.getMarket().getCampaign().getUsers().contains(student)) {
						((User)student).addTeam(t);
					}
				}
				em.persist(t);
			}
    	}
    	tx.commit();
    }
    
    public void removeStudentFromTeam(String studentId, String teamId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	Query q1 = em.createQuery("select u from User u where u.id = '" + studentId + "' and u.type = 'STUDENT'" );
    	List<?> students = q1.getResultList();
    	User student = students.size() == 0 ? null : (User)students.get(0);
    	
    	Query q2 = em.createQuery("select t from Team t where t.id = '" + teamId + "'" );
    	List<?> teams = q2.getResultList();
    	Team team = teams.size() == 0 ? null : (Team)teams.get(0);
    	
    	if(student != null && team != null) {
    		student.removeTeam(team);
    	}
    	tx.commit();
    }
    
    public void removeStudentFromCampaign(List<String> studentIds, String campaignId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	Query q1 = em.createQuery("select u from User u where u.id in " + studentIds.toString().replace('[', '(').replace(']', ')') + " and u.type = 'STUDENT'");
    	List<?> students = q1.getResultList();
    	
    	Campaign campaign = getCampaignById(campaignId);
    	
    	if(students.size() != 0 && campaign != null) {
    		for(Object student : students)
    			((User)student).removeCampaign(campaign);
    	}
    	
    	tx.commit();
    }
    
    public void saveScheduleEndDate(Campaign campaign, Date startDate, Date[] endDates){
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	if(campaign.getRounds().size() != endDates.length) {
    		throw new IllegalArgumentException("The number of rounds doesn't equal to number of date params: " + campaign.getRounds().size() + " != " + endDates.length);
    	}
    	
    	if(startDate != null && startDate.compareTo(new Date()) <= 0) {
    		throw new IllegalArgumentException("The startDate " + startDate + "should be after current time.");
    	}
    	
    	if(startDate != null && (startDate.after(endDates[0]) || startDate.equals(endDates[0]))) {
    		throw new IllegalArgumentException("The start date should be before any other date: " + startDate + "-" + endDates[0]);
    	}
    	
    	for(int i = 0; i < endDates.length-1; i++) {
    		if(endDates[i].after(endDates[i+1]) || endDates[i].equals(endDates[i+1])) {
    			throw new IllegalArgumentException("The dates params does not sorted by asc: " + Arrays.toString(endDates));
    		}
    	}
    	
    	if(startDate != null) {
    		campaign.setMeta(MetaKey.CAMPAIGN_START_TIME, String.valueOf(startDate.getTime()));
    	}
    	
    	int i = 0;
    	for(Round r : campaign.getSortedRoundsAsList()) {
    		r.setEndDate(endDates[i++]);
    		if(i == 1 && startDate != null) {
    			r.setLastTime(endDates[i-1].getTime() - startDate.getTime());
    		}
    		if(i > 1){
    			r.setLastTime(endDates[i-1].getTime()-endDates[i-2].getTime());
    		}
    	}
    	tx.commit();
    }
    
    public void saveScheduleLastTime(Campaign campaign, Date startDate, Long[] times) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	if(startDate != null && startDate.compareTo(new Date()) <= 0) {
    		throw new IllegalArgumentException("The startDate " + startDate + "should be after current time.");
    	}
    	
    	if(campaign.getRounds().size() != times.length) {
    		throw new IllegalArgumentException("The number of rounds doesn't equal to number of date params: " + campaign.getRounds().size() + " != " + times.length);
    	}
    	
    	if(startDate != null) {
    		campaign.setMeta(MetaKey.CAMPAIGN_START_TIME, String.valueOf(startDate.getTime()));
    	}
    	
    	int i = 0;
    	Date lastEndDate = startDate;
    	for(Round r : campaign.getSortedRoundsAsList()) {
    		r.setLastTime(times[i++]);
    		if(lastEndDate != null) {
    			r.setEndDate(new Date(lastEndDate.getTime() + r.getLastTime()));
    		}
    		lastEndDate = r.getEndDate();
    	}
    	
    	tx.commit();
    }
    
    public void editCampaign(String campaignName, String campaignId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	Campaign campaign = getCampaignById(campaignId);
    	if(campaign != null){
    		campaign.setName(campaignName);
    	} else {
    		//print error log
    	}
    	
    	tx.commit();
    }
    
    public SimMarket addMarketInCampaign(String campaignId, String marketName, int numOfCorp) throws DataValidationException {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	Campaign campaign = getCampaignById(campaignId);
    	SimMarket market = campaign.addMarket(marketName);
    	addCorpsInMarket(market, numOfCorp);
    	em.persist(market);
    	int j = 0;
		for(SimCorporation corp : market.getCorporations()) {
			Team t = addTeamForCorp(corp, "team"+campaign.getCampaignId()+market.getId()+ ++j);
			em.persist(t);
		}
		tx.commit();
    	return market;
		
    }
    
    private void addCorpsInMarket(SimMarket m, int numOfCorp) throws DataValidationException {
    	int j = 0;
		Set<Integer> colorIndexs = CommonUtil.getRandomNumbers(numOfCorp, SimCorporation.PREDEFINED_COLORS.length);
		for (int colorIndex : colorIndexs) {
			m.addCorporation("corporation" + ++j, SimCorporation.PREDEFINED_COLORS[colorIndex]);
		}
    }
    
    private Team addTeamForCorp(SimCorporation corp, String teamName){
    	Team t = new Team(teamName);
		t.addCorporation(corp);
		return t;
    }
    
    public void editMarket(String marketId, String marketName) throws DataValidationException {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	SimMarket market = getMarketById(marketId);
    	if(market !=  null) {
    		market.setName(marketName);
    	}else {
    		//error log
    	}
    	tx.commit();
    	
    }
    
    public SimCorporation addCorpInMarket(String marketId, String corpName) throws DataValidationException {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	SimMarket market = getMarketById(marketId);
    	SimCorporation result = null;
    	if(market !=  null) {
    		Color color = null;
    		RandomGenerator generator = new JDKRandomGenerator();
    		while(true) {
    			boolean shouldContinue = false;;
    			color = SimCorporation.PREDEFINED_COLORS[generator.nextInt(SimCorporation.PREDEFINED_COLORS.length)];
    			for(SimCorporation corp : market.getCorporations()) {
    				if(corp.getHtmlColor().equals(CommonUtil.covertToHtmlColor(color))) {
    					shouldContinue = true;
    					break;
    				}
    			}
    			if(!shouldContinue) {
    				break;
    			}
    		}
    		result = market.addCorporation(corpName, color);
    		Team t = addTeamForCorp(result, "team"+market.getCampaign().getCampaignId()+market.getId()+ (market.getCorporations().size()+1));
    		em.persist(t);
    		
    	}else {
    		//error log
    	}
    	tx.commit();
    	return result;
    }
    
    public void editCorpName(String corpId, String corpName) throws DataValidationException {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	SimCorporation corp = getCorporationById(em, corpId);
    	if(corp != null) {
    		corp.setName(corpName);
    	} else {
    		//error log
    	}
    	tx.commit();
    }
    
    public void deleteCorp(String corpId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	SimCorporation corp = getCorporationById(em, corpId);
    	deleteCorp(em, corp);
    	tx.commit();
    }
    
    private void deleteCorp(EntityManager em, SimCorporation corp) {
    	
    	if(corp != null) {
    		Team team = corp.getTeam();
    		team.removeCorporation(corp);
    		corp.getMarket().removeCorporation(corp);
    		for(User user : team.getUsers()) {
    			user.removeTeam(team);
    		}
    		em.remove(corp);
    		em.remove(team);
    	} else {
    		//error log
    	}
    }
    
    public void deleteMarket(String marketId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	SimMarket market = getMarketById(marketId);
    	deleteMarket(em, market);
    	tx.commit();
    }
    
    private void deleteMarket(EntityManager em, SimMarket market) {
    	if(market != null) {
    		market.getCampaign().removeMarket(market);
    		Set<SimCorporation> corps = new HashSet<SimCorporation>(market.getCorporations());
    		for(SimCorporation corp : corps) {
    			deleteCorp(em, corp);
    		}
    		em.remove(market);
    	} else {
    		//error log
    	}
    }
    
    public void deleteCampaign(String campaignId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	Campaign campaign = getCampaignById(campaignId);
    	for(User user : campaign.getUsers()) {
    		user.removeCampaign(campaign);
    	}
    	campaign.getHolder().deleteCampaign(campaign);
    	Set<SimMarket> markets = new HashSet<SimMarket>(campaign.getMarkets());
    	for(SimMarket market : markets) {
    		deleteMarket(em, market);
    	}
    	em.remove(campaign);
    	tx.commit();
    	
    }
    
    public Round addRound(String campaignId) throws DataValidationException {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	Campaign campaign = getCampaignById(campaignId);
    	Round round = null;
    	if(campaign != null ) {
    		round = campaign.addRound();
    	} else {
    		// erro log
    	}
    	tx.commit();
    	return round;
    }
    
    public void reduceRound(String campaignId) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	Campaign campaign = getCampaignById(campaignId);
    	if(campaign != null ) {
    		List<Round> rounds = campaign.getSortedRoundsAsList();
        	if(rounds != null && rounds.size() > 0 ){
        		campaign.getRounds().remove(rounds.get(rounds.size()-1));
        		em.remove(rounds.get(rounds.size()-1));
        	}
    	} else {
    		// erro log
    	}
    	tx.commit();
    }
    
    public void setStatus(Campaign campaign, Campaign.Status status) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	campaign.setStatus(status);
    	tx.commit();
    }
    
    public void updateCampaignSchedule(Campaign campaign) {
    	EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	if (!tx.isActive()) {
			tx.begin();
		}
    	
    	long currentTime = System.currentTimeMillis();
    	campaign.setMeta(MetaKey.CAMPAIGN_START_TIME, String.valueOf(System.currentTimeMillis()));
    	long startTime = currentTime;
    	for(Round round : campaign.getRounds()) {
    		startTime += round.getLastTime();
    		round.setEndDate(new Date(startTime));
    	}
    	
    	tx.commit();
    }
    
//	Query q = em.createQuery("select c from SimMarket m where m.id = '" + marketId +"' and m.campaign.id = '" + campaignId +"'");
//	List<?> resultList = q.getResultList();
    
    

}
