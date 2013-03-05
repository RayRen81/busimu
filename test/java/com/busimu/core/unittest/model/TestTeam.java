/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.unittest.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import com.busimu.core.model.Team;
import com.busimu.core.unittest.BaseDBTest;

/**
 * @author elihuwu
 * @version $Revision: $
 */
public class TestTeam extends BaseDBTest{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Test
	public void testBasic(){
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Team t1 = new Team("The First");
		em.persist(t1);
		tx.commit();
		em.close();
	}

}
