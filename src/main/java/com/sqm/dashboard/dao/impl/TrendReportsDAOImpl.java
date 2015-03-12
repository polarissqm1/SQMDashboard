package com.sqm.dashboard.dao.impl;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.sqm.dashboard.VO.DefectIdsVO;
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
	public ArrayList getTrendingInfo(String project,String release,String fromDate,String toDate) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		final Logger log=Logger.getLogger(DashboardController.class);
System.out.println("inside TrendReportsDAOImpl");
		 DBCursor cursor = null;
		 DashboardVO dashVO;
		 ArrayList list=new ArrayList();
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
			 /*System.out.println("Connect to database successfully");*/
			 
			 log.info("Connect to database successfully");
			 log.info("DAO Layer");
				
					DBCollection table = db.getCollection("alm_1");
					log.info("Connect to collection alm successfully");
					BasicDBObject searchQuery = new BasicDBObject();
				
					searchQuery.put("domain", "IB_TECHNOLOGY");
					searchQuery.put("projects", project);
					searchQuery.put("release", release);
					searchQuery.put("lastUpdationDate", BasicDBObjectBuilder.start("$gte",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(fromDate)).add("$lte", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(toDate)).get());
					
					
					log.debug(searchQuery.toString());
					cursor = table.find(searchQuery);
					log.debug(cursor);
					while (cursor.hasNext()) {
						DBObject report =cursor.next();
						 Gson gson=new Gson();
						 ManualVO manualVO=gson.fromJson(report.get("manual_TCExecutionStatus").toString(), ManualVO.class);
						 AutomationVO automationVO=gson.fromJson(report.get("automation_TCExecutionStatus").toString(), AutomationVO.class);
						 java.lang.reflect.Type listTypestatus = new TypeToken<ArrayList<StatusAndSeverityVO>>() {}.getType();
						 List<StatusAndSeverityVO> statusVO=(List<StatusAndSeverityVO>)new Gson().fromJson(report.get("statusAndSeverity").toString(), listTypestatus);
						 log.debug("************************************************ :");
						log.debug(report.get("lastUpdationDate").toString());
						dashVO=new DashboardVO();
						dashVO.setManualVO(manualVO);
						dashVO.setAutomationVO(automationVO);
						dashVO.setStatusAndSeverityVO(statusVO);
						String lastUpDate=report.get("lastUpdationDate").toString();
						DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
						Date date = (Date)formatter.parse(lastUpDate);
						System.out.println(date);        

						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
						String rdate = cal.get(Calendar.DATE) + "/" + new SimpleDateFormat("MMM").format(cal.getTime());
						System.out.println("formatedDate : " + rdate);
						dashVO.setRdate(rdate);
						//dashVO.setPlan(report.get("plan").toString());
						log.debug(dashVO.toString());
						log.debug("************************************************ :");
						log.debug("rdate is "+rdate);
						log.debug("Response form Mongo :");
						log.debug("Planned is"+dashVO.getPlan() );
						log.debug("manualVO :"+ dashVO.getManualVO());
						log.debug("automationVO :"+ dashVO.getAutomationVO());
						log.debug("effortsVO :"+ dashVO.getEffortsVO());
						log.debug("severityVO :"+ dashVO.getSeverityVO());
						log.debug("StatusAndSeverityVO :"+ dashVO.getStatusAndSeverityVO());
						log.debug("TestCaseExecutionStatusVO :"+ dashVO.getTestCaseExecutionStatusVO());
						list.add(dashVO);
						
					}
				}catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					
					log.debug("DAO Layer");
					throw e;
				}catch (Exception e) {
					// TODO Auto-generated catch block
					
					log.debug("DAO Layer",e);
				
				}
			 finally {
					cursor.close();
				}
		return list ;
	}

	@Override
	public ArrayList getReleaseInfo(String project, String release,
			String fromDate, String toDate) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		DBCursor cursor = null;
		DashboardVO dashVO;
		ArrayList list=new ArrayList();
		final Logger log=Logger.getLogger(DashboardController.class);
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
			 /*System.out.println("Connect to database successfully");*/
			 
			 log.info("Connect to database successfully");
			 log.info("DAO Layer");
			 DBCollection table = db.getCollection("alm_1");
			 log.info("Connect to collection alm successfully");
			 BasicDBObject searchQuery = new BasicDBObject();
			 searchQuery.put("domain", "IB_TECHNOLOGY");
			 searchQuery.put("project", project);
			 searchQuery.put("release", release);
			 //searchQuery.put("lastUpdationDate", BasicDBObjectBuilder.start("$gte", "13/feb/15").add("$lte", "25/feb/15").get());
			 cursor = table.find(searchQuery);
			 while (cursor.hasNext()){
					DBObject report =cursor.next();
					Gson gson=new Gson();
					java.lang.reflect.Type listTypeTest = new TypeToken<ArrayList<DefectIdsVO>>() {}.getType();
					List<DefectIdsVO> defectsVO=new Gson().fromJson(report.get("defects").toString(), listTypeTest);
					dashVO=new DashboardVO();
					dashVO.setDefectVO(defectsVO);
					list.add(dashVO);
			 }
			 }catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					
					log.debug("DAO Layer");
					throw e;
				}
		finally {
			cursor.close();
		}
		
		
			 
		return list;
	}
	
}

	

