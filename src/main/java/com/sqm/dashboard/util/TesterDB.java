package com.sqm.dashboard.util;

import java.net.UnknownHostException;
import java.util.List;

import com.sqm.dashboard.VO.AutomationVO;
import com.sqm.dashboard.VO.DashboardVO;
import com.sqm.dashboard.VO.EffortsVO;
import com.sqm.dashboard.VO.ManualVO;
import com.sqm.dashboard.VO.SeverityVO;
import com.sqm.dashboard.VO.StatusAndSeverityVO;
import com.sqm.dashboard.VO.TestCaseExecutionStatusVO;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class TesterDB {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub

		MongoClient clientDb=new MongoClient("172.23.16.28", 27017);
		
		  DB db = clientDb.getDB("sqmdb");
			 System.out.println("Connect to database successfully");
			 
			 
			 DBCursor cursor = null;
				try {
					DBCollection table = db.getCollection("alm");
					BasicDBObject searchQuery = new BasicDBObject();
					searchQuery.put("userid", "user1");
				/*	searchQuery.put("domain", "posttrade");
					searchQuery.put("projects", "CFPR");
					searchQuery.put("release", "cfprRelease2");*/
					cursor = table.find(searchQuery);
					while (cursor.hasNext()) {
						DBObject report =cursor.next();
						 /*DBObject report =(BasicDBObject) cursor.next().get("manual");
						 String passed=report.get("passed").toString();
						 System.out.println(passed);*/
						// System.out.println( cursor.next().get("manual"));
						 Gson gson=new Gson();
						 ManualVO manualVO=gson.fromJson(report.get("manual").toString(), ManualVO.class);
						 AutomationVO automationVO=gson.fromJson(report.get("automation").toString(), AutomationVO.class);
						 EffortsVO effortsVO=gson.fromJson(report.get("efforts").toString(), EffortsVO.class);
						 SeverityVO severityVO=gson.fromJson(report.get("severity").toString(), SeverityVO.class);
						 List<StatusAndSeverityVO> statusVO=(List<StatusAndSeverityVO>)gson.fromJson(report.get("statusAndSeverity").toString(),Object.class);
						// gson.from
						List<TestCaseExecutionStatusVO> testCaseVO=(List<TestCaseExecutionStatusVO>)gson.fromJson(report.get("testCaseExecutionStatus").toString(), Object.class);
						System.out.println(manualVO.toString());
						/*System.out.println(severityVO.toString());
						System.out.println(statusVO.toString());
						System.out.println(testCaseVO.toString());*/
						System.out.println(statusVO.toString());
						DashboardVO dashVO=new DashboardVO();
						dashVO.setManualVO(manualVO);
						dashVO.setAutomationVO(automationVO);
						dashVO.setEffortsVO(effortsVO);
						dashVO.setSeverityVO(severityVO);
						//statusVO.get(1).setTotal("200");
						dashVO.setStatusAndSeverityVO(statusVO);
						dashVO.setTestCaseExecutionStatusVO(testCaseVO);
						System.out.println(dashVO.getEffortsVO());
						// System.out.println(statusVO.toString());
						//System.out.println(report.get("statusAndSeverity").toString());
						
					}
				} finally {
					cursor.close();
				}
			}
	}


