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
import com.sqm.dashboard.dao.AlmSchedularDAO;
import com.sqm.dashboard.util.DashboardUtility;

public class AlmSchedularDAOImpl implements AlmSchedularDAO {
	
	final static Logger log = Logger.getLogger(AlmSchedularDAOImpl.class);
	
	public void validatorInsertion(AlmVO almVO, DBCollection table) throws Exception {
		
		DBCursor cursor = null;
		String keyValue = almVO.getRelease() + "|" + DashboardUtility.getCurrentDate().toString();

		try{
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("domain", almVO.getDomain());
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
				 updateAlmToDb(almVO, table, keyValue);
				 log.info("Updated record to alm collection");
			 } else {
				 log.info("isKeyValueMatching : " + isKeyValueMatching);
				 insertAlmToDb(almVO, table, keyValue);
				 log.info("Inserted record to alm collection");
			 }
		} catch(Exception e) {
			log.info("Exception occured at Update/Insert to alm collection");
			throw e;
		}
	}

	public  void insertAlmToDb(AlmVO almVO, DBCollection table, String keyValue) throws Exception {
		
		BasicDBObject alm = new BasicDBObject();
		
		alm.put("userid", "admin");
		alm.put("domain", almVO.getDomain());
		alm.put("projects", almVO.getProject());
		alm.put("release", almVO.getRelease());
		alm.put("release_EDate", "2015-03-02T07:47:37.676Z");
		alm.put("release_SDate", "2015-03-02T07:47:37.676Z");
		
		DBObject manual_TCExecutionStatus = new BasicDBObject();
		manual_TCExecutionStatus.put("passed", almVO.getAlmTCVO().getSchedManualVO().getPassed());
		manual_TCExecutionStatus.put("failed", almVO.getAlmTCVO().getSchedManualVO().getFailed());
		manual_TCExecutionStatus.put("noRun", almVO.getAlmTCVO().getSchedManualVO().getNoRun());
		manual_TCExecutionStatus.put("blocked", almVO.getAlmTCVO().getSchedManualVO().getBlocked());
		manual_TCExecutionStatus.put("defered", almVO.getAlmTCVO().getSchedManualVO().getDeferred());
		alm.put("manual_TCExecutionStatus", manual_TCExecutionStatus);
		
		DBObject automation_TCExecutionStatus = new BasicDBObject();
	    automation_TCExecutionStatus.put("passed", "0");
	    automation_TCExecutionStatus.put("failed", "0");
	    automation_TCExecutionStatus.put("noRun", "0");
	    automation_TCExecutionStatus.put("blocked", "0");
	    automation_TCExecutionStatus.put("defered", "0");
	    alm.put("automation_TCExecutionStatus", automation_TCExecutionStatus);
	    
	    ArrayList<Integer> defectId = new ArrayList<Integer>();
	    /*defectId.add(8956);
	    defectId.add(8955);*/
	    alm.put("defectId", defectId);
	    
	    DBObject statusAndSeverity1 = new BasicDBObject();
	    DBObject statusAndSeverity2 = new BasicDBObject();
	    DBObject statusAndSeverity3 = new BasicDBObject();
		DBObject statusAndSeverity4 = new BasicDBObject();
		DBObject statusAndSeverity5 = new BasicDBObject();
		DBObject statusAndSeverity6 = new BasicDBObject();
		DBObject statusAndSeverity7 = new BasicDBObject();

		statusAndSeverity1.put("statusSeverity", "Open/New/Re-Opened/Assigned");
		statusAndSeverity1.put("Urgent", almVO.getDefectsVO().getUrgent().get(0));
		statusAndSeverity1.put("High", almVO.getDefectsVO().getHigh().get(0));
		statusAndSeverity1.put("Medium", almVO.getDefectsVO().getMedium().get(0));
		statusAndSeverity1.put("Low", almVO.getDefectsVO().getLow().get(0));
		statusAndSeverity1.put("Total", almVO.getDefectsVO().getTotalDefects().get(0));

		statusAndSeverity2.put("statusSeverity", "Fixed/Ready for Re-test");
		statusAndSeverity2.put("Urgent", almVO.getDefectsVO().getUrgent().get(1));
		statusAndSeverity2.put("High", almVO.getDefectsVO().getHigh().get(1));
		statusAndSeverity2.put("Medium", almVO.getDefectsVO().getMedium().get(1));
		statusAndSeverity2.put("Low", almVO.getDefectsVO().getLow().get(1));
		statusAndSeverity2.put("Total", almVO.getDefectsVO().getTotalDefects().get(1));

		statusAndSeverity3.put("statusSeverity", "Duplicate/Rejected");
		statusAndSeverity3.put("Urgent", almVO.getDefectsVO().getUrgent().get(2));
		statusAndSeverity3.put("High", almVO.getDefectsVO().getHigh().get(2));
		statusAndSeverity3.put("Medium", almVO.getDefectsVO().getMedium().get(2));
		statusAndSeverity3.put("Low", almVO.getDefectsVO().getLow().get(2));
		statusAndSeverity3.put("Total", almVO.getDefectsVO().getTotalDefects().get(2));

		statusAndSeverity4.put("statusSeverity", "Deferred");
		statusAndSeverity4.put("Urgent", almVO.getDefectsVO().getUrgent().get(3));
		statusAndSeverity4.put("High", almVO.getDefectsVO().getHigh().get(3));
		statusAndSeverity4.put("Medium", almVO.getDefectsVO().getMedium().get(3));
		statusAndSeverity4.put("Low", almVO.getDefectsVO().getLow().get(3));
		statusAndSeverity4.put("Total", almVO.getDefectsVO().getTotalDefects().get(3));

		statusAndSeverity5.put("statusSeverity", "Closed");
		statusAndSeverity5.put("Urgent", almVO.getDefectsVO().getUrgent().get(4));
		statusAndSeverity5.put("High", almVO.getDefectsVO().getHigh().get(4));
		statusAndSeverity5.put("Medium", almVO.getDefectsVO().getMedium().get(4));
		statusAndSeverity5.put("Low", almVO.getDefectsVO().getLow().get(4));
		statusAndSeverity5.put("Total", almVO.getDefectsVO().getTotalDefects().get(4));
		       
		statusAndSeverity5.put("statusSeverity", "Closed");
		statusAndSeverity5.put("Urgent", almVO.getDefectsVO().getUrgent().get(4));
		statusAndSeverity5.put("High", almVO.getDefectsVO().getHigh().get(4));
		statusAndSeverity5.put("Medium", almVO.getDefectsVO().getMedium().get(4));
		statusAndSeverity5.put("Low", almVO.getDefectsVO().getLow().get(4));
		statusAndSeverity5.put("Total", almVO.getDefectsVO().getTotalDefects().get(4));
		
		statusAndSeverity6.put("statusSeverity", "Total");
		statusAndSeverity6.put("Urgent", almVO.getDefectsVO().getUrgent().get(5));
		statusAndSeverity6.put("High", almVO.getDefectsVO().getHigh().get(5));
		statusAndSeverity6.put("Medium", almVO.getDefectsVO().getMedium().get(5));
		statusAndSeverity6.put("Low", almVO.getDefectsVO().getLow().get(5));
		statusAndSeverity6.put("Total", almVO.getDefectsVO().getTotalDefects().get(5));
		
		statusAndSeverity7.put("statusSeverity", "Percentage(%)");
		statusAndSeverity7.put("Urgent", almVO.getDefectsVO().getUrgent().get(6));
		statusAndSeverity7.put("High", almVO.getDefectsVO().getHigh().get(6));
		statusAndSeverity7.put("Medium", almVO.getDefectsVO().getMedium().get(6));
		statusAndSeverity7.put("Low", almVO.getDefectsVO().getLow().get(6));
		statusAndSeverity7.put("Total", almVO.getDefectsVO().getTotalDefects().get(6));
		
		List<DBObject> statusAndSeverity = new ArrayList<DBObject>();
		statusAndSeverity.add(statusAndSeverity1);
		statusAndSeverity.add(statusAndSeverity2);
		statusAndSeverity.add(statusAndSeverity3);
		statusAndSeverity.add(statusAndSeverity4);
		statusAndSeverity.add(statusAndSeverity5);
		statusAndSeverity.add(statusAndSeverity6);
		statusAndSeverity.add(statusAndSeverity7);
			   
		alm.put("statusAndSeverity", statusAndSeverity);
		   
	    DBObject ragStatus_Manual = new BasicDBObject();
		ragStatus_Manual.put("Status", "g");
		ragStatus_Manual.put("user", "user1");
		ragStatus_Manual.put("date", "2015-03-02T07:47:37.676Z");
		alm.put("ragStatus_Manual", ragStatus_Manual);
		       
		DBObject ragStatus_System = new BasicDBObject();
		ragStatus_System.put("Status", "r");
		ragStatus_System.put("user", "System");
		ragStatus_System.put("date", "2015-03-02T07:47:37.676Z");
		
	   /* alm.put("docCreatedBy", "System");
	    alm.put("docCreatedDate", "2015-03-02T07:47:37.676Z");
	    alm.put("docUpdatedBy", "user666");
	    alm.put("lastUpdationDate", DashboardUtility.getCurrentDate());*/
	    
		alm.put("CreatedOn", DashboardUtility.getCurrentDate()); 
		alm.put("CreatedBy", "System");
		alm.put("UpdatedOn", DashboardUtility.getCurrentDate());
		alm.put("UpdatedBy", "System");
		alm.put("lastUpdationDate", DashboardUtility.getCurrentDate());
		    
	    ArrayList<String> jiraId = new ArrayList<String>();
	    /*jiraId.add("jira1");
	    jiraId.add("jira2");*/
	    alm.put("jiraId", jiraId);
	     
	    alm.put("key", keyValue);
	    
	    table.insert(alm);
	    log.info("ALM Inserted Successfully");
	}
	
	public void updateAlmToDb(AlmVO almVO, DBCollection table,String keyValue) throws Exception {
		
		Date date = DashboardUtility.getCurrentDate();
		BasicDBObject lastUpdateDate = new BasicDBObject();
		lastUpdateDate.append("$set", new BasicDBObject().append("lastUpdationDate", date));
		
		BasicDBObject updatedOn = new BasicDBObject();
		updatedOn.append("$set", new BasicDBObject().append("UpdatedOn", date));
		  
		BasicDBObject manual = new BasicDBObject();
		DBObject manual_TCExecutionStatus = new BasicDBObject();
		manual_TCExecutionStatus.put("passed", almVO.getAlmTCVO().getSchedManualVO().getPassed());
		manual_TCExecutionStatus.put("failed", almVO.getAlmTCVO().getSchedManualVO().getFailed());
		manual_TCExecutionStatus.put("noRun", almVO.getAlmTCVO().getSchedManualVO().getNoRun());
		manual_TCExecutionStatus.put("blocked", almVO.getAlmTCVO().getSchedManualVO().getBlocked());
		manual_TCExecutionStatus.put("defered", almVO.getAlmTCVO().getSchedManualVO().getDeferred());
		
	    manual.append("$set", new BasicDBObject().append("manual_TCExecutionStatus", manual_TCExecutionStatus));
	       
	    BasicDBObject statusSeverity = new BasicDBObject();
	    DBObject statusAndSeverity1 = new BasicDBObject();
	    DBObject statusAndSeverity2 = new BasicDBObject();
	    DBObject statusAndSeverity3 = new BasicDBObject();
	    DBObject statusAndSeverity4 = new BasicDBObject();
		DBObject statusAndSeverity5 = new BasicDBObject();
		DBObject statusAndSeverity6 = new BasicDBObject();
		DBObject statusAndSeverity7 = new BasicDBObject();

		statusAndSeverity1.put("statusSeverity", "Open/New/Re-Opened/Assigned");
	    statusAndSeverity1.put("Urgent", almVO.getDefectsVO().getUrgent().get(0));
	    statusAndSeverity1.put("High", almVO.getDefectsVO().getHigh().get(0));
	    statusAndSeverity1.put("Medium", almVO.getDefectsVO().getMedium().get(0));
	    statusAndSeverity1.put("Low", almVO.getDefectsVO().getLow().get(0));
	    statusAndSeverity1.put("Total", almVO.getDefectsVO().getTotalDefects().get(0));

	    statusAndSeverity2.put("statusSeverity", "Fixed/Ready for Re-test");
	    statusAndSeverity2.put("Urgent", almVO.getDefectsVO().getUrgent().get(1));
	    statusAndSeverity2.put("High", almVO.getDefectsVO().getHigh().get(1));
	    statusAndSeverity2.put("Medium", almVO.getDefectsVO().getMedium().get(1));
	    statusAndSeverity2.put("Low", almVO.getDefectsVO().getLow().get(1));
	    statusAndSeverity2.put("Total", almVO.getDefectsVO().getTotalDefects().get(1));
	    
	    statusAndSeverity3.put("statusSeverity", "Duplicate/Rejected");
	    statusAndSeverity3.put("Urgent", almVO.getDefectsVO().getUrgent().get(2));
	    statusAndSeverity3.put("High", almVO.getDefectsVO().getHigh().get(2));
	    statusAndSeverity3.put("Medium", almVO.getDefectsVO().getMedium().get(2));
	    statusAndSeverity3.put("Low", almVO.getDefectsVO().getLow().get(2));
	    statusAndSeverity3.put("Total", almVO.getDefectsVO().getTotalDefects().get(2));

	    statusAndSeverity4.put("statusSeverity", "Deferred");
	    statusAndSeverity4.put("Urgent", almVO.getDefectsVO().getUrgent().get(3));
	    statusAndSeverity4.put("High", almVO.getDefectsVO().getHigh().get(3));
	    statusAndSeverity4.put("Medium", almVO.getDefectsVO().getMedium().get(3));
	    statusAndSeverity4.put("Low", almVO.getDefectsVO().getLow().get(3));
	    statusAndSeverity4.put("Total", almVO.getDefectsVO().getTotalDefects().get(3));

		statusAndSeverity5.put("statusSeverity", "Closed");
	    statusAndSeverity5.put("Urgent", almVO.getDefectsVO().getUrgent().get(4));
	    statusAndSeverity5.put("High", almVO.getDefectsVO().getHigh().get(4));
	    statusAndSeverity5.put("Medium", almVO.getDefectsVO().getMedium().get(4));
	    statusAndSeverity5.put("Low", almVO.getDefectsVO().getLow().get(4));
	    statusAndSeverity5.put("Total", almVO.getDefectsVO().getTotalDefects().get(4));
	    
	    statusAndSeverity6.put("statusSeverity", "Total");
		statusAndSeverity6.put("Urgent", almVO.getDefectsVO().getUrgent().get(5));
		statusAndSeverity6.put("High", almVO.getDefectsVO().getHigh().get(5));
		statusAndSeverity6.put("Medium", almVO.getDefectsVO().getMedium().get(5));
		statusAndSeverity6.put("Low", almVO.getDefectsVO().getLow().get(5));
		statusAndSeverity6.put("Total", almVO.getDefectsVO().getTotalDefects().get(5));
		
		statusAndSeverity7.put("statusSeverity", "Percentage%");
		statusAndSeverity7.put("Urgent", almVO.getDefectsVO().getUrgent().get(6));
		statusAndSeverity7.put("High", almVO.getDefectsVO().getHigh().get(6));
		statusAndSeverity7.put("Medium", almVO.getDefectsVO().getMedium().get(6));
		statusAndSeverity7.put("Low", almVO.getDefectsVO().getLow().get(6));
		statusAndSeverity7.put("Total", almVO.getDefectsVO().getTotalDefects().get(6));
		
		List<DBObject> statusAndSeverity = new ArrayList<DBObject>();
		statusAndSeverity.add(statusAndSeverity1);
		statusAndSeverity.add(statusAndSeverity2);
		statusAndSeverity.add(statusAndSeverity3);
		statusAndSeverity.add(statusAndSeverity4);
		statusAndSeverity.add(statusAndSeverity5);
		statusAndSeverity.add(statusAndSeverity6);
		statusAndSeverity.add(statusAndSeverity7);
	       
	    statusSeverity.append("$set", new BasicDBObject().append("statusAndSeverity", statusAndSeverity));
		 
	    BasicDBObject automation = new BasicDBObject();
		
	    DBObject automation_TCExecutionStatus = new BasicDBObject();
	    automation_TCExecutionStatus.put("passed", "0");
	    automation_TCExecutionStatus.put("failed", "0");
	    automation_TCExecutionStatus.put("noRun", "0");
	    automation_TCExecutionStatus.put("blocked", "0");
	    automation_TCExecutionStatus.put("defered", "0");
	    
	    automation.append("$set", new BasicDBObject().append("automation_TCExecutionStatus", automation_TCExecutionStatus));
			
		BasicDBObject searchQuery = new BasicDBObject().append("key", keyValue);
			 
		table.update(searchQuery, manual);
		table.update(searchQuery, automation);
		table.update(searchQuery, statusSeverity);
		table.update(searchQuery, lastUpdateDate);
		table.update(searchQuery, updatedOn);
		
		log.info("ALM updated successfully");
	 }
}
