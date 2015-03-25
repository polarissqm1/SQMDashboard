package com.sqm.dashboard.schedular.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sqm.dashboard.schedular.AlmSchedProjectService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedProjectServiceImpl")
public class AlmSchedProjectServiceImpl implements AlmSchedProjectService {

	final Logger log = Logger.getLogger(AlmSchedProjectServiceImpl.class);
	
	@Value("${almBasicUrl}")
	private String almBasicUrl;
	
	@Value("${almDomains}")
	private String almDomains;
	
	@Value("${almProjects}")
	private String almProjects;
	
	public ArrayList<String> getAlmProjects(RestConnectorUtility connection, Map<String, String> requestHeaders, String domainName) throws Exception {

		String projectsUrl = almBasicUrl + almDomains + "/" + domainName + "/" + almProjects;
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
			return projectsList;
			
		}catch (Exception e) {
			log.error("Error in getting listOfProjects ", e);
			throw e;
		} finally {
		}
	}
}
