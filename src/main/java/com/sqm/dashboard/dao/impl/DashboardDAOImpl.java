package com.sqm.dashboard.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.sqm.dashboard.util.DashboardUtility;
@Repository
public class DashboardDAOImpl implements DashboardDAO {
	
 final Logger log=Logger.getLogger(DashboardDAOImpl.class);
 
 
 
	
	public static DBCollection getDbCollection(String sourceCollection) throws Exception{
		DBCursor cursor = null;
		DBCollection table=null;
		 DashboardVO dashVO=new DashboardVO();
		 Response.ResponseBuilder response = Response.ok(dashVO);
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
		  
		  table = db.getCollection(sourceCollection);
			 /*System.out.println("Connect to database successfully");*/
		}catch(Exception e){
	
			throw e;
		}
			 
		
		return table;
	}

	@Override
	public ArrayList getLandingInfo(String project,String release) throws Exception {
		// TODO Auto-generated method stub
		
		
		 DBCursor cursor = null;
		 DashboardVO dashVO=new DashboardVO();
		 DBCollection table=DashboardDAOImpl.getDbCollection("alm");
		 ArrayList list=new ArrayList();
		try {
			/*MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");*/
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
			/*		DBCollection table = db.getCollection("alm");*/
			 
					log.info("Connect to collection alm successfully");
					BasicDBObject searchQuery = new BasicDBObject();
				
					searchQuery.put("domain", "IB_TECHNOLOGY");
					searchQuery.put("project", project);
					searchQuery.put("release", release);
					//searchQuery.put("lastUpdationDate", DashboardUtility.getCurrentDate());
					log.debug(searchQuery.toString());
					cursor = table.find(searchQuery);
					while (cursor.hasNext()) {
						
						DBObject report =cursor.next();
						System.out.println(report);
						
						 Gson gson=new Gson();
						 ManualVO manualVO=gson.fromJson(report.get("manual_TCExecutionStatus").toString(), ManualVO.class);
						 AutomationVO automationVO=gson.fromJson(report.get("automation_TCExecutionStatus").toString(), AutomationVO.class);

						 java.lang.reflect.Type listTypestatus = new TypeToken<ArrayList<StatusAndSeverityVO>>() {}.getType();
						 /*List<StatusAndSeverityVO> statusVO=new Gson().fromJson(report.get("statusAndSeverity").toString(), listTypestatus);*/
						 List<StatusAndSeverityVO> statusVO=(List<StatusAndSeverityVO>)new Gson().fromJson(report.get("statusAndSeverity").toString(), listTypestatus);
						 java.lang.reflect.Type listTypeTest = new TypeToken<ArrayList<TestCaseExecutionStatusVO>>() {}.getType();
						// List<TestCaseExecutionStatusVO> testCaseVO=new Gson().fromJson(report.get("manual_TCExecutionStatus").toString(), listTypeTest);
						 List<TestCaseExecutionStatusVO> testCaseVO=new ArrayList<TestCaseExecutionStatusVO>();
						 TestCaseExecutionStatusVO testVOPassed=new TestCaseExecutionStatusVO();
						 TestCaseExecutionStatusVO testVOFailed=new TestCaseExecutionStatusVO();
						 TestCaseExecutionStatusVO testVONoRun=new TestCaseExecutionStatusVO();
						 TestCaseExecutionStatusVO testVOBlocked=new TestCaseExecutionStatusVO();
						 TestCaseExecutionStatusVO testVODeffered=new TestCaseExecutionStatusVO();
						 TestCaseExecutionStatusVO testVOTotal=new TestCaseExecutionStatusVO();
						 int passedV=Integer.parseInt(manualVO.getPassed())+Integer.parseInt(automationVO.getPassed());
						 int failedV=Integer.parseInt(manualVO.getFailed())+Integer.parseInt(automationVO.getFailed());
						 int noRunV=Integer.parseInt(manualVO.getNoRun())+Integer.parseInt(automationVO.getNoRun());
						 int blockedV=Integer.parseInt(manualVO.getBlocked())+Integer.parseInt(automationVO.getBlocked());
						 int defferedV=Integer.parseInt(manualVO.getDeferred())+Integer.parseInt(automationVO.getDeferred());
						 int totalValue=passedV+failedV+noRunV+blockedV+defferedV;
						 String totalPercent="100";
						 String totalValueString=""+ totalValue+"";
						 String passedValue=""+passedV+"";
						 String failedValue=""+failedV+"";
						 String noRunValue=""+noRunV+"";
						 String blockedValue=""+blockedV+"";
						 String defferedValue=""+defferedV+"";
						 
						 BigDecimal passedP=null;
						 BigDecimal failedP=null;
						 BigDecimal noRunP=null;
						 BigDecimal blockedP=null;
						 BigDecimal defferedP=null;
						 if(Double.valueOf(totalValue)*100==0){
						passedP=new BigDecimal(0); 
						 }else{
						 passedP=new BigDecimal((Double.valueOf(passedValue)/Double.valueOf(totalValue))*100);
						 }
						 if(Double.valueOf(totalValue)*100==0){
							 failedP=new BigDecimal(0); 
								 }else{
					failedP=new BigDecimal((Double.valueOf(failedValue)/Double.valueOf(totalValue))*100);
								 }
						 if(Double.valueOf(totalValue)*100==0){
							 noRunP=new BigDecimal(0); 
								 }else{
						 noRunP=new BigDecimal((Double.valueOf(noRunValue)/Double.valueOf(totalValue))*100);
								 }
						 if(Double.valueOf(totalValue)*100==0){
							 blockedP=new BigDecimal(0); 
								 }else{
				blockedP=new BigDecimal((Double.valueOf(blockedValue)/Double.valueOf(totalValue))*100);
								 }if(Double.valueOf(totalValue)*100==0){
									 defferedP=new BigDecimal(0); 
								 }else{
						defferedP=new BigDecimal((Double.valueOf(defferedValue)/Double.valueOf(totalValue))*100);
								 }
						 
						 
						/* BigDecimal passedP=new BigDecimal((Double.valueOf(passedValue)/Double.valueOf(totalValue))*100);
						 BigDecimal failedP=new BigDecimal((Double.valueOf(failedValue)/Double.valueOf(totalValue))*100);
						 BigDecimal noRunP=new BigDecimal((Double.valueOf(noRunValue)/Double.valueOf(totalValue))*100);
						 BigDecimal blockedP=new BigDecimal((Double.valueOf(blockedValue)/Double.valueOf(totalValue))*100);
						 BigDecimal defferedP=new BigDecimal((Double.valueOf(defferedValue)/Double.valueOf(totalValue))*100);*/
						 
						 
						
						 System.out.println(passedP);
						 String passedPercent=passedP.setScale(2, RoundingMode.CEILING).toString()+"%";
						 String failedPercent=failedP.setScale(2, RoundingMode.CEILING).toString()+"%";
						 
						 String noRunPercent=noRunP.setScale(2, RoundingMode.CEILING).toString()+"%";
						 String blockedPercent=blockedP.setScale(2, RoundingMode.CEILING).toString()+"%";
						 String defferedPercent=defferedP.setScale(2, RoundingMode.CEILING).toString()+"%";
						 
						 
						 testVOPassed.setStatus("Passed");
						 testVOPassed.setCount(passedValue);
						 testVOPassed.setPercentage(passedPercent);
						 
						 
						 
						 testVOFailed.setStatus("Failed");
						 testVOFailed.setCount(failedValue);
						 testVOFailed.setPercentage(failedPercent);
						 
						 testVONoRun.setStatus("NoRun");
						 testVONoRun.setCount(noRunValue);
						 testVONoRun.setPercentage(noRunPercent);
						 
						 testVOBlocked.setStatus("Blocked");
						 testVOBlocked.setCount(blockedValue);
						 testVOBlocked.setPercentage(blockedPercent);
						 
						 testVODeffered.setStatus("Deffered");
						 testVODeffered.setCount(defferedValue);
						 testVODeffered.setPercentage(defferedPercent);
						 
						 testVOTotal.setStatus("Total");
						 testVOTotal.setCount(totalValueString);
						 testVOTotal.setPercentage(totalPercent);
					
						 testCaseVO.add(testVOPassed);
						 testCaseVO.add(testVOFailed);
						 testCaseVO.add(testVONoRun);
						 testCaseVO.add(testVOBlocked);
						 testCaseVO.add(testVODeffered);
						 testCaseVO.add(testVOTotal);
						 
						 
						dashVO=new DashboardVO();
						dashVO.setManualVO(manualVO);
						dashVO.setAutomationVO(automationVO);

						dashVO.setStatusAndSeverityVO(statusVO);
					dashVO.setTestCaseExecutionStatusVO(testCaseVO);
					for(TestCaseExecutionStatusVO tr:testCaseVO){
						System.out.println(tr.toString());
					  }
						dashVO.setRdate(report.get("UpdatedOn").toString());
						//dashVO.setPlan(report.get("plan").toString());
						list.add(dashVO);
						System.out.println(dashVO.toString()+list);
					
						/*DBObject report =cursor.next();
						 
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
						dashVO.setTestCaseExecutionStatusVO(testCaseVO);*/
						
						log.debug("Response form Mongo :");
						log.debug("manualVO :"+ dashVO.getManualVO());
						log.debug("automationVO :"+ dashVO.getAutomationVO());
						log.debug("effortsVO :"+ dashVO.getEffortsVO());
						log.debug("severityVO :"+ dashVO.getSeverityVO());
						log.debug("StatusAndSeverityVO :"+ dashVO.getStatusAndSeverityVO());
						log.debug("TestCaseExecutionStatusVO :"+ dashVO.getTestCaseExecutionStatusVO());
						
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					
					log.debug("DAO Layer");
					throw e;
				}
			 finally {
					cursor.close();
					//table.getDB().getMongo().close();
				}
		return list;
	}
	
	
	@Override
	public Response getApplicationsList() throws Exception {
		// TODO Auto-generated method stub
		
		
		 DBCursor cursor = null;
		 
		
		 ArrayList projectName=new ArrayList();
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
			 /*System.out.println("Connect to database successfully");*/
		 
				DBCollection table = db.getCollection("release");
				BasicDBObject searchQuery = new BasicDBObject();
				System.out.println("Search Query is "+searchQuery);
				cursor=table.find();
				projectName= (ArrayList) table.distinct("project");
				System.out.println("The list is"+ projectName);
				
				
			}catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				
				log.debug("DAO Layer");
				throw e;
			}
			 
		 Response.ResponseBuilder response = Response.ok(projectName);
			return response.build();
	}
	
	
	@Override
	public Response getReleaseList(String project) throws Exception {
		// TODO Auto-generated method stub
		
		
		 DBCursor cursor = null;
		 
		
		 ArrayList releaseList=new ArrayList();
		try {
			MongoClient clientDb;
			
				clientDb = new MongoClient("172.23.16.28", 27017);
			
		  DB db = clientDb.getDB("sqmdb");
			 /*System.out.println("Connect to database successfully");*/
		 
		  DBCollection table = db.getCollection("release");
			BasicDBObject searchQuery = new BasicDBObject();
			System.out.println("Search Query is "+searchQuery);
			searchQuery.put("project", project);
			cursor=table.find(searchQuery);
			
			while (cursor.hasNext()) {
				DBObject report =cursor.next();
				System.out.println("The "+report.get("release"));
				releaseList.add(report.get("release"));
			}
				
				
			}catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				
				log.debug("DAO Layer");
				throw e;
			}
			 
		 Response.ResponseBuilder response = Response.ok(releaseList);
			return response.build();
	}


}
