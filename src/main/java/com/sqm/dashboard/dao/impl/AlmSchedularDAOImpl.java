package com.sqm.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.dao.AlmSchedularDAO;
import com.sqm.dashboard.util.Constants;
import com.sqm.dashboard.util.DashboardUtility;

@Repository("almSchedularDaoImpl")
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
		}finally{
			//table.getDB().getMongo().close();
		}
	}

	public  void insertAlmToDb(AlmVO almVO, DBCollection table, String keyValue) throws Exception {
		
		BasicDBObject alm = new BasicDBObject();
		
		alm.put(Constants.DB_ALM_USERID, "admin");
		alm.put(Constants.DB_DOMAIN, almVO.getDomain());
		alm.put(Constants.DB_PROJECT, almVO.getProject());
		alm.put(Constants.DB_RELEASE, almVO.getRelease());
		alm.put(Constants.DB_REL_SDATE, almVO.getRelStartDate());
		alm.put(Constants.DB_REL_EDATE, almVO.getRelEndDate());
		
		ArrayList<String> defectId = new ArrayList<String>();
		for(int i=0; i<almVO.getDefectIds().size(); i++) {
			defectId.add(almVO.getDefectIds().get(i));
		}
		alm.put(Constants.DB_ALM_DEFECT_IDS, defectId);
		
		DBObject manual_TCExecutionStatus = new BasicDBObject();
		manual_TCExecutionStatus.put(Constants.DB_ALM_PASSED, almVO.getAlmTCVO().getSchedManualVO().getPassed());
		manual_TCExecutionStatus.put(Constants.DB_ALM_FAILED, almVO.getAlmTCVO().getSchedManualVO().getFailed());
		manual_TCExecutionStatus.put(Constants.DB_ALM_NORUN, almVO.getAlmTCVO().getSchedManualVO().getNoRun());
		manual_TCExecutionStatus.put(Constants.DB_ALM_BLOCKED, almVO.getAlmTCVO().getSchedManualVO().getBlocked());
		manual_TCExecutionStatus.put(Constants.DB_ALM_DEFERRED, almVO.getAlmTCVO().getSchedManualVO().getDeferred());
		alm.put(Constants.DB_ALM_MANUAL_TC_EXECSTATUS, manual_TCExecutionStatus);
		
		DBObject automation_TCExecutionStatus = new BasicDBObject();
	    automation_TCExecutionStatus.put(Constants.DB_ALM_PASSED, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_FAILED, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_NORUN, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_BLOCKED, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_DEFERRED, "0");
	    alm.put(Constants.DB_ALM_AUTOMATION_TC_EXECSTATUS, automation_TCExecutionStatus);
	    
	    DBObject statusAndSeverity1 = new BasicDBObject();
	    DBObject statusAndSeverity2 = new BasicDBObject();
	    DBObject statusAndSeverity3 = new BasicDBObject();
		DBObject statusAndSeverity4 = new BasicDBObject();
		DBObject statusAndSeverity5 = new BasicDBObject();
		DBObject statusAndSeverity6 = new BasicDBObject();
		DBObject statusAndSeverity7 = new BasicDBObject();

		statusAndSeverity1.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_OP_NW_REOP_ASSIGN_VAL);
		statusAndSeverity1.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(0));
		statusAndSeverity1.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(0));
		statusAndSeverity1.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(0));
		statusAndSeverity1.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(0));
		statusAndSeverity1.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(0));

		statusAndSeverity2.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_FIX_READY_VAL);
		statusAndSeverity2.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(1));
		statusAndSeverity2.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(1));
		statusAndSeverity2.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(1));
		statusAndSeverity2.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(1));
		statusAndSeverity2.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(1));

		statusAndSeverity3.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_DUP_REJ_VAL);
		statusAndSeverity3.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(2));
		statusAndSeverity3.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(2));
		statusAndSeverity3.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(2));
		statusAndSeverity3.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(2));
		statusAndSeverity3.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(2));

		statusAndSeverity4.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_DEF_VAL);
		statusAndSeverity4.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(3));
		statusAndSeverity4.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(3));
		statusAndSeverity4.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(3));
		statusAndSeverity4.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(3));
		statusAndSeverity4.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(3));

		statusAndSeverity5.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_CLOSED_VAL);
		statusAndSeverity5.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(4));
		statusAndSeverity5.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(4));
		statusAndSeverity5.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(4));
		statusAndSeverity5.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(4));
		statusAndSeverity5.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(4));
		       
		statusAndSeverity6.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_TOTAL_VAL);
		statusAndSeverity6.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(5));
		
		statusAndSeverity7.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_PER_VAL);
		statusAndSeverity7.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(6));
		
		List<DBObject> statusAndSeverity = new ArrayList<DBObject>();
		statusAndSeverity.add(statusAndSeverity1);
		statusAndSeverity.add(statusAndSeverity2);
		statusAndSeverity.add(statusAndSeverity3);
		statusAndSeverity.add(statusAndSeverity4);
		statusAndSeverity.add(statusAndSeverity5);
		statusAndSeverity.add(statusAndSeverity6);
		statusAndSeverity.add(statusAndSeverity7);
			   
		alm.put(Constants.DB_ALM_STATUS_AND_SEVERITY, statusAndSeverity);
		
		DBObject ragStatus_System = new BasicDBObject();
		ragStatus_System.put(Constants.DB_ALM_RAG_STATUS, "g");
		ragStatus_System.put(Constants.DB_ALM_RAG_USER, "System");
		ragStatus_System.put(Constants.DB_ALM_RAG_DATE, "");
		alm.put(Constants.DB_ALM_RAG_SYSTEM, ragStatus_System);
		
	    DBObject ragStatus_Manual = new BasicDBObject();
		ragStatus_Manual.put(Constants.DB_ALM_RAG_STATUS, "g");
		ragStatus_Manual.put(Constants.DB_ALM_RAG_USER, "user1");
		ragStatus_Manual.put(Constants.DB_ALM_RAG_DATE, "");
		alm.put(Constants.DB_ALM_RAG_MANUAL, ragStatus_Manual);
		       
		alm.put(Constants.DB_CREATED_ON, DashboardUtility.getCurrentDate()); 
		alm.put(Constants.DB_CREATED_BY, "System");
		alm.put(Constants.DB_UPDATED_ON, DashboardUtility.getCurrentDate());
		alm.put(Constants.DB_UPDATED_BY, "System");
		    
	  ArrayList<String> jiraId = new ArrayList<String>();
		for(int j=0; j<almVO.getJiraIds().size(); j++) {
			jiraId.add(almVO.getJiraIds().get(j));
		}
		alm.put(Constants.DB_ALM_JIRAIDS, jiraId);
		
	    alm.put(Constants.KEY, keyValue);
	    
	    table.insert(alm);
	    log.info("ALM Inserted Successfully");
	}
	
	public void updateAlmToDb(AlmVO almVO, DBCollection table, String keyValue) throws Exception {
		
		Date date = DashboardUtility.getCurrentDate();
		
		BasicDBObject relStartDate = new BasicDBObject();
		relStartDate.append("$set", new BasicDBObject().append(Constants.DB_REL_SDATE, almVO.getRelStartDate()));
		
		BasicDBObject relEndDate = new BasicDBObject();
		relEndDate.append("$set", new BasicDBObject().append(Constants.DB_REL_EDATE, almVO.getRelEndDate()));
		
		BasicDBObject defectIds = new BasicDBObject();
		ArrayList<String> defectId = new ArrayList<String>();
		for(int i=0; i<almVO.getDefectIds().size(); i++) {
			defectId.add(almVO.getDefectIds().get(i));
		}
		defectIds.append("$set", new BasicDBObject().append(Constants.DB_ALM_DEFECT_IDS, defectId));
		
		BasicDBObject jiraIds = new BasicDBObject();
		ArrayList<String> jiraId = new ArrayList<String>();
		for(int j=0; j<almVO.getJiraIds().size(); j++) {
			jiraId.add(almVO.getJiraIds().get(j));
		}
		jiraIds.append("$set", new BasicDBObject().append(Constants.DB_ALM_JIRAIDS, jiraId));
		
		BasicDBObject manual = new BasicDBObject();
		DBObject manual_TCExecutionStatus = new BasicDBObject();
		manual_TCExecutionStatus.put(Constants.DB_ALM_PASSED, almVO.getAlmTCVO().getSchedManualVO().getPassed());
		manual_TCExecutionStatus.put(Constants.DB_ALM_FAILED, almVO.getAlmTCVO().getSchedManualVO().getFailed());
		manual_TCExecutionStatus.put(Constants.DB_ALM_NORUN, almVO.getAlmTCVO().getSchedManualVO().getNoRun());
		manual_TCExecutionStatus.put(Constants.DB_ALM_BLOCKED, almVO.getAlmTCVO().getSchedManualVO().getBlocked());
		manual_TCExecutionStatus.put(Constants.DB_ALM_DEFERRED, almVO.getAlmTCVO().getSchedManualVO().getDeferred());
		
	    manual.append("$set", new BasicDBObject().append(Constants.DB_ALM_MANUAL_TC_EXECSTATUS, manual_TCExecutionStatus));
	    
	    BasicDBObject statusSeverity = new BasicDBObject();
	    DBObject statusAndSeverity1 = new BasicDBObject();
	    DBObject statusAndSeverity2 = new BasicDBObject();
	    DBObject statusAndSeverity3 = new BasicDBObject();
	    DBObject statusAndSeverity4 = new BasicDBObject();
		DBObject statusAndSeverity5 = new BasicDBObject();
		DBObject statusAndSeverity6 = new BasicDBObject();
		DBObject statusAndSeverity7 = new BasicDBObject();

		statusAndSeverity1.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_OP_NW_REOP_ASSIGN_VAL);
	    statusAndSeverity1.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(0));
	    statusAndSeverity1.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(0));
	    statusAndSeverity1.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(0));
	    statusAndSeverity1.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(0));
	    statusAndSeverity1.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(0));

	    statusAndSeverity2.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_FIX_READY_VAL);
	    statusAndSeverity2.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(1));
	    statusAndSeverity2.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(1));
	    statusAndSeverity2.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(1));
	    statusAndSeverity2.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(1));
	    statusAndSeverity2.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(1));
	    
	    statusAndSeverity3.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_DUP_REJ_VAL);
	    statusAndSeverity3.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(2));
	    statusAndSeverity3.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(2));
	    statusAndSeverity3.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(2));
	    statusAndSeverity3.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(2));
	    statusAndSeverity3.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(2));

	    statusAndSeverity4.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_DEF_VAL);
	    statusAndSeverity4.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(3));
	    statusAndSeverity4.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(3));
	    statusAndSeverity4.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(3));
	    statusAndSeverity4.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(3));
	    statusAndSeverity4.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(3));

		statusAndSeverity5.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_CLOSED_VAL);
	    statusAndSeverity5.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(4));
	    statusAndSeverity5.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(4));
	    statusAndSeverity5.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(4));
	    statusAndSeverity5.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(4));
	    statusAndSeverity5.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(4));
	    
	    statusAndSeverity6.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_TOTAL_VAL);
		statusAndSeverity6.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(5));
		statusAndSeverity6.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(5));
		
		statusAndSeverity7.put(Constants.DB_ALM_STATUS_SEVERITY, Constants.DB_ALM_STATUS_PER_VAL);
		statusAndSeverity7.put(Constants.DB_ALM_URGENT, almVO.getDefectsVO().getUrgent().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_HIGH, almVO.getDefectsVO().getHigh().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_MEDIUM, almVO.getDefectsVO().getMedium().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_LOW, almVO.getDefectsVO().getLow().get(6));
		statusAndSeverity7.put(Constants.DB_ALM_TOTAL, almVO.getDefectsVO().getTotalDefects().get(6));
		
		List<DBObject> statusAndSeverity = new ArrayList<DBObject>();
		statusAndSeverity.add(statusAndSeverity1);
		statusAndSeverity.add(statusAndSeverity2);
		statusAndSeverity.add(statusAndSeverity3);
		statusAndSeverity.add(statusAndSeverity4);
		statusAndSeverity.add(statusAndSeverity5);
		statusAndSeverity.add(statusAndSeverity6);
		statusAndSeverity.add(statusAndSeverity7);
	       
	    statusSeverity.append("$set", new BasicDBObject().append(Constants.DB_ALM_STATUS_AND_SEVERITY, statusAndSeverity));
		 
	    BasicDBObject automation = new BasicDBObject();
		
	    DBObject automation_TCExecutionStatus = new BasicDBObject();
	    automation_TCExecutionStatus.put(Constants.DB_ALM_PASSED, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_FAILED, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_NORUN, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_BLOCKED, "0");
	    automation_TCExecutionStatus.put(Constants.DB_ALM_DEFERRED, "0");
	    
	    automation.append("$set", new BasicDBObject().append(Constants.DB_ALM_AUTOMATION_TC_EXECSTATUS, automation_TCExecutionStatus));
		
	    BasicDBObject updatedBy = new BasicDBObject();
		updatedBy.append("$set", new BasicDBObject().append(Constants.DB_UPDATED_BY, "System"));
		
		BasicDBObject updatedOn = new BasicDBObject();
		updatedOn.append("$set", new BasicDBObject().append(Constants.DB_UPDATED_ON, date));
		
		BasicDBObject searchQuery = new BasicDBObject().append(Constants.KEY, keyValue);
			 
		table.update(searchQuery, updatedBy);
		table.update(searchQuery, updatedOn);
		table.update(searchQuery, relStartDate);
		table.update(searchQuery, relEndDate);
		table.update(searchQuery, defectIds);
		table.update(searchQuery, jiraIds);
		table.update(searchQuery, manual);
		table.update(searchQuery, automation);
		table.update(searchQuery, statusSeverity);
		
		log.info("ALM updated successfully");
	 }
}
