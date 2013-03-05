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

import com.busimu.core.model.User;
import com.busimu.core.unittest.BaseDBTest;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
public class TestUser extends BaseDBTest{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	@Test
	public void testBasic(){
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User u1 = new User("manager@busimu.com", "123456", "Manager", User.Type.ADMIN);
		User u2 = new User("wjc@busimu.com", "123456", "Teacher", User.Type.TEACHER);
		u2.setStatus(User.Status.ACTIVE);
		System.out.println(u1.getId());
		em.persist(u1);
		System.out.println(u1.getId());
		u1.setPasswd("34567");
		em.persist(u1);
		System.out.println(u1.getId());
		em.persist(u2);
		tx.commit();
		em.close();
	}
	
	

}
