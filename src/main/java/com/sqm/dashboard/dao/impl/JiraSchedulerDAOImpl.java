package com.sqm.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sqm.dashboard.VO.EffortJiraVO;
import com.sqm.dashboard.VO.JiraIdVO;
import com.sqm.dashboard.VO.JiraSchedulerVO;
import com.sqm.dashboard.dao.JiraSchedulerDAO;
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
		else if(sourceVO.getProject().equalsIgnoreCase("CFPR")){
			key="ReMit Mar 20th release";
			keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
		}
		else if(sourceVO.getProject().equalsIgnoreCase("PBCFT")){
			key="iManage Mar 20 Rel";
			keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
		}
		else if(sourceVO.getProject().equalsIgnoreCase("CFTPOSTTRADE")){
			key="OTCC - Mar 27 release";
			keyValue = key + "|" + DashboardUtility.getCurrentDate().toString();
		}
		else if(sourceVO.getProject().equalsIgnoreCase("CFPIWATCH")){
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
		}finally{
			//table.getDB().getMongo().close();
		}
	}
	
	@Override
	public void insertJiraData(JiraSchedulerVO sourceVO,DBCollection collection, String keyValue)throws Exception {
		
		try{
			TesterDBNew testDb=new TesterDBNew();
			Date systemDate = testDb.getCurrentDate();

			BasicDBObject jiracollection = new BasicDBObject();
			jiracollection.put(Constants.DB_PROJECT, sourceVO.getProject());
			jiracollection.put(Constants.DB_RELEASE, sourceVO.getRelease());
			jiracollection.put(Constants.KEY, keyValue);
			jiracollection.put(Constants.DB_CREATED_BY, "System");
			jiracollection.put(Constants.DB_CREATED_ON, systemDate);
			jiracollection.put(Constants.DB_UPDATED_BY, "System");
			jiracollection.put(Constants.DB_UPDATED_ON, systemDate);
			
			final List<DBObject> efforts = new ArrayList<DBObject>();
			DBObject effortsData = null;
			ListIterator<EffortJiraVO> effortsiterator = sourceVO.getEffort().listIterator();
			while(effortsiterator.hasNext()) {
				int j=0;
				effortsData = new BasicDBObject();
				EffortJiraVO result = effortsiterator.next();
				effortsData.put(Constants.DB_JIRA_SQM, result.getSqm() );
				effortsData.put(Constants.DB_JIRA_NONSQM, result.getNonsqm());
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
				JiraIdVO resultJiraId = jiraIterator.next();
				jiraIdsData.put(Constants.DB_JIRA_JIRAID, resultJiraId.getJiraid());
				jiraIdsData.put("env", resultJiraId.getEnv());
				jiraIds.add(jiraIdsData);
				j++;
			}
			
			jiracollection.put(Constants.DB_JIRA_JIRA, jiraIds);
	        jiracollection.put(Constants.DB_JIRA_EFFORT, efforts);
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
			jiracollection.append("$set", new BasicDBObject().append(Constants.DB_UPDATED_ON, systemDate));

			final List<DBObject> efforts = new ArrayList<DBObject>();
			BasicDBObject updateEfforts = new BasicDBObject();
			DBObject effortsData = null;
			ListIterator<EffortJiraVO> effortsiterator=sourceVO.getEffort().listIterator();
			while(effortsiterator.hasNext()) {
				int j=0;
				effortsData = new BasicDBObject();
				EffortJiraVO result = effortsiterator.next();
				effortsData.put(Constants.DB_JIRA_SQM, result.getSqm() );
				effortsData.put(Constants.DB_JIRA_NONSQM, result.getNonsqm());
				efforts.add(effortsData);
				j++;
			}
			updateEfforts.append("$set", new BasicDBObject().append(Constants.DB_JIRA_EFFORT, efforts));
		
			ListIterator<JiraIdVO> jiraIterator=sourceVO.getJiraids().listIterator();
			final List<DBObject> jiraIds = new ArrayList<DBObject>();
			BasicDBObject updateJiraIds= new BasicDBObject();
			DBObject jiraIdsData = null;
			System.out.println("###########################################Updating %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sourceVO.getJiraids().size());
			while(jiraIterator.hasNext()) {
				int j=0;
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Inside");
				jiraIdsData = new BasicDBObject();
				JiraIdVO resultJiraId = jiraIterator.next();
				jiraIdsData.put(Constants.DB_JIRA_JIRAID, resultJiraId.getJiraid());
				jiraIdsData.put(Constants.DB_JIRA_ENV, resultJiraId.getEnv());
				jiraIds.add(jiraIdsData);
				j++;
			}
			updateJiraIds.append("$set", new BasicDBObject().append(Constants.DB_JIRA_JIRA, jiraIds));
					
			BasicDBObject searchQuery = new BasicDBObject().append(Constants.KEY, keyValue);

			collection.update(searchQuery, jiracollection);
			collection.update(searchQuery, updateEfforts);
			collection.update(searchQuery, updateJiraIds);
		} catch(Exception e){
			log.error("Exception Jira DAO Insertion level");
			throw e;
		}
	}
}