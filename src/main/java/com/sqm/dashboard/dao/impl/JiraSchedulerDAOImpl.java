package com.sqm.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.VO.EffortJiraVO;
import com.sqm.dashboard.VO.JiraIdVO;
import com.sqm.dashboard.VO.JiraSchedulerVO;
import com.sqm.dashboard.dao.JiraSchedulerDAO;
import  com.sqm.dashboard.schedular.JiraSchedulerImpl;
import com.sqm.dashboard.util.Constants;
import com.sqm.dashboard.util.DashboardUtility;
import com.sqm.dashboard.util.TesterDBNew;

@Repository
public class JiraSchedulerDAOImpl implements JiraSchedulerDAO {

	final Logger log = Logger.getLogger(JiraSchedulerDAOImpl.class);
	
	
public void validatorInsertion(JiraSchedulerVO sourceVO, DBCollection table) throws Exception {
		
	DBCursor cursor = null;
	String key="";
	String keyValue="";
	
	if(sourceVO.getProject().equalsIgnoreCase("CFPCOB")){
		
		key="CFP Reporting: March Release";
		keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
		
	}
	else if(sourceVO.getProject().equalsIgnoreCase("CFPR"))
	{
		key="ReMit Mar 20th release";
		keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
	}
	else if(sourceVO.getProject().equalsIgnoreCase("PBCFT"))
	{
		key="iManage Mar 20 Rel";
		keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
	}
	else if(sourceVO.getProject().equalsIgnoreCase("CFTPOSTTRADE"))
	{
		key="OTCC - Mar 27 release";
		keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
	}
	else if(sourceVO.getProject().equalsIgnoreCase("CFPIWATCH"))
	{
		key="Plexus 5.2 upgrade April release";
		keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
	}
	
	System.out.println(keyValue);

	try{
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("project", sourceVO.getProject());
		cursor = table.find(searchQuery);
		
		
		boolean isKeyValueMatching = false;
		
		
		while (cursor.hasNext()) {
			DBObject report = cursor.next();
			if(report.get("key").toString().equalsIgnoreCase(keyValue)){
				isKeyValueMatching = true;
			}
		}
		if(isKeyValueMatching){
			 log.info("isKeyValueMatching : " + isKeyValueMatching);
			 System.out.println("###########################################Updating %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			 updateJiraData(sourceVO, table, keyValue);
			 log.info("Updated record to alm collection");
		 } else {
			 log.info("isKeyValueMatching : " + isKeyValueMatching);
			 insertJiraData(sourceVO, table, keyValue);
			 log.info("Inserted record to alm collection");
		 }
	}catch(Exception e) {
		log.info("Exception occured at Update/Insert to alm collection");
		throw e;
	}
		/*DBCursor cursor = null;
		
		String keyValue = sourceVO.getRelease() + "|" + DashboardUtility.getCurrentDate().toString();

		try{
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("project", sourceVO.getProject());
			cursor = table.find(searchQuery);
			
			boolean isKeyValueMatching = false;
			
			while (cursor.hasNext()) {
				DBObject report = cursor.next();
				if(report.get("key").toString().equalsIgnoreCase(keyValue)){
					isKeyValueMatching = true;
				}
			}
			if(isKeyValueMatching){
				 log.info("isKeyValueMatching : " + isKeyValueMatching);
				 System.out.println("###########################################Updating %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				 updateJiraData(sourceVO, table, keyValue);
				 log.info("Updated record to alm collection");
			 } else {
				 log.info("isKeyValueMatching : " + isKeyValueMatching);
				 insertJiraData(sourceVO, table, keyValue);
				 log.info("Inserted record to alm collection");
			 }
		} catch(Exception e) {
			log.info("Exception occured at Update/Insert to alm collection");
			throw e;
		}*/
	}
	
	
	@Override
	public void insertJiraData(JiraSchedulerVO sourceVO,DBCollection collection, String keyValue)throws Exception {
		
		try{
			
			
			TesterDBNew testDb=new TesterDBNew();
			Date systemDate=testDb.getCurrentDate();

			        BasicDBObject jiracollection = new BasicDBObject();
			        jiracollection.put(Constants.DB_JIRA_PROJECT, sourceVO.getProject());
			        jiracollection.put("release", sourceVO.getRelease());
			        jiracollection.put("key", keyValue);
			       /* jiracollection.put("project", "pppppppp");
			        jiracollection.put("release", "rrrrrrrrr");*/
			        jiracollection.put("docCreatedBy", "System");
			        jiracollection.put("docCreatedDate",systemDate );
			        	jiracollection.put("docUpdatedBy", "System");
			        jiracollection.put("docUpdatedDate",systemDate );
			        final List<DBObject> efforts = new ArrayList<DBObject>();
				    
						DBObject effortsData = null;
						ListIterator<EffortJiraVO> effortsiterator=sourceVO.getEffort().listIterator();
						while(effortsiterator.hasNext()) {
				        int j=0;
							effortsData = new BasicDBObject();
							EffortJiraVO result=effortsiterator.next();
							effortsData.put("sqm",result.getSqm() );
							effortsData.put("nonSqm", result.getNonsqm());
							efforts.add(effortsData);
							j++;
						}
			        
			        ListIterator<JiraIdVO> jiraIterator=sourceVO.getJiraids().listIterator();
					 final List<DBObject> jiraIds = new ArrayList<DBObject>();
				    
						DBObject jiraIdsData = null;
					 while(jiraIterator.hasNext()) {
							int j=0;
				        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Inside");
							jiraIdsData = new BasicDBObject();
							JiraIdVO resultJiraId=jiraIterator.next();
							jiraIdsData.put("jiraId", resultJiraId.getJiraid());
							jiraIdsData.put("env", resultJiraId.getEnv());
							jiraIds.add(jiraIdsData);
							j++;
						}
					jiracollection.put("jira", jiraIds);
			        
			     /*  List<JiraIdVO> jiras =sourceVO.getJiraids();
			       List<BasicDBObject> jiraDbObj = new ArrayList<BasicDBObject>();			       
			       for(JiraIdVO jiraVo:jiras){
			    	   jiraDbObj.add(new BasicDBObject("jiraId", jiraVo.getJiraid()));
			    	   jiraDbObj.add(new BasicDBObject("env", jiraVo.getEnv()));
			    	   
			       }	*/		        
			        jiracollection.put("effort", efforts);
			       // jiracollection.put("jira", jiraDbObj);			       			       
			        collection.insert(jiracollection);	
			        
			        System.out.println(" JIRA Inserted Successfully");
		}catch(Exception e){
			log.error("Exception Jira DAO Insertion level");
			throw e;
		}
		
		
	}
	
	public void updateJiraData(JiraSchedulerVO sourceVO,DBCollection collection, String keyValue) throws Exception{
	try{
		

		TesterDBNew testDb=new TesterDBNew();
		Date systemDate=testDb.getCurrentDate();

		        BasicDBObject jiracollection = new BasicDBObject();
		     jiracollection.append("$set", new BasicDBObject().append("docUpdatedDate", systemDate));
		     /*    jiracollection.append("$set", new BasicDBObject().append("release", sourceVO.getRelease()));*/
		     
		     final List<DBObject> efforts = new ArrayList<DBObject>();
		     BasicDBObject updateEfforts = new BasicDBObject();
				DBObject effortsData = null;
				ListIterator<EffortJiraVO> effortsiterator=sourceVO.getEffort().listIterator();
				while(effortsiterator.hasNext()) {
		        int j=0;
					effortsData = new BasicDBObject();
					EffortJiraVO result=effortsiterator.next();
					effortsData.put("sqm",result.getSqm() );
					effortsData.put("nonSqm", result.getNonsqm());
					efforts.add(effortsData);
					j++;
				}
				updateEfforts.append("$set", new BasicDBObject().append("effort", efforts));
				ListIterator<JiraIdVO> jiraIterator=sourceVO.getJiraids().listIterator();
				 final List<DBObject> jiraIds = new ArrayList<DBObject>();
			     BasicDBObject updateJiraIds= new BasicDBObject();
					DBObject jiraIdsData = null;
					System.out.println("###########################################Updating %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sourceVO.getJiraids().size());
					while(jiraIterator.hasNext()) {
						int j=0;
			        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Inside");
						jiraIdsData = new BasicDBObject();
						JiraIdVO resultJiraId=jiraIterator.next();
						jiraIdsData.put("jiraId", resultJiraId.getJiraid());
						jiraIdsData.put("env", resultJiraId.getEnv());
						jiraIds.add(jiraIdsData);
						j++;
					}
					updateJiraIds.append("$set", new BasicDBObject().append("jira", jiraIds));
					
					BasicDBObject searchQuery = new BasicDBObject().append("key", keyValue);
					 
					collection.update(searchQuery, jiracollection);
					collection.update(searchQuery, updateEfforts);
					collection.update(searchQuery, updateJiraIds);
		
		
	}catch(Exception e){
		log.error("Exception Jira DAO Insertion level");
		throw e;
	}
	}

}
