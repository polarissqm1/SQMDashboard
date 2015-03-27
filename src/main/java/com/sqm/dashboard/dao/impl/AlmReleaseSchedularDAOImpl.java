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
import com.sqm.dashboard.VO.AlmReleaseVO;
import com.sqm.dashboard.dao.AlmReleaseSchedularDAO;
import com.sqm.dashboard.util.Constants;
import com.sqm.dashboard.util.DashboardUtility;

@Repository("almReleaseSchedularDaoImpl")
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
		}finally{
			//table.getDB().getMongo().close();
		}
	}
	
	public void insertAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection table, String keyValue) throws Exception{
	
		log.info("AlmReleaseSchedularDAOImpl insertAlmReleasesToDb");
		
		BasicDBObject almRelease = new BasicDBObject();
		
		almRelease.put(Constants.DB_DOMAIN, almReleaseVO.getDomain());
		almRelease.put(Constants.DB_PROJECT, almReleaseVO.getProject());
		almRelease.put(Constants.DB_RELEASE, almReleaseVO.getReleaseName());
		almRelease.put(Constants.DB_REL_SDATE, almReleaseVO.getReleaseStartDate());
		almRelease.put(Constants.DB_REL_EDATE, almReleaseVO.getReleaseEndDate());
		almRelease.put(Constants.DB_RELEASE_RELFOLDER, almReleaseVO.getReleaseFolder());
		
		List<DBObject> cycles = new ArrayList<DBObject>();

		DBObject cycleData = null;
		
		for(int i=0; i<almReleaseVO.getSchedReleaseCyclesVO().getCycleName().size(); i++) {
			cycleData = new BasicDBObject();
			cycleData.put(Constants.DB_RELEASE_CYCLENAME, almReleaseVO.getSchedReleaseCyclesVO().getCycleName().get(i));
			cycleData.put(Constants.DB_RELEASE_CYCLE_SDATE, almReleaseVO.getSchedReleaseCyclesVO().getCycleStartDate().get(i));
			cycleData.put(Constants.DB_RELEASE_CYCLE_EDATE, almReleaseVO.getSchedReleaseCyclesVO().getCycleEndDate().get(i));
			cycles.add(i, cycleData);
		}
		
		almRelease.put(Constants.DB_RELEASE_CYCLES, cycles);
		
		almRelease.put(Constants.DB_RELEASE_PLANNEDTC, "0");
		
		List<DBObject> defects = new ArrayList<DBObject>();
		DBObject defectData = null;
		for(int j=0; j<almReleaseVO.getSchedReleaseDefectsVO().getDefectId().size(); j++) {
			defectData = new BasicDBObject();
			defectData.put(Constants.DB_RELEASE_DEFECT_ID, almReleaseVO.getSchedReleaseDefectsVO().getDefectId().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_TYPE, almReleaseVO.getSchedReleaseDefectsVO().getDefectType().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_ROOTCAUSE, almReleaseVO.getSchedReleaseDefectsVO().getDefectRootCause().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_RAISEDDATE, almReleaseVO.getSchedReleaseDefectsVO().getDefectRaisedDate().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_FIXEDDATE, almReleaseVO.getSchedReleaseDefectsVO().getDefectFixedDate().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_SEVERITY, almReleaseVO.getSchedReleaseDefectsVO().getDefectSeverity().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_FIXTIME, almReleaseVO.getSchedReleaseDefectsVO().getDefectFixTime().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECTSTATUS, almReleaseVO.getSchedReleaseDefectsVO().getDefectStatus().get(j));
			defects.add(j, defectData);
		}
		
		almRelease.put(Constants.DB_RELEASE_DEFECTS, defects);
		
		almRelease.put(Constants.DB_RELEASE_STATUS, "");
		almRelease.put(Constants.DB_RELEASE_ENVDATES, "");
		
		almRelease.put(Constants.DB_CREATED_ON, DashboardUtility.getCurrentDate()); 
		almRelease.put(Constants.DB_CREATED_BY, "System");
		almRelease.put(Constants.DB_UPDATED_ON, DashboardUtility.getCurrentDate());
		almRelease.put(Constants.DB_UPDATED_BY, "System");
		
		almRelease.put(Constants.KEY, keyValue);
	    
		table.insert(almRelease);
		log.info("Alm Release collection inserted successfully");
		
	}
	
	public void updateAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection table, String keyValue) throws Exception {
		
		Date date = DashboardUtility.getCurrentDate();
		
		BasicDBObject releaseStartDate = new BasicDBObject();
		releaseStartDate.append("$set", new BasicDBObject().append(Constants.DB_REL_SDATE, almReleaseVO.getReleaseStartDate()));
		
		BasicDBObject releaseEndDate = new BasicDBObject();
		releaseEndDate.append("$set", new BasicDBObject().append(Constants.DB_REL_EDATE, almReleaseVO.getReleaseEndDate()));
		
		BasicDBObject updateCycles = new BasicDBObject();
		List<DBObject> cycles = new ArrayList<DBObject>();
		DBObject cycleData = null;

		for(int i=0; i<almReleaseVO.getSchedReleaseCyclesVO().getCycleName().size(); i++) {
			cycleData = new BasicDBObject();
			cycleData.put(Constants.DB_RELEASE_CYCLENAME, almReleaseVO.getSchedReleaseCyclesVO().getCycleName().get(i));
			cycleData.put(Constants.DB_RELEASE_CYCLE_SDATE, almReleaseVO.getSchedReleaseCyclesVO().getCycleStartDate().get(i));
			cycleData.put(Constants.DB_RELEASE_CYCLE_EDATE, almReleaseVO.getSchedReleaseCyclesVO().getCycleEndDate().get(i));
			cycles.add(i, cycleData);
		}
		
		updateCycles.append("$set", new BasicDBObject().append(Constants.DB_RELEASE_CYCLES, cycles));
		
		BasicDBObject plannedTC = new BasicDBObject();
		plannedTC.append("$set", new BasicDBObject().append(Constants.DB_RELEASE_PLANNEDTC, "0"));
		
		BasicDBObject updateDefects = new BasicDBObject();
		List<DBObject> defects = new ArrayList<DBObject>();
		DBObject defectData = null;
		for(int j=0; j<almReleaseVO.getSchedReleaseDefectsVO().getDefectId().size(); j++) {
			defectData = new BasicDBObject();
			defectData.put(Constants.DB_RELEASE_DEFECT_ID, almReleaseVO.getSchedReleaseDefectsVO().getDefectId().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_TYPE, almReleaseVO.getSchedReleaseDefectsVO().getDefectType().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_ROOTCAUSE, almReleaseVO.getSchedReleaseDefectsVO().getDefectRootCause().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_RAISEDDATE, almReleaseVO.getSchedReleaseDefectsVO().getDefectRaisedDate().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_FIXEDDATE, almReleaseVO.getSchedReleaseDefectsVO().getDefectFixedDate().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_SEVERITY, almReleaseVO.getSchedReleaseDefectsVO().getDefectSeverity().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECT_FIXTIME, almReleaseVO.getSchedReleaseDefectsVO().getDefectFixTime().get(j));
			defectData.put(Constants.DB_RELEASE_DEFECTSTATUS, almReleaseVO.getSchedReleaseDefectsVO().getDefectStatus().get(j));
			defects.add(j, defectData);
		}
		
		updateDefects.append("$set", new BasicDBObject().append(Constants.DB_RELEASE_DEFECTS, defects));
		
		BasicDBObject updatedBy = new BasicDBObject();
		updatedBy.append("$set", new BasicDBObject().append(Constants.DB_UPDATED_BY, "System"));
		
		BasicDBObject updatedOn = new BasicDBObject();
		updatedOn.append("$set", new BasicDBObject().append(Constants.DB_UPDATED_ON, date));
		
		BasicDBObject searchQuery = new BasicDBObject().append(Constants.KEY, keyValue);
		
		table.update(searchQuery, releaseStartDate);
		table.update(searchQuery, releaseEndDate);
		table.update(searchQuery, updateCycles);
		table.update(searchQuery, plannedTC);
		table.update(searchQuery, updateDefects);
		table.update(searchQuery, updatedBy);
		table.update(searchQuery, updatedOn);
		
		log.info("Alm Release collection updated successfully");
		
	}
}
