package com.sqm.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sqm.dashboard.VO.AlmReleaseVO;
import com.sqm.dashboard.dao.AlmReleaseSchedularDAO;
import com.sqm.dashboard.util.DashboardUtility;

public class AlmReleaseSchedularDAOImpl implements AlmReleaseSchedularDAO {

	final Logger log = Logger.getLogger(AlmSchedularDAOImpl.class);
	public void storeAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection table) throws Exception{
		
		DBCursor cursor = null;
		String keyValue = almReleaseVO.getDomain() +"|"+ almReleaseVO.getProject() + "|" + almReleaseVO.getReleaseName();

		try{
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("domain", almReleaseVO.getDomain());
			searchQuery.put("project", almReleaseVO.getProject());
			searchQuery.put("release", almReleaseVO.getReleaseName());
			
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
				 updateAlmReleasesToDb(almReleaseVO, table, keyValue);
				 log.info("Updated record to release collection");
			 } else {
				 log.info("isKeyValueMatching : " + isKeyValueMatching);
				 insertAlmReleasesToDb(almReleaseVO, table, keyValue);
				 log.info("Inserted record to release collection");
			 }
		} catch(Exception e) {
			log.info("Exception occured at Update/Insert to release collection");
			throw e;
		}
	}
	
	public void insertAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection table, String keyValue) throws Exception{
		
		log.info("AlmReleaseSchedularDAOImpl insertAlmReleasesToDb");
		
		BasicDBObject almRelease = new BasicDBObject();
		
		almRelease.put("domain", almReleaseVO.getDomain());
		almRelease.put("project", almReleaseVO.getProject());
		almRelease.put("release", almReleaseVO.getReleaseName());
		almRelease.put("release_SDate", almReleaseVO.getReleaseStartDate());
		almRelease.put("release_EDate", almReleaseVO.getReleaseEndDate());
		almRelease.put("releaseFolder", almReleaseVO.getReleaseFolder());
		
		List<DBObject> cycles = new ArrayList<DBObject>();

		DBObject cycleData = null;
		
		for(int i=0; i<almReleaseVO.getSchedReleaseCyclesVO().getCycleName().size(); i++) {
			cycleData = new BasicDBObject();
			cycleData.put("cycleName", almReleaseVO.getSchedReleaseCyclesVO().getCycleName().get(i));
			cycleData.put("cycle_SDate", almReleaseVO.getSchedReleaseCyclesVO().getCycleStartDate().get(i));
			cycleData.put("cycle_EDate", almReleaseVO.getSchedReleaseCyclesVO().getCycleEndDate().get(i));
			cycles.add(i, cycleData);
		}
		
		almRelease.put("cycles", cycles);
		
		almRelease.put("plannedTestcases", "0");
		
		List<DBObject> defects = new ArrayList<DBObject>();
		DBObject defectData = null;
		for(int j=0; j<almReleaseVO.getSchedReleaseDefectsVO().getDefectId().size(); j++) {
			defectData = new BasicDBObject();
			defectData.put("defectId", almReleaseVO.getSchedReleaseDefectsVO().getDefectId().get(j));
			defectData.put("defectType", almReleaseVO.getSchedReleaseDefectsVO().getDefectType().get(j));
			defectData.put("defectRootCause", almReleaseVO.getSchedReleaseDefectsVO().getDefectRootCause().get(j));
			defectData.put("defectRaisedDate", almReleaseVO.getSchedReleaseDefectsVO().getDefectRaisedDate().get(j));
			defectData.put("defectFixedDate", almReleaseVO.getSchedReleaseDefectsVO().getDefectFixedDate().get(j));
			defectData.put("defectSeverity", almReleaseVO.getSchedReleaseDefectsVO().getDefectSeverity().get(j));
			defectData.put("defectFixTime", almReleaseVO.getSchedReleaseDefectsVO().getDefectFixTime().get(j));
			defectData.put("defectStatus", almReleaseVO.getSchedReleaseDefectsVO().getDefectStatus().get(j));
			defects.add(j, defectData);
		}
		
		almRelease.put("defects", defects);
		
		almRelease.put("status", "");
		almRelease.put("envDates", "");
		
		almRelease.put("CreatedOn", DashboardUtility.getCurrentDate()); 
		almRelease.put("CreatedBy", "System");
		almRelease.put("UpdatedOn", DashboardUtility.getCurrentDate());
		almRelease.put("UpdatedBy", "System");
		
		almRelease.put("key", keyValue);
	    
		table.insert(almRelease);
		log.info("Alm Release collection inserted successfully");
	}
	
	public void updateAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection table, String keyValue) throws Exception {
		
		Date date = DashboardUtility.getCurrentDate();
		
		BasicDBObject updateCycles = new BasicDBObject();
		List<DBObject> cycles = new ArrayList<DBObject>();
		DBObject cycleData = null;

		for(int i=0; i<almReleaseVO.getSchedReleaseCyclesVO().getCycleName().size(); i++) {
			cycleData = new BasicDBObject();
			cycleData.put("cycleName", almReleaseVO.getSchedReleaseCyclesVO().getCycleName().get(i));
			cycleData.put("cycle_SDate", almReleaseVO.getSchedReleaseCyclesVO().getCycleStartDate().get(i));
			cycleData.put("cycle_EDate", almReleaseVO.getSchedReleaseCyclesVO().getCycleEndDate().get(i));
			cycles.add(i, cycleData);
		}
		
		updateCycles.append("$set", new BasicDBObject().append("cycles", cycles));
		
		BasicDBObject plannedTC = new BasicDBObject();
		plannedTC.append("$set", new BasicDBObject().append("plannedTestcases", "0"));
		
		BasicDBObject updateDefects = new BasicDBObject();
		List<DBObject> defects = new ArrayList<DBObject>();
		DBObject defectData = null;
		for(int j=0; j<almReleaseVO.getSchedReleaseDefectsVO().getDefectId().size(); j++) {
			defectData = new BasicDBObject();
			defectData.put("defectId", almReleaseVO.getSchedReleaseDefectsVO().getDefectId().get(j));
			defectData.put("defectType", almReleaseVO.getSchedReleaseDefectsVO().getDefectType().get(j));
			defectData.put("defectRootCause", almReleaseVO.getSchedReleaseDefectsVO().getDefectRootCause().get(j));
			defectData.put("defectRaisedDate", almReleaseVO.getSchedReleaseDefectsVO().getDefectRaisedDate().get(j));
			defectData.put("defectFixedDate", almReleaseVO.getSchedReleaseDefectsVO().getDefectFixedDate().get(j));
			defectData.put("defectSeverity", almReleaseVO.getSchedReleaseDefectsVO().getDefectSeverity().get(j));
			defectData.put("defectFixTime", almReleaseVO.getSchedReleaseDefectsVO().getDefectFixTime().get(j));
			defectData.put("defectStatus", almReleaseVO.getSchedReleaseDefectsVO().getDefectStatus().get(j));
			defects.add(j, defectData);
		}
		
		updateDefects.append("$set", new BasicDBObject().append("defects", defects));
		
		BasicDBObject updatedBy = new BasicDBObject();
		updatedBy.append("$set", new BasicDBObject().append("UpdatedBy", "System"));
		
		BasicDBObject updatedOn = new BasicDBObject();
		updatedOn.append("$set", new BasicDBObject().append("UpdatedOn", date));
		
		BasicDBObject searchQuery = new BasicDBObject().append("key", keyValue);
		
		table.update(searchQuery, updateCycles);
		table.update(searchQuery, plannedTC);
		table.update(searchQuery, updateDefects);
		table.update(searchQuery, updatedBy);
		table.update(searchQuery, updatedOn);
		
		log.info("Alm Release collection updated successfully");
		
	}
}
