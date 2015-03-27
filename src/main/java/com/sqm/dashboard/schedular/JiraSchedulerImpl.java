package com.sqm.dashboard.schedular;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.EffortJiraVO;
import com.sqm.dashboard.VO.JiraIdVO;
import com.sqm.dashboard.VO.JiraSchedulerVO;
import com.sqm.dashboard.dao.impl.DashboardDAOImpl;
import com.sqm.dashboard.dao.impl.JiraSchedulerDAOImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service("jiraSchedulerImpl")
public class JiraSchedulerImpl implements JiraScheduler {

	final Logger log = Logger.getLogger(JiraSchedulerImpl.class);
	
	private DateFormat dateFormat;
	
	public DateFormat getDateFormat() {
		return new SimpleDateFormat("dd/MMM/yy");
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Autowired
	private JiraSchedulerDAOImpl jiraSchedulerDAOImpl;

	@Autowired
	private JiraSchedulerVO jiraSchedulerVO;

	@Autowired
	private JiraIdVO jiraIdVO;
	
	@Autowired
	private EffortJiraVO effortJiraVO;
	
	@Value("${jiraProjectUrl}")
	private String jiraProjectUrl;
	
	@Value("${jiraVersions}")
	private String jiraVersions;
	
	@Value("${jiraUrl}")
	private String jiraUrl;

	@Value("${jiraCollectionJira}")
	private String jiraCollectionJira;
	
	@Override
	public void startJiraInsert(JiraSchedulerImpl jiraSchedulerImpl) throws Exception {
		try {
			DBCollection collection=DashboardDAOImpl.getDbCollection(jiraCollectionJira);
			jiraSchedulerImpl.startProjectInsertion(jiraSchedulerImpl, collection);
			System.out.println("Task Done");
		} catch (Exception e) {
			log.error("Exception in start Insertion");
			throw e;
		}

	}

	public void startProjectInsertion(JiraSchedulerImpl jiraSchedulerImpl, DBCollection collection) throws Exception {

		try {
			//String jiraProjectUrl = "https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/";
			log.info("Hitting Url at project level: " + jiraProjectUrl);
			
			Client client = Client.create();
			WebResource webResource = client.resource(jiraProjectUrl);
			ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
			String stringResponse = clientResponse.getEntity(String.class);
			JSONArray jsonArray;
			jsonArray = new JSONArray(stringResponse);
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(jsonObject.get("key").toString().contains("CFP") || jsonObject.get("key").toString().contains("CFT")){
					System.out.println(jsonObject.get("key").toString());
					jiraSchedulerImpl.startReleaseInsertion(jsonObject.get("key").toString(), jiraSchedulerImpl, collection);
				}
				else{
					System.out.println("Not CFP");
				}
			}
		} catch (Exception e) {
			log.error("Exception in project level Jira Insertion");
			throw e;
		}
	}

