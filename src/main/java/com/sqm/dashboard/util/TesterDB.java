package com.sqm.dashboard.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.sqm.dashboard.VO.AutomationVO;
import com.sqm.dashboard.VO.DashboardVO;
import com.sqm.dashboard.VO.EffortsVO;
import com.sqm.dashboard.VO.ManualVO;
import com.sqm.dashboard.VO.SeverityVO;
import com.sqm.dashboard.VO.StatusAndSeverityVO;
import com.sqm.dashboard.VO.TestCaseExecutionStatusVO;
import com.sqm.dashboard.schedular.JiraSchedulerImpl;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.format.datetime.DateFormatter;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class TesterDB {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
    JiraSchedulerImpl ji=new JiraSchedulerImpl();

    int i =ji.dateChecker(DashboardUtility.getCurrentDate());
		
		
	MongoClient clientDb=new MongoClient("172.23.16.28", 27017);
		
		  DB db = clientDb.getDB("sqmdb");
			 System.out.println("Connect to database successfully");
			 DashboardVO dashVO=new DashboardVO();
			 ArrayList list=new ArrayList();
			 DBCursor cursor = null;
				try {
					DBCollection table = db.getCollection("alm");
					BasicDBObject searchQuery = new BasicDBObject();
					
					Date date=new Date();
					Date date_final=new Date();
				    System.out.println("the Todays Date is "+ date);
				    
				    String dateStr = date.toString();
				    DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				    Date date2 = (Date)formatter.parse(dateStr);
				    System.out.println("Required Date is"+date2);        

				    Calendar cal1 = Calendar.getInstance();
				    cal1.setTime(date2);
				    String formatedDate = cal1.get(Calendar.DATE) + "/" + new SimpleDateFormat("MMM").format(cal1.getTime()) + "/" +   new SimpleDateFormat("yy").format(cal1.getTime());
				    System.out.println("Formatted Date is "+formatedDate);
				    Date sdf= new SimpleDateFormat("dd/MMM/yy").parse(formatedDate);
				    DateTime dt=new DateTime(sdf);
				    dt=dt.plusDays(1);
				    date_final=dt.toDate();
				    System.out.println("Plus one date is "+date_final );
				    
				    DateFormat plusOneDay = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				    Date plusOneDate = (Date)formatter.parse(date_final.toString());
				    Calendar cal2 = Calendar.getInstance();
				    cal2.setTime(plusOneDate);
				    String plusOneformatedDate = cal2.get(Calendar.DATE) + "/" + new SimpleDateFormat("MMM").format(cal2.getTime()) + "/" +   new SimpleDateFormat("yy").format(cal2.getTime());
				    System.out.println(date2); 
				    
				    System.out.println("formatedDate Final : " + plusOneformatedDate);    
				    
					
					 
					searchQuery.put("domain", "IB_TECHNOLOGY");
					searchQuery.put("projects", "CFT_POST_TRADE");
					searchQuery.put("release", "CFP Reporting: Sprint 2");
					//searchQuery.put("lastUpdationDate", "Thu Mar 12 00:00:00 IST 2015");
					/*searchQuery.put("UpdatedOn", BasicDBObjectBuilder.start("$gte",new SimpleDateFormat("dd/MMM/yy").parse(formatedDate)).add("$lt", new SimpleDateFormat("dd/MMM/yy").parse(plusOneformatedDate)).get());*/
					searchQuery.put("UpdatedOn", BasicDBObjectBuilder.start("$gte",new SimpleDateFormat("dd/MMM/yy").parse("18/Mar/15")).add("$lt", new SimpleDateFormat("dd/MMM/yy").parse("19/Mar/15")).get());
					cursor = table.find(searchQuery);
					while (cursor.hasNext()) {
						DBObject report =cursor.next();
						System.out.println(report);
						
						 Gson gson=new Gson();
						 ManualVO manualVO=gson.fromJson(report.get("manual_TCExecutionStatus").toString(), ManualVO.class);
						 AutomationVO automationVO=gson.fromJson(report.get("automation_TCExecutionStatus").toString(), AutomationVO.class);

						 java.lang.reflect.Type listTypestatus = new TypeToken<ArrayList<StatusAndSeverityVO>>() {}.getType();
						 /*List<StatusAndSeverityVO> statusVO=new Gson().fromJson(report.get("statusAndSeverity").toString(), listTypestatus);*/
						 List<StatusAndSeverityVO> statusVO=(List<StatusAndSeverityVO>)new Gson().fromJson(report.get("statusAndSeverity").toString(), Object.class);
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
						 int defferedV=Integer.parseInt(manualVO.getDefered())+Integer.parseInt(automationVO.getDefered());
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
						dashVO.setRdate(report.get("lastUpdationDate").toString());
						//dashVO.setPlan(report.get("plan").toString());
						list.add(dashVO);
						System.out.println(dashVO.toString()+list);			
					}
					System.out.println("Hi");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
	}


