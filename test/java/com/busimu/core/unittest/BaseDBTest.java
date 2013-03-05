/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.unittest;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
public class BaseDBTest {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	protected static Map<String, String> properties = new HashMap<String, String>();
	
	protected EntityManagerFactory emf = null;
	
	@BeforeClass
	public static void beforeClass(){
		properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/testdb");
		properties.put("hibernate.connection.username", "testdb");
		properties.put("hibernate.connection.password", "testdb");
		properties.put("hibernate.c3p0.min_size", "5");
		properties.put("hibernate.c3p0.max_size", "20");
		properties.put("hibernate.c3p0.timeout", "300");
		properties.put("hibernate.c3p0.max_statements" ,"50");
		properties.put("hibernate.c3p0.idle_test_period", "3000");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("use_sql_comments", "true");
	}
	
	@Before
	public void beforeMethod(){
		// Start EntityManagerFactory
        emf = Persistence.createEntityManagerFactory("busimu", properties);
	}
	
	@After
	public void afterMethod(){
		if(emf != null && emf.isOpen()) {
			emf.close();
		}
	}

}
