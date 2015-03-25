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

import com.sqm.dashboard.schedular.AlmSchedDomainService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedDomainServiceImpl")
public class AlmSchedDomainServiceImpl implements AlmSchedDomainService {

	final Logger log = Logger.getLogger(AlmSchedDomainServiceImpl.class);

	@Value("${almBasicUrl}")
	private String almBasicUrl;
	
	@Value("${almDomains}")
	private String almDomains;

	public ArrayList<String> getAlmDomains(RestConnectorUtility connection, Map<String, String> requestHeaders) throws Exception {

		String domainsUrl = almBasicUrl + almDomains;
		log.info("domainsUrl : " + domainsUrl);

		String listOfDomains;
		
		try {
			listOfDomains = connection.httpGet(domainsUrl, null, requestHeaders).toString();
			log.info(" listOfDomains : " + listOfDomains);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(listOfDomains)));
			Node nodes = document.getDocumentElement();
			String root = nodes.getNodeName();
			log.info("Domains XML Root Node: " + root);

			NodeList nodeList = document.getElementsByTagName("Domain");

			ArrayList<String> domainsList = new ArrayList<String>();
			
			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				Node node = nodeList.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					domainsList.add(eElement.getAttribute("Name"));
					log.info("Domain name : " + eElement.getAttribute("Name"));
				}
			}
			log.info("Domains List : " + domainsList);
			
			return domainsList;
		} catch (Exception e) {
			log.error("Error in getting domains ", e);
			throw e;
		}
	}
}
