package com.sqm.dashboard.dao.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sqm.dashboard.VO.AutomationVO;
import com.sqm.dashboard.VO.DashboardVO;
import com.sqm.dashboard.VO.EffortsVO;
import com.sqm.dashboard.VO.ManualVO;
import com.sqm.dashboard.VO.SeverityVO;
import com.sqm.dashboard.VO.StatusAndSeverityVO;
import com.sqm.dashboard.VO.TestCaseExecutionStatusVO;
import com.sqm.dashboard.controller.DashboardController;
import com.sqm.dashboard.dao.DashboardDAO;
import com.sqm.dashboard.dao.TrendReportsDAO;
@Repository
public class TrendReportsDAOImpl implements TrendReportsDAO {
	
	@Override
	public DashboardVO getTrendingInfo(String project,String release,String fromDate,String toDate) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		final Logger log=Logger.getLogger(DashboardController.class);
		 DBCursor cursor = null;
		 DashboardVO dashVO=new DashboardVO();
		 
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
			 /*System.out.println("Connect to database successfully");*/
			 
			 log.info("Connect to database successfully");
			 log.info("DAO Layer");
				
					DBCollection table = db.getCollection("almOld");
					log.info("Connect to collection alm successfully");
					BasicDBObject searchQuery = new BasicDBObject();
				
					searchQuery.put("domain", "IB_TECHNOLOGY");
					searchQuery.put("projects", project);
					searchQuery.put("release", release);
					searchQuery.put("release_SDate", BasicDBObjectBuilder.start("$gte", fromDate).add("$lte", toDate).get());
					
					
					log.debug(searchQuery.toString());
					cursor = table.find(searchQuery);
					while (cursor.hasNext()) {
						DBObject report =cursor.next();
						 
						 Gson gson=new Gson();
						 ManualVO manualVO=gson.fromJson(report.get("manual").toString(), ManualVO.class);
						 AutomationVO automationVO=gson.fromJson(report.get("automation").toString(), AutomationVO.class);
						 EffortsVO effortsVO=gson.fromJson(report.get("efforts").toString(), EffortsVO.class);
						 SeverityVO severityVO=gson.fromJson(report.get("severity").toString(), SeverityVO.class);
						 java.lang.reflect.Type listTypestatus = new TypeToken<ArrayList<StatusAndSeverityVO>>() {}.getType();
						 List<StatusAndSeverityVO> statusVO=new Gson().fromJson(report.get("statusAndSeverity").toString(), listTypestatus);
						 java.lang.reflect.Type listTypeTest = new TypeToken<ArrayList<TestCaseExecutionStatusVO>>() {}.getType();
						 List<TestCaseExecutionStatusVO> testCaseVO=new Gson().fromJson(report.get("testCaseExecutionStatus").toString(), listTypeTest);
						
						
						dashVO.setManualVO(manualVO);
						dashVO.setAutomationVO(automationVO);
						dashVO.setEffortsVO(effortsVO);
						dashVO.setSeverityVO(severityVO);
						dashVO.setStatusAndSeverityVO(statusVO);
						dashVO.setTestCaseExecutionStatusVO(testCaseVO);
						dashVO.setRdate(report.get("release_SDate").toString());
						log.debug("Response form Mongo :");
						log.debug("manualVO :"+ dashVO.getManualVO());
						log.debug("automationVO :"+ dashVO.getAutomationVO());
						log.debug("effortsVO :"+ dashVO.getEffortsVO());
						log.debug("severityVO :"+ dashVO.getSeverityVO());
						log.debug("StatusAndSeverityVO :"+ dashVO.getStatusAndSeverityVO());
						log.debug("TestCaseExecutionStatusVO :"+ dashVO.getTestCaseExecutionStatusVO());
						
					}
				}catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					
					log.debug("DAO Layer");
					throw e;
				}
			 finally {
					cursor.close();
				}
		return dashVO ;
	}

}
