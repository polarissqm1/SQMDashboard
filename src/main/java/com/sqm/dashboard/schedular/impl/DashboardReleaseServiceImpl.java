package com.sqm.dashboard.schedular.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import com.sqm.dashboard.schedular.DashboardReleaseService;
import com.sqm.dashboard.util.EntitiesUtility;
import com.sqm.dashboard.util.MarshallingUtility;
import com.sqm.dashboard.util.RestConnectorUtility;
import com.sqm.dashboard.VO.AlmReleaseVO;

@Service("dbReleaseServiceImpl")
public class DashboardReleaseServiceImpl implements DashboardReleaseService{	
	static final Log log = LogFactory.getLog(DashboardReleaseServiceImpl.class);
	
	/*@Autowired
	DashboardReleaseServiceImpl dbReleaseServiceImpl;*/

	//DashboardReleaseServiceImpl dbReleaseServiceImpl = new DashboardReleaseServiceImpl();
	
	private String currentDate;
	
  	@Override
	public List<String> getAlmReleasesIds(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception {
		
  		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  		Date date = new Date();
	    String currentDate = dateFormat.format(date);

	    List<String> releaseIds = getAlmReleaseIds(conn, requestHeaders, releasesUrl, currentDate);
	    
	    return releaseIds;
	}
	
  	@Override
	public List<String> getAlmReleasesNames(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception {
		
  		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  		Date date = new Date();
	    String currentDate = dateFormat.format(date);

	    List<String> releaseNames = getAlmReleaseNames(conn, requestHeaders, releasesUrl, currentDate);
	    
	    return releaseNames;
	}
  	
	static List<String> getAlmReleaseIds(RestConnectorUtility conn, Map<String, String> requestHeaders,  String releasesUrl, String currentDate) throws Exception {
		
		StringBuilder queryAlmReleases = new StringBuilder();
		List<String> releaseIds = new ArrayList<String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmReleases.append("query={end-date[");
			queryAlmReleases.append(">");
			queryAlmReleases.append(currentDate);
			queryAlmReleases.append("]");
			queryAlmReleases.append("}");
			queryAlmReleases.append("&fields=id,name,start-date,end-date");

			log.info("AlmReleases Query : " + queryAlmReleases);

			String listFromReleasesCollectionAsXml = conn.httpGet(releasesUrl, queryAlmReleases.toString(), requestHeaders).toString();
			log.info("listFromReleasesCollectionAsXml : " + listFromReleasesCollectionAsXml);
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(listFromReleasesCollectionAsXml));

			Document doc = db.parse(is);
			
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("Field");
			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						releaseIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					} 
				}
			}
			
			return releaseIds;
			
		} catch (Exception e) {
				log.error("Error in getting active releases : " + e.getMessage());
				throw e;
		}
	}

	static List<String> getAlmReleaseNames(RestConnectorUtility conn, Map<String, String> requestHeaders,  String releasesUrl, String currentDate) throws Exception {
		
		StringBuilder queryAlmReleases = new StringBuilder();
		List<String> releaseNames = new ArrayList<String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmReleases.append("query={end-date[");
			queryAlmReleases.append(">");
			queryAlmReleases.append(currentDate);
			queryAlmReleases.append("]");
			queryAlmReleases.append("}");
			queryAlmReleases.append("&fields=id,name,start-date,end-date");

			log.info("AlmReleases Query : " + queryAlmReleases);

			String listFromReleasesCollectionAsXml = conn.httpGet(releasesUrl, queryAlmReleases.toString(), requestHeaders).toString();
			log.info("listFromReleasesCollectionAsXml : " + listFromReleasesCollectionAsXml);
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(listFromReleasesCollectionAsXml));

			Document doc = db.parse(is);
			
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("Field");
			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						releaseNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			return releaseNames;
			
		} catch (Exception e) {
				log.error("Error in getting active releases : " + e.getMessage());
				throw e;
		}
	}
}