/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/action/LogoutAction.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;

/**
 * @author elihuwu
 * @version $Revision: 27 $
 */
public class LogoutAction implements Action{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 27 $";
	
	/**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws Exception {
    	ServletActionContext.getRequest().getSession().invalidate();
	    return Action.SUCCESS;
    }

}
