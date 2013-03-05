/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.dao.impl;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.busimu.core.dao.UserMngDaoPolicy;
import com.busimu.core.exception.DataValidationErrorCode;
import com.busimu.core.exception.DataValidationException;
import com.busimu.core.model.Campaign;
import com.busimu.core.model.License;
import com.busimu.core.model.LoginHistory;
import com.busimu.core.model.Round;
import com.busimu.core.model.SimCorporation;
import com.busimu.core.model.SimMarket;
import com.busimu.core.model.Team;
import com.busimu.core.model.User;
import com.busimu.core.util.MetaKey;
import com.busimu.core.util.SecurityUtil;

/**
 * @author Ray
 * @version $Revision: $
 */
// TODO using spring dao?
public class UserMngDaoPolicyJpaImpl implements UserMngDaoPolicy {

	/** Class revision */
	public static final String   _REV_ID_ = "$Revision: $";

	private EntityManagerFactory emf;

	public void init() {
		// TODO:do NOT invoke in production!!!
		try {
	        initExampleData();
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

	private void initExampleData() throws Exception{
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			User player1 = new User("student@busimu.com", "ssssss", "s", User.Type.STUDENT);
			player1.setStatus(User.Status.ACTIVE);
			User player2 = new User("zhangsan@busimu.com", "123456", "张三",  User.Type.STUDENT);
			player2.setStatus(User.Status.ACTIVE);
			User player3 = new User("lisi@busimu.com", "123456", "李四", User.Type.STUDENT);
			player3.setStatus(User.Status.ACTIVE);
			User teacher = new User("teacher@busimu.com", "tttttt", "t", User.Type.TEACHER);
			teacher.setStatus(User.Status.ACTIVE);
			em.persist(player1);
			em.persist(player2);
			em.persist(player3);
			em.persist(teacher);
			
			Campaign campaign = teacher.createCampagin("课程", Campaign.Type.COURSE);
			SimMarket south = campaign.addMarket("华南");
			SimMarket central = campaign.addMarket("华中");
			SimMarket north = campaign.addMarket("华北");
			SimMarket east = campaign.addMarket("华东");
			SimMarket west = campaign.addMarket("中西部");
			SimCorporation ibm = south.addCorporation("IBM", new Color(128, 128, 255));
			SimCorporation ericsson = south.addCorporation("Ericsson", Color.BLUE);
			SimCorporation oracle = south.addCorporation("Oracle", Color.RED);
			for(int i = 0; i < 8; i++) {
				campaign.addRound();
			}
			
			player2.addCampaign(campaign);
			player3.addCampaign(campaign);
			em.persist(campaign);
			
			Team t1 = new Team("anoym1");
			Team t2 = new Team("anoym2");
			Team t3 = new Team("anoym3");
			t1.addCorporation(ibm);
			t2.addCorporation(ericsson);
			t3.addCorporation(oracle);
			em.persist(t1);
			em.persist(t2);
			em.persist(t3);
			
			Campaign runningCampaign = teacher.createCampagin("进行中课程", Campaign.Type.COURSE);
			SimMarket m1 = runningCampaign.addMarket("亚洲区");
			SimMarket m2 = runningCampaign.addMarket("欧洲区");
			SimMarket m3 = runningCampaign.addMarket("北美区");
			SimCorporation corp1 = m1.addCorporation("爱立信", Color.ORANGE);
			for(int i = 0; i < 3; i++) {
				runningCampaign.addRound();
			}
			player1.addCampaign(runningCampaign);
			player2.addCampaign(runningCampaign);
			player3.addCampaign(runningCampaign);
			runningCampaign.setStatus(Campaign.Status.ONGOING);
			
			Team t11 = new Team("anoym11");
			t11.addCorporation(corp1);
			t11.addUser(player1);
			t11.addUser(player2);
			t11.addUser(player3);
			em.persist(t11);
			
			tx.commit();
		} finally {
			em.close();
		}
	}
	

	/**
	 * @param emf
	 *            the emf to set
	 */
	@Required
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isExistedLicense(String licenseCode) {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
		return getLicenseByCode(em, licenseCode) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeLicenses(Set<License> licenses) {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
		        .getEntityManager();
		EntityTransaction tx = em.getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}
		for (License l : licenses) {
			em.persist(l);
		}
		tx.commit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveUser(User user) {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
		        .getEntityManager();
		EntityTransaction tx = em.getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}
		em.persist(user);
		tx.commit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User activateUser(User user, String licenseCode) {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
		        .getEntityManager();
		EntityTransaction tx = em.getTransaction();
		License l = getLicenseByCode(em, licenseCode);
		if (l != null) {
			if (!tx.isActive()) {
				tx.begin();
			}
			user.setType(User.Type.valueOf(l.getType().name()));
			user.setStatus(User.Status.ACTIVE);
			user = em.merge(user);
			em.remove(l);
			tx.commit();
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User authenticateUser(String userName, String passwd) throws DataValidationException {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
		        .getEntityManager();
		User user = getUserByName(em, userName);
		if (user == null) {
			throw new DataValidationException("Authentication Fail - NO user called " + userName,
			        DataValidationErrorCode.NONE_EXISTED_USER);
		} else if (!user.getPasswd().equals(SecurityUtil.getMD5ByString(passwd))) {
			throw new DataValidationException("Authentication Fail - Password is NOT right for " + userName,
			        DataValidationErrorCode.WRONG_PASSWORD);
		} else {
			return user;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LoginHistory addLoginHistory(User user, Date loginDate, Date logoutDate) {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
		EntityTransaction tx = em.getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}
		LoginHistory history = user.addLoginHistory(loginDate, logoutDate);
		tx.commit();
		return history;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUserById(String userId) {
		try {
			EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
			        .getEntityManager();
			Long id = Long.parseLong(userId);
			User user = em.find(User.class, id);
			return user;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LoginHistory> getRealLoginHistory(User user, Campaign campaign) {
		EntityManager em = ((EntityManagerHolder) TransactionSynchronizationManager.getResource(emf))
        .getEntityManager();
		Date startDate = new Date(Long.parseLong(campaign.getMeta(MetaKey.CAMPAIGN_START_TIME)));
		List<Round> rounds = campaign.getSortedRoundsAsList();
		Date endDate = rounds.get(rounds.size()-1).getEndDate();
		TypedQuery<LoginHistory> q = em.createQuery("select lh from LoginHistory lh where lh.user = :user and lh.loginDate between :start and :end", LoginHistory.class);
		q.setParameter("user", user);
		q.setParameter("start", startDate, TemporalType.TIMESTAMP);
		q.setParameter("end", endDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}

	/**
	 * Template, reduce this code using spring dao support?
	 * 
	 * EntityManager em = emf.createEntityManager(); try{ EntityTransaction tx = em.getTransaction(); tx.begin();
	 * tx.commit(); }finally{ em.close(); }
	 */

	private User getUserByName(EntityManager em, String userName) {
		Query q = em.createQuery("select u from User u where u.name = '" + userName + "'");
		List<?> resultList = q.getResultList();
		return resultList.size() == 0 ? null : (User) resultList.get(0);
	}

	private License getLicenseByCode(EntityManager em, String licenseCode) {
		Query q = em.createQuery("select l from License l where l.licenseCode = '" + licenseCode + "'");
		List<?> resultList = q.getResultList();
		return resultList.size() == 0 ? null : (License) resultList.get(0);
	}

}
