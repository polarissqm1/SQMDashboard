package com.sqm.dashboard.dao.impl;

import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.core.Response;

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
import com.sqm.dashboard.dao.DashboardDAO;
@Repository
public class DashboardDAOImpl implements DashboardDAO {

	@Override
	public Response getLandingInfo(String project,String release) {
		// TODO Auto-generated method stub
		 DBCursor cursor = null;
		 DashboardVO dashVO=new DashboardVO();
		 Response.ResponseBuilder response = Response.ok(dashVO);
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
			 System.out.println("Connect to database successfully");
			 
			 
			
				
					DBCollection table = db.getCollection("alm");
					BasicDBObject searchQuery = new BasicDBObject();
				
					searchQuery.put("domain", "posttrade");
					searchQuery.put("projects", project);
					searchQuery.put("release", release);
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
						
						
						
					}
				}catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 finally {
					cursor.close();
				}
		return response.build();
	}

}
