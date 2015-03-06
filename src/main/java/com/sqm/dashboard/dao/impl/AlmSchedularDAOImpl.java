package com.sqm.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.dao.AlmSchedularDAO;
import com.sqm.dashboard.schedular.AlmSchedular;
import com.sqm.dashboard.schedular.AlmSchedularImpl;

public class AlmSchedularDAOImpl implements AlmSchedularDAO {

	@Override
	public  void insertAlmToDb(AlmVO almVO) throws Exception {


		 BasicDBObject alm = new BasicDBObject();
	       
	       DBCollection table=DashboardDAOImpl.getDbCollection("alm");
	       
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
	     
	       alm.put("key", "CFPCOB 2015-03-02");
	       alm.put("lastUpdationDate", "2015-03-02T07:47:37.676Z");
	       
	       List<BasicDBObject> manual_TCExecutionStatus = new ArrayList<BasicDBObject>();
	       manual_TCExecutionStatus.add(new BasicDBObject("passed", "2000"));
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("passed", almVO.getTestcaseVO().getPassed()));
	       
	       manual_TCExecutionStatus.add(new BasicDBObject("failed", "850"));
	       manual_TCExecutionStatus.add(new BasicDBObject("noRun", "1500"));
	       manual_TCExecutionStatus.add(new BasicDBObject("blocked", "200"));
	       manual_TCExecutionStatus.add(new BasicDBObject("defered", "120"));
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
	       statusAndSeverity.add(new BasicDBObject("Low", almVO.getDefectsVO().getLow().get(4)));
	       
	       alm.put("statusAndSeverity", statusAndSeverity);
	       
	       alm.put("userid", "user555");
	       
	       table.insert(alm);
	       System.out.println("Inserted Successfully");
		
		
		
	}


}
