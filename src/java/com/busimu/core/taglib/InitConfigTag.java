/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.busimu.core.util.CommonConfig;

/**
 * @author Administrator
 * @version $Revision: $
 */
public class InitConfigTag extends SimpleTagSupport{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTag() throws JspException, IOException {
		getJspContext().setAttribute("config", CommonConfig.getInstance());
	}

}
