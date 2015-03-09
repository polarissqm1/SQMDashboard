package com.sqm.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.EffortJiraVO;
import com.sqm.dashboard.VO.JiraIdVO;
import com.sqm.dashboard.VO.JiraSchedulerVO;
import com.sqm.dashboard.dao.JiraSchedulerDAO;
import  com.sqm.dashboard.schedular.JiraSchedulerImpl;
import com.sqm.dashboard.util.TesterDBNew;

@Repository
public class JiraSchedulerDAOImpl implements JiraSchedulerDAO {

	final Logger log = Logger.getLogger(JiraSchedulerDAOImpl.class);
	@Override
	public void insertJiraData(JiraSchedulerVO sourceVO)throws Exception {
		DBCollection collection=DashboardDAOImpl.getDbCollection("jira");
		try{
			
			
			TesterDBNew testDb=new TesterDBNew();
			Date systemDate=testDb.getCurrentDate();

			        BasicDBObject jiracollection = new BasicDBObject();
			        jiracollection.put("project", sourceVO.getProject());
			        jiracollection.put("release", sourceVO.getRelease());
			       /* jiracollection.put("project", "pppppppp");
			        jiracollection.put("release", "rrrrrrrrr");*/
			        jiracollection.put("docCreatedBy", "System");
			        jiracollection.put("docCreatedDate",systemDate );
			        	jiracollection.put("docUpdatedBy", "System");
			        jiracollection.put("docCreatedDate",systemDate );
			        List<EffortJiraVO> effortList =sourceVO.getEffort();			      
			        List<BasicDBObject> effort = new ArrayList<BasicDBObject>();			        
			        for(EffortJiraVO effortVo:effortList){
			        	effort.add(new BasicDBObject("sqm", effortVo.getSqm()));
			        	effort.add(new BasicDBObject("non-sqm", effortVo.getNonsqm()));			     	   
			        }			    
			       List<JiraIdVO> jiras =sourceVO.getJiraids();
			       List<BasicDBObject> jiraDbObj = new ArrayList<BasicDBObject>();			       
			       for(JiraIdVO jiraVo:jiras){
			    	   jiraDbObj.add(new BasicDBObject("jiraId", jiraVo.getJiraid()));
			    	   jiraDbObj.add(new BasicDBObject("env", jiraVo.getEnv()));
			    	   
			       }			        
			        jiracollection.put("effort", effort);
			        jiracollection.put("jira", jiraDbObj);			       			       
			        collection.insert(jiracollection);	
			        
			        System.out.println(" JIRA Inserted Successfully");
		}catch(Exception e){
			log.error("Exception Jira DAO Insertion level");
			throw e;
		}
		
		
	}

}
