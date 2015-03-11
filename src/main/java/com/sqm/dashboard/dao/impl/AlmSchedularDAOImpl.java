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
import com.sqm.dashboard.schedular.AlmSchedular;
import com.sqm.dashboard.schedular.AlmSchedularImpl;
import com.sqm.dashboard.schedular.JiraSchedulerImpl;
import com.sqm.dashboard.util.DashboardUtility;

public class AlmSchedularDAOImpl implements AlmSchedularDAO {
	
	final Logger log = Logger.getLogger(AlmSchedularDAOImpl.class);
	public void validatorInsertion(AlmVO almVO) throws Exception{
		
		 DBCursor cursor = null;
		String keyValue=almVO.getRelease()+DashboardUtility.getCurrentDate().toString();
		 DBCollection table=DashboardDAOImpl.getDbCollection("alm");
		try{
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("domain", almVO.getDomain());
			cursor = table.find(searchQuery);
		
			
			boolean isKeyValueMatching = false;
			
			while (cursor.hasNext()) {
				DBObject report =cursor.next();
				if(report.get("key").toString().equalsIgnoreCase(keyValue)){
					isKeyValueMatching = true;
				}
			}
			if(isKeyValueMatching){
				 System.out.println("isKeyValueMatching : " + isKeyValueMatching);
				 System.out.println("updateAlmToDb");
			 	 updateAlmToDb(almVO, table, keyValue);
			 } else {
				 System.out.println("isKeyValueMatching : " + isKeyValueMatching);
				 System.out.println("insertAlmToDb");
				 insertAlmToDb(almVO, table, keyValue);
			 }
			
		}catch(Exception e)
		{
			log.info("Exception occured at Validation level");
			throw e;
		}
	}

	@Override
	public  void insertAlmToDb(AlmVO almVO, DBCollection table,String keyValue) throws Exception {


		 BasicDBObject alm = new BasicDBObject();
		/* String keyValue=almVO.getRelease()+DashboardUtility.getCurrentDate().toString();
		 DBCollection table=DashboardDAOImpl.getDbCollection("alm");*/
	       
	       List<BasicDBObject> automation_TCExecutionStatus = new ArrayList<BasicDBObject>();
	       automation_TCExecutionStatus.add(new BasicDBObject("passed", "2000"));
	       automation_TCExecutionStatus.add(new BasicDBObject("failed", "1000"));
	       automation_TCExecutionStatus.add(new BasicDBObject("noRun", "500"));
	       automation_TCExecutionStatus.add(new BasicDBObject("blocked", "150"));
	       automation_TCExecutionStatus.add(new BasicDBObject("defered", "20"));
	       alm.put("automation_TCExecutionStatus", automation_TCExecutionStatus);
	       
	       ArrayList<Integer> defectId = new ArrayList<Integer>();
	       defectId.add(8956);
	       defectId.add(8955);
	       alm.put("defectId", defectId);
	       
	       alm.put("docCreatedBy", "System");
	       alm.put("docCreatedDate", "2015-03-02T07:47:37.676Z");
	       alm.put("docUpdatedBy", "user666");
	       alm.put("domain", almVO.getDomain());
	       
	       
	       ArrayList<String> jiraId = new ArrayList<String>();
	       jiraId.add("jira1");
	       jiraId.add("jira2");
	       alm.put("jiraId", jiraId);
	     
	     //  alm.put("key", "CFPCOB 2015-03-02");
	       alm.put("key", keyValue);
	       alm.put("lastUpdationDate", DashboardUtility.getCurrentDate());
	       
	       
	       List<BasicDBObject> manual_TCExecutionStatus = new ArrayList<BasicDBObject>();
	       /*manual_TCExecutionStatus.add(new BasicDBObject("passed", "2000"));
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("passed", almVO.getTestcaseVO().getPassed()));
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("failed", "850"));
	       manual_TCExecutionStatus.add(new BasicDBObject("noRun", "1500"));
	       manual_TCExecutionStatus.add(new BasicDBObject("blocked", "200"));
	       manual_TCExecutionStatus.add(new BasicDBObject("defered", "120"));
	       alm.put("manual_TCExecutionStatus", manual_TCExecutionStatus);*/
	      
	       
 // manual_TCExecutionStatus.add(new BasicDBObject("passed", "2000"));
	       
	       /*manual_TCExecutionStatus.add(new BasicDBObject("passed", almVO.getTestcaseVO().getPassed()));
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("failed", almVO.getTestcaseVO().getFailed()));
	       manual_TCExecutionStatus.add(new BasicDBObject("noRun", almVO.getTestcaseVO().getNoRun()));
	       manual_TCExecutionStatus.add(new BasicDBObject("blocked", almVO.getTestcaseVO().getBlocked()));
	       manual_TCExecutionStatus.add(new BasicDBObject("defered", almVO.getTestcaseVO().getDeferred()));*/
	       
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("passed", almVO.getAlmTCVO().getSchedManualVO().getPassed()));
	       manual_TCExecutionStatus.add(new BasicDBObject("failed", almVO.getAlmTCVO().getSchedManualVO().getFailed()));
	       manual_TCExecutionStatus.add(new BasicDBObject("noRun", almVO.getAlmTCVO().getSchedManualVO().getNoRun()));
	       manual_TCExecutionStatus.add(new BasicDBObject("blocked", almVO.getAlmTCVO().getSchedManualVO().getBlocked()));
	       manual_TCExecutionStatus.add(new BasicDBObject("defered", almVO.getAlmTCVO().getSchedManualVO().getDeferred()));
	       
