/*
 * $HeadURL: svn://localhost/dev/busimu/trunk/src/java/com/busimu/core/util/CommonUtil.java $
 *
 * Copyright (c) 2010 Busimu Corporation, all rights reserved.
 *
 */
package com.busimu.core.util;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math.random.JDKRandomGenerator;
import org.apache.commons.math.random.RandomGenerator;

/**
 * @author elihuwu
 * @version $Revision: 45 $
 */
public class CommonUtil {

	/** Class revision */
	public static final String _REV_ID_ = "$Revision: 45 $";
	
	public static Set<Integer> getRandomNumbers(int countOfNumbers, int lessThan) {
		if(countOfNumbers > lessThan) {
			throw new IllegalArgumentException("The count(" + countOfNumbers + ") of returned number must be less or equal than the max value(" + lessThan + ")");
		}
		Set<Integer> result = new LinkedHashSet<Integer>();
		RandomGenerator generator = new JDKRandomGenerator();
		while(result.size() < countOfNumbers) {
			result.add(generator.nextInt(lessThan));
		}
		return result;
	}
	
	public static String covertToHtmlColor(Color color) {
		return color == null ? null : "#" + to2BitHexString(color.getRed()) + to2BitHexString(color.getGreen()) + to2BitHexString(color.getBlue());
	}
	
	private static String to2BitHexString(int i) {
		String ret = Integer.toHexString(i);
		if(ret.length() < 2) {
			ret = "0" + ret;
		}
		return ret;
	}
	
	public static String formatDate(Date date, String pattern) { 
		if(date == null) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
			return sdf.format(date);
		}
	}
	
	public static Date parseDate(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	    return sdf.parse(date);
	}

}
