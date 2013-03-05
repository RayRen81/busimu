/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/filter/SetupModelBeanInSession.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.busimu.core.model.User;
import com.busimu.core.util.SessionScopeKey;

/**
 * @author elihuwu
 * @version $Revision: 39 $
 */
public class SetupModelBeanInSession extends OpenEntityManagerInViewFilter {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 39 $";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(SessionScopeKey.USER);
		if(user != null) {
			EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(lookupEntityManagerFactory());
			request.getSession().setAttribute(SessionScopeKey.USER, emHolder.getEntityManager().merge(user));
		}
		filterChain.doFilter(request, response);
	}

}
