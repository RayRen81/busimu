/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.unittest.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Test;

import com.busimu.core.model.Team;
import com.busimu.core.model.User;
import com.busimu.core.unittest.BaseDBTest;

/**
 * @author elihuwu
 * @version $Revision: $
 */
public class TestUserTeam extends BaseDBTest{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	private User u1, u2, u3;
	private Team t1, t2, t3;
	
	@Test
	public void testAddTeamInUser(){
		initNewEntities();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		u1.addTeam(t1);
		u1.addTeam(t2);
		
		em.persist(u1);
		tx.commit();
		em.close();
	}
	
	@Test
	public void testRemoveTeamInUser(){
		testAddTeamInUser();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select u from User u where u.id =" + u1.getId());
		u1 = (User)q.getResultList().get(0);
		for(Team t : u1.getTeams()){
			if(t.getId() == t1.getId()){
				t1 = t;
				continue;
			}
			if(t.getId() == t2.getId()){
				t2 = t;
				continue;
			}
		}
		
		u1.removeTeam(t1);
		System.out.println(u1.getTeams().size());
		User tempu1 = u1;
		initNewEntities();
		u1 = tempu1;
		u1.removeTeam(t1);
		u1.removeTeam(t2);
		u1.removeTeam(t3);
		em.persist(u1);
		tx.commit();
		em.close();
	}
	
	@Test 
	public void testDeleteTeamInUser(){
		testAddTeamInUser();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select u from User u where u.id =" + u1.getId());
		u1 = (User)q.getResultList().get(0);
		for(Team t : u1.getTeams()){
			if(t.getId() == t1.getId()){
				t1 = t;
				continue;
			}
			if(t.getId() == t2.getId()){
				t2 = t;
				continue;
			}
		}
		
		u1.removeTeam(t2);
		em.remove(t2);
		tx.commit();
		em.close();
	}
	
	@Test
	public void testAddUserInTeam(){
		initNewEntities();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		t1.addUser(u1);
		t1.addUser(u2);
		
		em.persist(u1);
		tx.commit();
		em.close();
	}
	
	@Test
	public void testRemoveUserInTeam(){
		testAddUserInTeam();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select t from Team t where t.id =" + t1.getId());
		t1 = (Team)q.getResultList().get(0);
		for(User u : t1.getUsers()){
			if(u.getId() == u1.getId()){
				u1 = u;
				continue;
			}
			if(u.getId() == u2.getId()){
				u2 = u;
				continue;
			}
		}
		
		t1.removeUser(u1);
		System.out.println(u1.getTeams().size());
		Team tempt1 = t1;
		User tempu1 = u1;
		initNewEntities();
		t1 = tempt1;
		t1.removeUser(u1);
		t1.removeUser(u2);
		t1.removeUser(u3);
		em.persist(tempu1);
		tx.commit();
		em.close();
	}
	
	@Test
	public void testMixedAdd(){
		/**
		 * t1-u1,u2 t2-u1,u3 t3-u1,u2
		 */
		initNewEntities();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		t1.addUser(u1);
		t1.addUser(u2);
		em.persist(u1);
		tx.commit();
		em.close();
		
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		Query q = em.createQuery("select t from Team t where t.id =" + t1.getId());
		t1 = (Team)q.getResultList().get(0);
		for(User u : t1.getUsers()){
			if(u.getId() == u1.getId()){
				u1 = u;
				continue;
			}
			if(u.getId() == u2.getId()){
				u2 = u;
				continue;
			}
		}
		u1.addTeam(t1);
		u1.addTeam(t3);
		u1.addTeam(t2);
		
		t3.addUser(u1);
		t3.addUser(u2);
		t2.addUser(u1);
		t2.addUser(u3);
		
		em.persist(u1);
		em.persist(t1);
		tx.commit();
		em.close();
		
	}
	
	private void initNewEntities(){
		u1 = new User("manager@busimu.com", "123456", "Manager", User.Type.ADMIN);
		u2 = new User("wjc@busimu.com", "123456", "Teacher", User.Type.TEACHER);
		u3 = new User("rlh@busimu.com", "123456", "Student", User.Type.STUDENT);
		
		
		t1 = new Team("The First");
		t2 = new Team("The Second");
		t3 = new Team("The Third");
	}
	

}
