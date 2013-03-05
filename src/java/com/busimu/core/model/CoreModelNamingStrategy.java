/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author Ray Ren
 * @version $Revision: $
 */
public class CoreModelNamingStrategy extends ImprovedNamingStrategy{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	public static final String PREFIX = "BSM_CORE_";
	
	@Override
	public String classToTableName(String className){
		return PREFIX+StringHelper.unqualify(className);
	}
	
	@Override
	public String propertyToColumnName(String propertyName) {
		return propertyName;
	}
	
	@Override
	public String tableName(String tableName){
		return PREFIX+tableName;
	}
	
	@Override
	public String columnName(String columnName){
		return columnName;
	}
	
	public String propertyToTableName(String className, String propertyName){
		return PREFIX+classToTableName(className)+'_'+propertyToColumnName(propertyName);
	}

}
