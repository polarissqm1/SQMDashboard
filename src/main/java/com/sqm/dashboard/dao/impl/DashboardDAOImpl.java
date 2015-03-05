package com.sqm.dashboard.dao.impl;

import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
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
@Repository
public class DashboardDAOImpl implements DashboardDAO {

	@Override
	public Response getLandingInfo(String project,String release) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		final Logger log=Logger.getLogger(DashboardController.class);
		 DBCursor cursor = null;
		 DashboardVO dashVO=new DashboardVO();
		 Response.ResponseBuilder response = Response.ok(dashVO);
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
			 /*System.out.println("Connect to database successfully");*/
			 
			 log.info("Connect to database successfully");
			 log.info("DAO Layer");
					/* RAG Data fetch
					 * 
					 DBCollection table = db.getCollection("almNew");
					
					BasicDBObject searchQuery = new BasicDBObject();
				
					searchQuery.put("domain", "posttrade");
					searchQuery.put("projects", "PMP");
					searchQuery.put("release", "pmpRelease1");
					
					cursor = table.find(searchQuery);
					System.out.println(cursor);
					while (cursor.hasNext()) {
						DBObject report =cursor.next();
						 
						 Gson gson=new Gson();
						
						 System.out.println(report);
						RagVO ragVO_system =gson.fromJson(report.get("ragStatus_System").toString(), RagVO.class);
						RagVO ragVO_manual =gson.fromJson(report.get("ragStatus_Manual").toString(), RagVO.class);
						if(ragVO_manual.getStatus() != null){
							System.out.println(ragVO_manual.getStatus() );
							dashVO.setRagVO(ragVO_manual);
						}
						else {
							dashVO.setRagVO(ragVO_system);
						} 
					 
					 * */
					DBCollection table = db.getCollection("almOld");
					log.info("Connect to collection alm successfully");
					BasicDBObject searchQuery = new BasicDBObject();
				
					searchQuery.put("domain", "posttrade");
					searchQuery.put("projects", project);
					searchQuery.put("release", release);
					log.debug(searchQuery.toString());
					cursor = table.find(searchQuery);
					while (cursor.hasNext()) {
						DBObject report =cursor.next();
						 
						 Gson gson=new Gson();
						 ManualVO manualVO=gson.fromJson(report.get("manual").toString(), ManualVO.class);
						 AutomationVO automationVO=gson.fromJson(report.get("automation").toString(), AutomationVO.class);
						 EffortsVO effortsVO=gson.fromJson(report.get("efforts").toString(), EffortsVO.class);
						 SeverityVO severityVO=gson.fromJson(report.get("severity").toString(), SeverityVO.class);
						 List<StatusAndSeverityVO> statusVO=(List<StatusAndSeverityVO>)gson.fromJson(report.get("statusAndSeverity").toString(),Object.class);
						
						List<TestCaseExecutionStatusVO> testCaseVO=(List<TestCaseExecutionStatusVO>)gson.fromJson(report.get("testCaseExecutionStatus").toString(), Object.class);
						
						
						dashVO.setManualVO(manualVO);
						dashVO.setAutomationVO(automationVO);
						dashVO.setEffortsVO(effortsVO);
						dashVO.setSeverityVO(severityVO);
						dashVO.setStatusAndSeverityVO(statusVO);
						dashVO.setTestCaseExecutionStatusVO(testCaseVO);
						
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
		return response.build();
	}

}
