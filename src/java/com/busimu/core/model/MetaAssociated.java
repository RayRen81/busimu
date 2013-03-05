/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

/**
 * @author Administrator
 * @version $Revision: $
 */
public interface MetaAssociated {
	
	String getMeta(String key);
	
	Meta setMeta(String key, String value);

}