	       alm.put("manual_TCExecutionStatus", manual_TCExecutionStatus);
	      
	       
	       alm.put("projects", almVO.getProject());
	       
	       List<BasicDBObject> ragStatus_Manual = new ArrayList<BasicDBObject>();
	       ragStatus_Manual.add(new BasicDBObject("Status", "g"));
	       ragStatus_Manual.add(new BasicDBObject("user", "user1"));
	       ragStatus_Manual.add(new BasicDBObject("date", "2015-03-02T07:47:37.676Z"));
	       alm.put("ragStatus_Manual", ragStatus_Manual);
	       
	       List<BasicDBObject> ragStatus_System = new ArrayList<BasicDBObject>();
	       ragStatus_System.add(new BasicDBObject("Status", "r"));
	       ragStatus_System.add(new BasicDBObject("user", "system"));
	       ragStatus_System.add(new BasicDBObject("date", "2015-03-02T07:47:37.676Z"));
	       
	       alm.put("release", almVO.getRelease());
	       alm.put("release_EDate", "2015-03-02T07:47:37.676Z");
	       alm.put("release_SDate", "2015-03-02T07:47:37.676Z");
	       
	       List<BasicDBObject> statusAndSeverity = new ArrayList<BasicDBObject>();
	       
	       
	       
	       
	       
	    	   
	    	   
	    	   statusAndSeverity.add(new BasicDBObject("statusSeverity", "Open/New/Re-Opened/Assigned"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(0)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(0)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(0)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(0)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(0)));
			
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Fixed/Ready for Re-test"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(1)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(1)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(1)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(1)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(1)));
		       
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Duplicate/Rejected"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(2)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(2)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(2)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(2)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(2)));
		       
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Deferred"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(3)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(3)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(3)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(3)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(3)));
		       
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Closed"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(4)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(4)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(4)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(4)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(4)));
	    	   
	       
	       
	       
	       
	       
	       /*statusAndSeverity.add(new BasicDBObject("statusSeverity", "Open/New/Re-Opened/Assigned"));
	       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(0)));
	       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(0)));
	       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(0)));
	       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(0)));
		
	       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Fixed/Ready for Re-test"));
	       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(1)));
	       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(1)));
	       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(1)));
	       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(1)));
	       
	       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Duplicate/Rejected"));
	       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(2)));
	       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(2)));
	       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(2)));
	       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(2)));
	       
	       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Deferred"));
	       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(3)));
	       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(3)));
	       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(3)));
	       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(3)));
	       
	       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Closed"));
	       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(4)));
	       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(4)));
	       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(4)));
	       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(4)));*/
	       
	       alm.put("statusAndSeverity", statusAndSeverity);
	       
	       alm.put("userid", "user555");
	       
