package com.sqm.dashboard.schedular.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import com.sqm.dashboard.schedular.DashboardProjectService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("dbProjectServiceImpl")
public class DashboardProjectServiceImpl implements DashboardProjectService {

	static final Log log = LogFactory.getLog(DashboardProjectServiceImpl.class);
	
	/*@Autowired*/
	DashboardAuthServiceImpl dbAuthServiceImpl = new DashboardAuthServiceImpl();
	
	/*@Value("$almBasicUrl")private String almBasicUrl;
	@Value("$almDomains")private String almDomains;*/
	
	/*private String almBasicUrl = "http://ealm11.jpmchase.net/qcbin/rest/";
	private String almDomains = "domains";*/
	
	@SuppressWarnings("unused")
	@Override
	public HashMap<String,String> getAlmProjects(RestConnectorUtility connection, Map<String, String> requestHeaders, String domainName) throws Exception {

		HashMap<String,String> jsonProjects=new HashMap<String, String>();
		//HashMap<String,String> dummy=new HashMap<String, String>();
		
		String projectsUrl = "http://ealm11.jpmchase.net/qcbin/rest/" + "domains" + "/" + domainName + "/" + "projects";
		//String projectsUrl = almBasicUrl + almDomains + "/" + domainName + "/" + almProjects;
		log.info("projectsUrl : " + projectsUrl);
		
		String listOfProjects;
		try{
			
			listOfProjects = connection.httpGet(projectsUrl, null, requestHeaders).toString();
			log.info("Alm Projects : " + listOfProjects);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(listOfProjects)));
			Node nodes = document.getDocumentElement();
			String root = nodes.getNodeName();
			log.info("Projects XML Root Node: " + root);
			
			NodeList nodeList = document.getElementsByTagName("Project");
			
			ArrayList<String> projectsList = new ArrayList<String>();
			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				Node node = nodeList.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					projectsList.add(eElement.getAttribute("Name"));
					log.info("Project name : " + eElement.getAttribute("Name"));
				}
			}
			log.info("List of Projects : " + projectsList);
			
			if(projectsList != null){
				jsonProjects = new HashMap<String,String>();

				int i = 1;
				for (@SuppressWarnings("rawtypes")
				Iterator itr = projectsList.iterator(); itr.hasNext();) {
					String projectName = (String) itr.next();
					log.info("Project name : " + projectName);
					jsonProjects.put("project" + i, projectName);
					i++;
				}
				log.info("jsonProjects : " + jsonProjects.toString());
				return jsonProjects;
			} else {
				jsonProjects.put("project", "No Projects");
				return jsonProjects;
			} 
		}catch (Exception e) {
			log.error("Error in getting listOfProjects ", e);
			throw e;
		} finally {
			//dbAuthServiceImpl.logout(connection);
		}
	}
	
	

}
