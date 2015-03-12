package com.sqm.dashboard.dao;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.JiraSchedulerVO;

public interface JiraSchedulerDAO {

	public void insertJiraData(JiraSchedulerVO sourceVO,DBCollection collection)throws Exception;
	
}