	       table.insert(alm);
	       System.out.println(" ALM Inserted Successfully");
		
		
		
	}
	
	private static void updateAlmToDb(AlmVO almVO, DBCollection table,String keyValue) throws Exception{
		
		
		/* String keyValue=almVO.getRelease()+DashboardUtility.getCurrentDate().toString();
		 DBCollection table=DashboardDAOImpl.getDbCollection("alm");*/
		 //System.out.println("Update DB");
		 Date date=DashboardUtility.getCurrentDate();
		 BasicDBObject lastUpdateDate = new BasicDBObject();
		 ///////////////////////////////////////////////////////
		 lastUpdateDate.append("$set", new BasicDBObject().append("lastUpdationDate",date));
		  
	       
		 BasicDBObject manual = new BasicDBObject();
	       List<BasicDBObject> manual_TCExecutionStatus = new ArrayList<BasicDBObject>();
	       
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("passed", almVO.getAlmTCVO().getSchedManualVO().getPassed()));
	       manual_TCExecutionStatus.add(new BasicDBObject("failed", almVO.getAlmTCVO().getSchedManualVO().getFailed()));
	       manual_TCExecutionStatus.add(new BasicDBObject("noRun", almVO.getAlmTCVO().getSchedManualVO().getNoRun()));
	       manual_TCExecutionStatus.add(new BasicDBObject("blocked", almVO.getAlmTCVO().getSchedManualVO().getBlocked()));
	       manual_TCExecutionStatus.add(new BasicDBObject("defered", almVO.getAlmTCVO().getSchedManualVO().getDeferred()));
	       
	   /*    manual_TCExecutionStatus.add(new BasicDBObject("passed", almVO.getTestcaseVO().getPassed()));
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("failed", almVO.getTestcaseVO().getFailed()));
	       manual_TCExecutionStatus.add(new BasicDBObject("noRun", almVO.getTestcaseVO().getNoRun()));
	       manual_TCExecutionStatus.add(new BasicDBObject("blocked", almVO.getTestcaseVO().getBlocked()));
	       manual_TCExecutionStatus.add(new BasicDBObject("defered", almVO.getTestcaseVO().getDeferred()));*/
	       
	       
	       manual.append("$set", new BasicDBObject().append("manual_TCExecutionStatus", manual_TCExecutionStatus));
	       
	      
	       BasicDBObject statusSeverity = new BasicDBObject();
	       
	       List<BasicDBObject> statusAndSeverity = new ArrayList<BasicDBObject>();
	       
	       
	       
	       
	       
	    	   
	    	   
   
	    	   
	    	   statusAndSeverity.add(new BasicDBObject("statusSeverity", "Open/New/Re-Opened/Assigned"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(0)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(0)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(0)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(0)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(0)));
			
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Fixed/Ready for Re-test"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(1)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(1)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(1)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(1)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(1)));
		       
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Duplicate/Rejected"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(2)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(2)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(2)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(2)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(2)));
		       
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Deferred"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(3)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(3)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(3)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(3)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(3)));
		       
		       statusAndSeverity.add(new BasicDBObject("statusSeverity", "Closed"));
		       statusAndSeverity.add(new BasicDBObject("Urgent", almVO.getDefectsVO().getUrgent().get(4)));
		       statusAndSeverity.add(new BasicDBObject("High", almVO.getDefectsVO().getHigh().get(4)));
		       statusAndSeverity.add(new BasicDBObject("Medium", almVO.getDefectsVO().getMedium().get(4)));
		       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(4)));
		       statusAndSeverity.add(new BasicDBObject("Total", almVO.getDefectsVO().getTotalDefects().get(4)));
	    	   
	       
	       
	       
	       statusSeverity.append("$set", new BasicDBObject().append("statusAndSeverity", statusAndSeverity));
		 
		 /////////////////////////////////////////////////////////
		 
		  
	       
		 
	       BasicDBObject automation = new BasicDBObject();
		 List<BasicDBObject> automation_TCExecutionStatus = new ArrayList<BasicDBObject>();
	        automation_TCExecutionStatus.add(new BasicDBObject("passed", "2000675858"));
	        automation_TCExecutionStatus.add(new BasicDBObject("failed", "10040"));
	        automation_TCExecutionStatus.add(new BasicDBObject("noRun", "500547u54"));
	        automation_TCExecutionStatus.add(new BasicDBObject("blocked", "15057"));
	        automation_TCExecutionStatus.add(new BasicDBObject("defered", "203443"));
	        
	        automation.append("$set", new BasicDBObject().append("automation_TCExecutionStatus", automation_TCExecutionStatus));
			
			
		

			BasicDBObject searchQuery = new BasicDBObject().append("key",keyValue);
			 
			table.update(searchQuery, manual);
			table.update(searchQuery, automation);
			table.update(searchQuery, statusSeverity);
			table.update(searchQuery, lastUpdateDate);
			
	 }


}
