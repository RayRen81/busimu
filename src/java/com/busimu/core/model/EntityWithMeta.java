/*
 * $HeadURL:  $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.model;

import java.util.Map;

/**
 * @author wang
 * @version $Revision: $
 */
public class EntityWithMeta implements MetaAssociated{

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: $";
	
	protected Map<String, Meta> metas = null;
	
	/**
     * 
     */
    public EntityWithMeta(Map<String, Meta> metas) {
    	this.metas = metas;
    }
	
	public String getMeta(String key){
		if(metas.get(key) == null) {
			return null;
		} else {
			return metas.get(key).getValue();
		}
	}
	
	public Meta setMeta(String key, String value) {
		return metas.put(key, new Meta(value));
	}

}
