package com.sqm.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.VO.AlmReleaseVO;
import com.sqm.dashboard.dao.AlmSchedularDAO;
import com.sqm.dashboard.schedular.AlmSchedular;
import com.sqm.dashboard.schedular.AlmSchedularImpl;
import com.sqm.dashboard.schedular.JiraSchedulerImpl;
import com.sqm.dashboard.util.DashboardUtility;

public class AlmReleaseSchedularDAOImpl {

	final Logger log = Logger.getLogger(AlmSchedularDAOImpl.class);
	public void validatorInsertion(AlmVO almVO) throws Exception{
		
	}
	
	public void insertAlmReleasesToDb(AlmReleaseVO releaseVO)throws Exception{
		
		BasicDBObject almReleases=new BasicDBObject();
		 DBCollection table=DashboardDAOImpl.getDbCollection("release_vara");
		
		almReleases.put("domain", releaseVO.getDomain());
		almReleases.put("project", releaseVO.getProject());
		almReleases.put("id", releaseVO.getId());
		table.insert(almReleases);
		System.out.println(almReleases);
		System.out.println("inserted successfully");
		
	}
}
