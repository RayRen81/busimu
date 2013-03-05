/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/test/java/com/busimu/core/unittest/util/TestCommonUtil.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.unittest.util;

import java.awt.Color;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.busimu.core.util.CommonUtil;

/**
 * @author elihuwu
 * @version $Revision: 43 $
 */
public class TestCommonUtil {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 43 $";
	
	@Test
	public void testGetRandomNumbers() {
		Set<Integer> results = CommonUtil.getRandomNumbers(12, 12);
		Assert.assertEquals(12, results.size());
		for(int num : results){
			Assert.assertTrue(num < 12);
		}
		System.out.println(results);
	}
	
	@Test
	public void testCovertToHtmlColor(){
		System.out.println(Color.ORANGE);
		System.out.println(Color.ORANGE.brighter().brighter());
	}

}
