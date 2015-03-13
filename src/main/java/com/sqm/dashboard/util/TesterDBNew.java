package com.sqm.dashboard.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import com.sqm.dashboard.VO.AutomationVO;
import com.sqm.dashboard.VO.DashboardVO;
import com.sqm.dashboard.VO.EffortJiraVO;
import com.sqm.dashboard.VO.EffortsVO;
import com.sqm.dashboard.VO.JiraIdVO;
import com.sqm.dashboard.VO.JiraSchedulerVO;
import com.sqm.dashboard.VO.ManualVO;
import com.sqm.dashboard.VO.SeverityVO;
import com.sqm.dashboard.VO.StatusAndSeverityVO;
import com.sqm.dashboard.VO.TestCaseExecutionStatusVO;
import com.sqm.dashboard.schedular.AlmSchedularImpl;
import com.sqm.dashboard.schedular.JiraSchedulerImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.apache.noggit.JSONUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class TesterDBNew {
	
	

	
public static DBCollection getConnection() throws Exception{
	

	
	MongoClient clientDb=new MongoClient("172.23.16.28", 27017);
	
	  DB db = clientDb.getDB("sqmdb");

		 
		 
		 DBCursor cursor = null;
			
				DBCollection table = db.getCollection("jiraNew");
				return table;
			}
	DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yy");
	public static void main(String[] args) throws Exception {
		
JiraSchedulerImpl jiraScheduler=new JiraSchedulerImpl();
		try {
			
			
			String projectUrl="https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/";
			//log.info("Hiting Url at project level: " + projectUrl);
			Client client = Client.create();
			WebResource webResource = client.resource(projectUrl);
			ClientResponse clientResponse = webResource.accept(
					"application/json").get(ClientResponse.class);
			String stringResponse = clientResponse.getEntity(String.class);
			JSONArray jsonArray;
			jsonArray = new JSONArray(stringResponse);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.get("key").toString().contains("CFP") || jsonObject.get("key").toString().contains("CFT")){
					System.out.println(jsonObject.get("key").toString());
					System.out.println(jsonObject.get("id").toString());
				}
				
				
				
			
						
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		/*
		
		AlmSchedularImpl almObj=new AlmSchedularImpl();
		AlmSchedularImpl almObj1=new AlmSchedularImpl();
		almObj.startAlmInsert(almObj1);
		
		try {
		String mainUrl="https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/";

		
		Client client=Client.create();
		WebResource webResource=client.resource(mainUrl);
		ClientResponse clientResponse=webResource.accept("application/json").get(
				ClientResponse.class);
		String stringResponse=clientResponse.getEntity(String.class);
		CheckVO checker=clientResponse.getEntity(CheckVO.class);
		System.out.println(checker.getReleaseDate());
		System.out.println(stringResponse);
		Gson gsonObj=new Gson();
		//gsonObj.
		
		JSONArray jsonArray;
		
			
				jsonArray = new JSONArray( stringResponse );
			
			for(int i=0;i<jsonArray.length();i++){
				TesterDBNew tester=new TesterDBNew();
		JSONObject jsonObject=jsonArray.getJSONObject(i);
		
		
		
		//System.out.println("Application Name:"+ jsonObject.get("key"));
		
			releaseUrl="https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/"+URLEncoder.encode(jsonObject.get("key").toString(), "UTF-8")+"/versions/";
			tester.doProcess(jsonObject.get("key").toString());
			
			}
			
			
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
		
	*/}
	
	public  void doProcess(String applicationName) throws Exception{
		
		String url;
		String versionUrl;
		String releaseUrl;
		try {
			releaseUrl="https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/"+URLEncoder.encode(applicationName, "UTF-8")+"/versions/";
			versionUrl="https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/CFPCOB/versions/";
			url = "https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/"+ URLEncoder.encode("CFPCOB", "UTF-8")
				+ "%20AND%20fixVersion%20=%20"
				+ ""
				+ "'"+URLEncoder.encode("Dec PROD Release", "UTF-8")+"'";
//			System.out.println(url);

			Client client=Client.create();
			WebResource webResource=client.resource(releaseUrl);
			ClientResponse clientResponse=webResource.accept("application/json").get(
					ClientResponse.class);
			String stringResponse=clientResponse.getEntity(String.class);
			/*CheckVO checker=clientResponse.getEntity(CheckVO.class);
			System.out.println(checker.getReleaseDate());*/
			/*System.out.println(stringResponse);*/
			Gson gsonObj=new Gson();
			//gsonObj.
			
			JSONArray jsonArray;
			
				jsonArray = new JSONArray( stringResponse );
				for(int i=0;i<jsonArray.length();i++){
				
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			
			//System.out.println(jsonObject.get("released"));
			if(jsonObject.has("userReleaseDate")){
				TesterDBNew testerDate=new TesterDBNew();
				Date sourceDate=dateFormat.parse(jsonObject.get("userReleaseDate").toString());
				if(testerDate.dateChecker(sourceDate)<1){
					System.out.println("start");
					testerDate.getVOS(applicationName, jsonObject.get("name").toString());
					/*System.out.println("Application is is :"+ applicationName);
					System.out.println("release is :"+ jsonObject.get("name"));
			System.out.println("User release Date is:"+jsonObject.get("userReleaseDate"));*/
			System.out.println("stop");
				}
			}
				}
				
			//log.debug(" to check the issues :"+jsonArray);
			/*JSONArray issueArray = new JSONArray(jsonArray.getJSONObject(0)
					.getString("releaseDate"));*/
				
				/*CheckVO checker=gsonObj.fromJson(jsonArray.getString(0),CheckVO.class);
				System.out.println(checker.getReleaseDate());*/
			//System.out.println(issueArray.get(0));
			
			
			/*MultivaluedMap< String, String> maps=clientResponse.getMetadata();*/
			/*MultivaluedMap< String, String> maps=clientResponse.getHeaders();
			
			Set<String> keys=maps.keySet();
			for(String s:keys){  
				System.out.println(s);
				System.out.println();
				System.out.println();
					System.out.println(maps.getFirst(s));
			   }
			
			*/
			
				
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
			
			/*
			// TODO Auto-generated method stub

			MongoClient clientDb=new MongoClient("172.23.16.28", 27017);
			
			  DB db = clientDb.getDB("sqmdb");
				 System.out.println("Connect to database successfully");
				 
				 
				 DBCursor cursor = null;
					try {
						DBCollection table = db.getCollection("alm");
						BasicDBObject searchQuery = new BasicDBObject();
						searchQuery.put("userid", "user1");
						searchQuery.put("domain", "posttrade");
						searchQuery.put("projects", "CFPR");
						searchQuery.put("release", "cfprRelease2");
						cursor = table.find(searchQuery);
						while (cursor.hasNext()) {
							DBObject report =cursor.next();
							 DBObject report =(BasicDBObject) cursor.next().get("manual");
							 String passed=report.get("passed").toString();
							 System.out.println(passed);
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
							System.out.println(severityVO.toString());
							System.out.println(statusVO.toString());
							System.out.println(testCaseVO.toString());
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
				*/
		
	}
	
	
	public  int dateChecker(Date sourceDate) throws Exception{
		
		Date date = new Date();
		Date systemDate=dateFormat.parse(dateFormat.format(date));
		
		return systemDate.compareTo(sourceDate);
	}
	
	public void getVOS(String applicationName, String releaseName) throws Exception{
		
		
		String 	finalUrl = "https://issuetracking.jpmchase.net/jira15/rest/api/latest/search?jql=project%20="+ URLEncoder.encode(applicationName, "UTF-8")
			+ "%20AND%20fixVersion%20=%20"
			+ ""
			+ "'"+URLEncoder.encode(releaseName, "UTF-8")+"'";
		
		//String finalUrl="https://issuetracking.jpmchase.net/jira15/rest/api/latest/search?jql=project%20=ACCESSFX%20AND%20fixVersion%20=%20%27TRADE+PROCESSING%3A+%28Paymentech+Ph1%29+Integration+with+Paymentech+to+internalize+FX+currently+handled+by+MC%2FVisa%27";
//		System.out.println(url);

		Client client=Client.create();
		System.out.println(finalUrl);
		WebResource webResource=client.resource(finalUrl);
		ClientResponse clientResponse=webResource.accept("application/json").get(
				ClientResponse.class);
		String stringResponse=clientResponse.getEntity(String.class);
		/*CheckVO checker=clientResponse.getEntity(CheckVO.class);
		System.out.println(checker.getReleaseDate());*/
		/*System.out.println(stringResponse);*/
		Gson gsonObj=new Gson();
		//gsonObj.
		JiraSchedulerVO jiraScVO=new JiraSchedulerVO();
		List<JiraIdVO> jiraIdList=new ArrayList<JiraIdVO>();
		List<EffortJiraVO> effortJiraList=new ArrayList<EffortJiraVO>();
		jiraScVO.setProject(applicationName);
		jiraScVO.setRelease(releaseName);
		JSONArray jsonArray;
		JSONObject obj=null;
			jsonArray = new JSONArray("["+ stringResponse+"]" );
			
			JSONArray issueArray = new JSONArray(jsonArray.getJSONObject(0)
					.getString("issues"));
			
			if (issueArray.length() > 0) {
				int sqmCount=0;
				int nonSqmCount=0;
				EffortJiraVO effortsJiraVo=new EffortJiraVO();
				for (int i = 0; i < issueArray.length(); i++) {
					
					JiraIdVO jiraIds=new JiraIdVO();
					
					String ids	= issueArray.getJSONObject(i).getString("key");
					System.out.println(ids);
					
					
					/*jiraIds.setEnv("SIT");*/
					//System.out.println(ids);
					obj = new JSONObject(issueArray.getJSONObject(i).getString(
							"fields"));
					String sourceStatus=getSourceStatus(obj);
					String aliasStatus=getAliasStatus(sourceStatus);
					jiraIds.setJiraid(ids);
					jiraIds.setEnv(aliasStatus);
					if(getSqmEfforts(obj.get("labels")).toString().equalsIgnoreCase("sqm")){
						sqmCount++;
					}else{
						nonSqmCount++;
					}
					
					jiraIdList.add(jiraIds);
				}
				effortsJiraVo.setSqm(sqmCount);
				effortsJiraVo.setNonsqm(nonSqmCount);
				/*System.out.println(obj.get("labels"));
				System.out.println(obj.get("progress"));*/
				effortJiraList.add(effortsJiraVo);
				jiraScVO.setEffort(effortJiraList);
				jiraScVO.setJiraids(jiraIdList);
				System.out.println(jiraIdList.get(0).getEnv());
				insertDBObject(jiraScVO);
			}
//			 System.out.println(jiraIdList.get(0).getJiraid());
			
			
	
	}
	
public static void insertDBObject(JiraSchedulerVO jiraScVO) throws Exception{

DBCollection collection=getConnection();
TesterDBNew testDb=new TesterDBNew();
Date systemDate=testDb.getCurrentDate();

        BasicDBObject jiracollection = new BasicDBObject();
        jiracollection.put("project", jiraScVO.getProject());
        jiracollection.put("release", jiraScVO.getRelease());
        jiracollection.put("docCreatedBy", "System");
        jiracollection.put("docCreatedDate",systemDate );
        	jiracollection.put("docUpdatedBy", "System");
        jiracollection.put("docCreatedDate",systemDate );
        List<EffortJiraVO> effortList =jiraScVO.getEffort();
      
        List<BasicDBObject> effort = new ArrayList<BasicDBObject>();
        
        for(EffortJiraVO effortVo:effortList){
        	effort.add(new BasicDBObject("sqm", effortVo.getSqm()));
        	effort.add(new BasicDBObject("non-sqm", effortVo.getNonsqm()));
     	   
        }
        
       /* effort.add(new BasicDBObject("SQM", "Testcaseexec,TestcaseRel,others"));
        effort.add(new BasicDBObject("NON-SQM", "Testcaseexec,TestcaseRel,others"));
        */
       List<JiraIdVO> jiras =jiraScVO.getJiraids();
       List<BasicDBObject> jiraDbObj = new ArrayList<BasicDBObject>();
       
       for(JiraIdVO jiraVo:jiras){
    	   jiraDbObj.add(new BasicDBObject("jiraId", jiraVo.getJiraid()));
    	   jiraDbObj.add(new BasicDBObject("env", jiraVo.getEnv()));
    	   
       }

 //System.out.println(jiras.get(0).getJiraid());
        
       
       /* jiras.add(jiraScVO.getJiraids());*/
       /* jiras.add(new BasicDBObject("environment", "UAT"));
        
        jiras.add(new BasicDBObject("jira-id", "1614"));
        jiras.add(new BasicDBObject("environment", "SIT"));
        
        jiras.add(new BasicDBObject("jira-id", "1618"));
        jiras.add(new BasicDBObject("environment", "SIT"));*/
        
        
       /* List<BasicDBObject> jiras = new ArrayList<BasicDBObject>();
        jiras.add(new BasicDBObject().putAll);*/
        
        jiracollection.put("effort", effort);
        jiracollection.put("jira", jiraDbObj);
        
        
        collection.insert(jiracollection);
       
    }

public static String  getId(String sourceString){
	
int index=	sourceString.lastIndexOf('-');

	
	return sourceString.substring(index+1);
}

public static String  getSqmEfforts(Object sourceObject){
	
if(sourceObject==null){
	return "non-sqm";
}else{
	return "sqm";
}

	
	
}

public  Date getCurrentDate() throws Exception{
	
	Date date = new Date();
	Date systemDate=dateFormat.parse(dateFormat.format(date));
	return systemDate;
	
}

public static String getSourceStatus(JSONObject obj) throws Exception{
	
	JSONObject obj2=null;
	JSONObject obj3=null;
	JSONObject obj4=null;

if(obj.has("parent")){
     
     
     obj2 = obj.getJSONObject("parent");
     
     if(obj2.has("fields")){
     obj3 = obj2.getJSONObject("fields");
     obj4 = obj3.getJSONObject("status");
    /* System.out.println(obj4.get("name"));*/
     return obj4.get("name").toString().replace(" ", "");
     }
     else{
     obj4 = obj2.getJSONObject("status");
         /*System.out.println(obj4.get("name"));*/
      return obj4.get("name").toString().replace(" ", "");
     
     }
     
     }else
     {
     
         obj4 = obj.getJSONObject("status");
         /*System.out.println(obj4.get("name"));*/
         return obj4.get("name").toString().replace(" ", "");
          
     }

	
}

public static String getAliasStatus(String sourceString){
	
	if(sourceString.equalsIgnoreCase("open")||sourceString.equalsIgnoreCase("indev")||sourceString.equalsIgnoreCase("reopened")||sourceString.equalsIgnoreCase("inprogress")){
		
		return "Dev";
		
	}else if(sourceString.equalsIgnoreCase("onhold")||sourceString.equalsIgnoreCase("inanalysis")){
		return "SIT";
	}else{
		return "SIT-SIGNOff";
	}
}



}