	public void startReleaseInsertion(String sourceProject, JiraSchedulerImpl jiraSchedulerImpl, DBCollection collection) throws Exception {

		try {
			
			/*String releaseUrl = "https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/" + URLEncoder.encode(sourceProject, "UTF-8") + "/versions/";*/
			String releaseUrl = jiraProjectUrl + URLEncoder.encode(sourceProject, "UTF-8") + "/" + jiraVersions + "/";
			log.info("Hitting Url at release level: " + releaseUrl);
			
			Client client = Client.create();
			WebResource webResource = client.resource(releaseUrl);
			ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
			String stringResponse = clientResponse.getEntity(String.class);
			JSONArray jsonArray;
			jsonArray = new JSONArray(stringResponse);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.has("userReleaseDate")) {
					Date sourceDate = getDateFormat().parse(jsonObject.get("userReleaseDate").toString());
					if (jiraSchedulerImpl.dateChecker(sourceDate) < 1) {
						jiraSchedulerImpl.startJiraDataInsertion(sourceProject, jsonObject.get("name").toString(), jiraSchedulerImpl, collection);
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception in Release Level Jira Insertion");
			throw e;
		}

	}

	@SuppressWarnings("static-access")
	public void startJiraDataInsertion(String sourceProject, String sourceRelease, JiraSchedulerImpl jiraSchedulerImpl, DBCollection collection) throws Exception {

		try {
			List<JiraIdVO> jiraIdList = new ArrayList<JiraIdVO>();
			List<EffortJiraVO> effortJiraList = new ArrayList<EffortJiraVO>();
			/*String finalUrl = "https://issuetracking.jpmchase.net/jira15/rest/api/latest/search?jql=project%20="
					  			+ URLEncoder.encode(sourceProject, "UTF-8") + "%20AND%20fixVersion%20=%20" + "" + "'"+URLEncoder.encode(sourceRelease, "UTF-8")+"'";*/
			
			String finalUrl = jiraUrl + URLEncoder.encode(sourceProject, "UTF-8") + "%20AND%20fixVersion%20=%20" + "" + "'"+URLEncoder.encode(sourceRelease, "UTF-8")+"'";
			
			log.info("Hitting Url at jiraData level: " + finalUrl);
			Client client = Client.create();
			WebResource webResource = client.resource(finalUrl);
			ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
			String stringResponse = clientResponse.getEntity(String.class);
			jiraSchedulerVO.setProject(sourceProject);
			jiraSchedulerVO.setRelease(sourceRelease);
			
			JSONArray jsonArray;
			JSONObject obj = new JSONObject();
			
			jsonArray = new JSONArray("[" + stringResponse + "]");
			JSONArray issueArray = new JSONArray(jsonArray.getJSONObject(0).getString("issues"));
			if (issueArray.length() > 0) {
				int sqmCount = 0;
				int nonSqmCount = 0;
				for (int i = 0; i < issueArray.length(); i++) {
					String ids = issueArray.getJSONObject(i).getString("key");
					System.out.println(ids);
					obj = new JSONObject(issueArray.getJSONObject(i).getString("fields"));
					String sourceStatus = jiraSchedulerImpl.getSourceStatus(obj);
					String aliasStatus = jiraSchedulerImpl.getAliasStatus(sourceStatus);
					jiraIdVO.setJiraid(ids);
					jiraIdVO.setEnv(aliasStatus);
					if (jiraSchedulerImpl.getSqmEfforts(obj.get("labels")).toString().equalsIgnoreCase("sqm")) {
						sqmCount++;
					} else {
						nonSqmCount++;
					}
					jiraIdList.add(jiraIdVO);
				}
				
				effortJiraVO.setSqm(sqmCount);
				effortJiraVO.setNonsqm(nonSqmCount);
				effortJiraList.add(effortJiraVO);
				
				jiraSchedulerVO.setEffort(effortJiraList);
				jiraSchedulerVO.setJiraids(jiraIdList);
				System.out.println(jiraIdList.get(0).getEnv());
				jiraSchedulerDAOImpl.validatorInsertion(jiraSchedulerVO, collection);
			}
		} catch (Exception e) {
			log.error("Exception at Jira data Level");
			throw e;
		}
	}

	public int dateChecker(Date sourceDate) throws Exception {
		Date date = new Date();
		Date systemDate = getDateFormat().parse(getDateFormat().format(date));
		return systemDate.compareTo(sourceDate);
	}

	public static String getSourceStatus(JSONObject obj) throws Exception {
		JSONObject obj2 = null;
		JSONObject obj3 = null;
		JSONObject obj4 = null;
		if (obj.has("parent")) {
			obj2 = obj.getJSONObject("parent");
			if (obj2.has("fields")) {
				obj3 = obj2.getJSONObject("fields");
				obj4 = obj3.getJSONObject("status");
				return obj4.get("name").toString().replace(" ", "");
			} else {
				obj4 = obj2.getJSONObject("status");
				return obj4.get("name").toString().replace(" ", "");
			}
		} else {
			obj4 = obj.getJSONObject("status");
			return obj4.get("name").toString().replace(" ", "");
		}
	}

	public static String getAliasStatus(String sourceString) {
		if (sourceString.equalsIgnoreCase("open")
				|| sourceString.equalsIgnoreCase("indev")
				|| sourceString.equalsIgnoreCase("reopened")
				|| sourceString.equalsIgnoreCase("inprogress")) {
			return "Dev";
		} else if (sourceString.equalsIgnoreCase("onhold")
				|| sourceString.equalsIgnoreCase("inanalysis")) {
			return "SIT";
		} else {
			return "SIT-SIGNOff";
		}
	}

	public static String getSqmEfforts(Object sourceObject) {
		if (sourceObject == null) {
			return "non-sqm";
		} else {
			return "sqm";
		}
	}
}
