package com.sqm.dashboard.schedular;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.EffortJiraVO;
import com.sqm.dashboard.VO.JiraIdVO;
import com.sqm.dashboard.VO.JiraSchedulerVO;
import com.sqm.dashboard.controller.DashboardController;
import com.sqm.dashboard.dao.impl.DashboardDAOImpl;
import com.sqm.dashboard.dao.impl.JiraSchedulerDAOImpl;
import com.sqm.dashboard.util.TesterDBNew;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class JiraSchedulerImpl implements JiraScheduler {

	/*public JiraSchedulerImpl getJiraSchedule() {
		return jiraSchedule;
	}

	public void setJiraSchedule(JiraSchedulerImpl jiraSchedule) {
		this.jiraSchedule = jiraSchedule;
	}*/

	public DateFormat getDateFormat() {
		return new SimpleDateFormat("dd/MMM/yy");
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	final Logger log = Logger.getLogger(JiraSchedulerImpl.class);

	
	/*@Value("${projectUrl}" )
	private String projectUrl;
	@Value("${versionUrl}")
	private String versionUrl;
	@Value("${jiraUrl}")
	private String jiraUrl;*/

	/*@Autowired
	private JiraSchedulerImpl jiraSchedule;*/
	private DateFormat dateFormat;
	@Autowired
	private JiraSchedulerVO jiraScVOClass;
	@Autowired
	private JiraSchedulerDAOImpl jiraDao;
	
	private JiraSchedulerDAOImpl jiraDao1=new JiraSchedulerDAOImpl();

	public JiraSchedulerDAOImpl getJiraDao() {
		return jiraDao;
	}

	public void setJiraDao(JiraSchedulerDAOImpl jiraDao) {
		this.jiraDao = jiraDao;
	}

	public JiraSchedulerVO getJiraScVOClass() {
		return jiraScVOClass;
	}

	public void setJiraScVOClass(JiraSchedulerVO jiraScVOClass) {
		this.jiraScVOClass = jiraScVOClass;
	}

	public List<JiraIdVO> getJiraIdListClass() {
		return jiraIdListClass;
	}

	public void setJiraIdListClass(List<JiraIdVO> jiraIdListClass) {
		this.jiraIdListClass = jiraIdListClass;
	}

	public List<EffortJiraVO> getEffortJiraClass() {
		return effortJiraClass;
	}

	public void setEffortJiraClass(List<EffortJiraVO> effortJiraClass) {
		this.effortJiraClass = effortJiraClass;
	}

	private List<JiraIdVO> jiraIdListClass;
	private List<EffortJiraVO> effortJiraClass;

	@Override
	public void startJiraInsert(JiraSchedulerImpl jiraScheduler) throws Exception {
		try {
			jiraScheduler.startProjectInsertion(jiraScheduler);
		} catch (Exception e) {
			log.error("Exception in start Insertion");
			throw e;
		}

	}

	public void startProjectInsertion(JiraSchedulerImpl jiraScheduler) throws Exception {

		try {
			String projectUrl="https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/";
			log.info("Hiting Url at project level: " + projectUrl);
			Client client = Client.create();
			WebResource webResource = client.resource(projectUrl);
			ClientResponse clientResponse = webResource.accept(
					"application/json").get(ClientResponse.class);
			String stringResponse = clientResponse.getEntity(String.class);
			JSONArray jsonArray;
			jsonArray = new JSONArray(stringResponse);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				jiraScheduler.startReleaseInsertion(
						jsonObject.get("key").toString(),jiraScheduler);
			}
		} catch (Exception e) {
			log.error("Ecxeption in project level Jira Insertion");
			throw e;
		}
	}

	public void startReleaseInsertion(String sourceProject,JiraSchedulerImpl jiraScheduler) throws Exception {

		try {
			
			String releaseUrl=
			 "https://issuetracking.jpmchase.net/jira15/rest/api/latest/project/"
			 +URLEncoder.encode(sourceProject, "UTF-8")+"/versions/";
			 
			/*String releaseUrl = versionUrl + sourceProject + "/versions/";*/
			log.info("Hiting Url at release level: " + releaseUrl);
			Client client = Client.create();
			WebResource webResource = client.resource(releaseUrl);
			ClientResponse clientResponse = webResource.accept(
					"application/json").get(ClientResponse.class);
			String stringResponse = clientResponse.getEntity(String.class);
			JSONArray jsonArray;
			jsonArray = new JSONArray(stringResponse);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.has("userReleaseDate")) {
					//System.out.println("true");
					Date sourceDate = getDateFormat().parse(
							jsonObject.get("userReleaseDate").toString());
					if (jiraScheduler.dateChecker(sourceDate) < 1) {
						jiraScheduler.startJiraDataInsertion(sourceProject,
								jsonObject.get("name").toString(),jiraScheduler);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ecxeption in Release level Jira Insertion");
			throw e;
		}

	}

	public void startJiraDataInsertion(String sourceProject,
			String sourceRelease,JiraSchedulerImpl jiraScheduler) throws Exception {

		
		 /* String finalUrl =
		  "https://issuetracking.jpmchase.net/jira15/rest/api/latest/search?jql=project%20="
		  + URLEncoder.encode(applicationName, "UTF-8") +
		 "%20AND%20fixVersion%20=%20" + "" +
		 "'"+URLEncoder.encode(releaseName, "UTF-8")+"'";*/
		 
		try {
			/*JiraSchedulerVO jiraScVO = jiraScheduler.getJiraScVOClass();*/
			JiraSchedulerVO jiraScVO = new JiraSchedulerVO();
			/*List<JiraIdVO> jiraIdList = jiraScheduler.getJiraIdListClass();*/
			List<JiraIdVO> jiraIdList = new ArrayList<JiraIdVO>();
			/*List<EffortJiraVO> effortJiraList = jiraScheduler.getEffortJiraClass();*/
			List<EffortJiraVO> effortJiraList = new ArrayList<EffortJiraVO>();
			  String finalUrl =
					  "https://issuetracking.jpmchase.net/jira15/rest/api/latest/search?jql=project%20="
					  + URLEncoder.encode(sourceProject, "UTF-8") +
					 "%20AND%20fixVersion%20=%20" + "" +
					 "'"+URLEncoder.encode(sourceRelease, "UTF-8")+"'";
			/*String finalUrl = jiraUrl
					+ URLEncoder.encode(sourceProject, "UTF-8")
					+ "%20AND%20fixVersion%20=%20" + "" + "'"
					+ URLEncoder.encode(sourceRelease, "UTF-8") + "'";*/
			log.info("Hiting Url at jiraData level: " + finalUrl);
			Client client = Client.create();
			WebResource webResource = client.resource(finalUrl);
			ClientResponse clientResponse = webResource.accept(
					"application/json").get(ClientResponse.class);
			String stringResponse = clientResponse.getEntity(String.class);
			jiraScVO.setProject(sourceProject);
			jiraScVO.setRelease(sourceRelease);
			JSONArray jsonArray;
			JSONObject obj = new JSONObject();
			jsonArray = new JSONArray("[" + stringResponse + "]");
			JSONArray issueArray = new JSONArray(jsonArray.getJSONObject(0)
					.getString("issues"));
			if (issueArray.length() > 0) {
				int sqmCount = 0;
				int nonSqmCount = 0;
				EffortJiraVO effortsJiraVo = new EffortJiraVO();
				for (int i = 0; i < issueArray.length(); i++) {
					JiraIdVO jiraIds = new JiraIdVO();
					String ids = issueArray.getJSONObject(i).getString("key");
					System.out.println(ids);
					obj = new JSONObject(issueArray.getJSONObject(i).getString(
							"fields"));
					String sourceStatus = jiraScheduler.getSourceStatus(obj);
					String aliasStatus = jiraScheduler.getAliasStatus(sourceStatus);
					jiraIds.setJiraid(ids);
					jiraIds.setEnv(aliasStatus);
					if (jiraScheduler.getSqmEfforts(obj.get("labels")).toString()
							.equalsIgnoreCase("sqm")) {
						sqmCount++;
					} else {
						nonSqmCount++;
					}
					jiraIdList.add(jiraIds);
				}
				effortsJiraVo.setSqm(sqmCount);
				effortsJiraVo.setNonsqm(nonSqmCount);
				effortJiraList.add(effortsJiraVo);
				jiraScVO.setEffort(effortJiraList);
				jiraScVO.setJiraids(jiraIdList);
				System.out.println(jiraIdList.get(0).getEnv());
				/*jiraScheduler.getJiraDao().insertJiraData(jiraScVO);*/
				jiraDao1.insertJiraData(jiraScVO);
			}
		} catch (Exception e) {
			log.error("Ëxception at Jira data Level");
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
