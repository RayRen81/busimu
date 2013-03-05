/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.unittest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.busimu.core.dao.UserMngDaoPolicy;
import com.busimu.core.dao.impl.UserMngDaoPolicyJpaImpl;
import com.busimu.core.model.License;
import com.busimu.core.unittest.BaseDBTest;

/**
 * @author elihuwu
 * @version $Revision: $
 */
public class TestUserMngDaoPolicy extends BaseDBTest {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private UserMngDaoPolicy policy = null;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Before
	public void beforeMethod() {
	    super.beforeMethod();
	    policy = new UserMngDaoPolicyJpaImpl();
	    ((UserMngDaoPolicyJpaImpl)policy).setEntityManagerFactory(emf);
	}
	
	@Test
	public void testIsExistedLicense(){
		License la = new License("AAA", License.Type.STUDENT);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(la);
		tx.commit();
		em.close();
		
		assertTrue(policy.isExistedLicense("AAA"));
		assertFalse(policy.isExistedLicense("BBB"));
	}

}
