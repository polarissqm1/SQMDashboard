package com.sqm.dashboard.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardUtility {

	
	public static Date getCurrentDate() throws Exception{
		
		Date date = new Date();
		DateFormat dateFormate=new SimpleDateFormat("dd/MMM/yy");
		Date systemDate = dateFormate.parse(dateFormate.format(date));

		return systemDate;
	}
}
