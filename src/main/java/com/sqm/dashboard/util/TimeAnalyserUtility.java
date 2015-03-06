package com.sqm.dashboard.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimeAnalyserUtility {

	private static final Log log = LogFactory.getLog(TimeAnalyserUtility.class);
	
	public static double calculateTime(long startTime,String method)
	{
		long endTime=System.currentTimeMillis();
		double duration=(endTime-startTime)/1000;
		log.debug(method +"  Execution duration is: "+duration+"  seconds");
		return duration;
		
	}
	
}
