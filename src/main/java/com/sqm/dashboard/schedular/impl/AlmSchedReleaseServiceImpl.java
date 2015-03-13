package com.sqm.dashboard.schedular.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sqm.dashboard.VO.SchedularReleaseDefectsVO;
import com.sqm.dashboard.schedular.AlmSchedReleaseService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedReleaseServiceImpl")
public class AlmSchedReleaseServiceImpl implements AlmSchedReleaseService{	
	final Logger log = Logger.getLogger(AlmSchedReleaseServiceImpl.class);
	
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
  	
  	@Override
  	public ArrayList<String> getAlmReleaseCycleNames(RestConnectorUtility conn, String releaseCyclesUrl, Map<String, String> requestHeaders, String releaseId) throws Exception {
		
  		ArrayList<String> releaseCycleNames = getAlmReleasesCycleNames(conn, requestHeaders, releaseCyclesUrl, releaseId);
	    return releaseCycleNames;
	}

  	@Override
  	public SchedularReleaseDefectsVO getAlmReleaseDefectsData(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
  		SchedularReleaseDefectsVO schedReleaseDefectsVO = getAlmReleasesDefectsData(conn, requestHeaders, releaseDefectsUrl, releaseId);
	    return schedReleaseDefectsVO;
	    
	}
  	
	public List<String> getAlmReleaseIds(RestConnectorUtility conn, Map<String, String> requestHeaders,  String releasesUrl, String currentDate) throws Exception {
		
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
				log.error("Error in getting Alm active releases : " + e.getMessage());
				throw e;
		}
	}

	public List<String> getAlmReleaseNames(RestConnectorUtility conn, Map<String, String> requestHeaders,  String releasesUrl, String currentDate) throws Exception {
		
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
				log.error("Error in getting Alm active releases : " + e.getMessage());
				throw e;
		}
	}
	
	public ArrayList<String> getAlmReleasesCycleNames(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseCyclesUrl, String releaseId) throws Exception {
		
		//http://ealm11.jpmchase.net/qcbin/rest/domains/IB_TECHNOLOGY/projects/CFT_POST_TRADE/release-cycles?query={parent-id[1326]}&fields=name,start-date,end-date
		
		StringBuilder queryAlmReleaseCycleNames = new StringBuilder();
		ArrayList<String> releaseCycleNames = new ArrayList<String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmReleaseCycleNames.append("query={parent-id[");
			queryAlmReleaseCycleNames.append(releaseId);
			queryAlmReleaseCycleNames.append("]}");
			queryAlmReleaseCycleNames.append("&fields=name,start-date,end-date");

			log.info("AlmReleaseCycleNames Query : " + queryAlmReleaseCycleNames);

			String listFromReleaseCyclesCollectionAsXml = conn.httpGet(releaseCyclesUrl, queryAlmReleaseCycleNames.toString(), requestHeaders).toString();
			log.info("listFromReleaseCyclesCollectionAsXml : " + listFromReleaseCyclesCollectionAsXml);
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(listFromReleaseCyclesCollectionAsXml));

			Document doc = db.parse(is);
			
			doc.getDocumentElement().normalize();
			log.info("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("Field");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						releaseCycleNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			return releaseCycleNames;
			
		} catch (Exception e) {
				log.error("Error in getting Alm Releases Cycle Names: " + e.getMessage());
				throw e;
		}
	}
	
	public SchedularReleaseDefectsVO getAlmReleasesDefectsData(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
		//http://ealm11.jpmchase.net/qcbin/rest/domains/IB_TECHNOLOGY/projects/CFT_POST_TRADE/defects?query={detected-in-rel[1330]}&fields=id,user-06,user-08,creation-time,closing-date,severity
		
		StringBuilder queryAlmReleaseDefects = new StringBuilder();
		List<String> defectId = new ArrayList<String>();
		List<String> defectType = new ArrayList<String>();
		List<String> defectRootCause = new ArrayList<String>();
		List<String> defectRaisedDate = new ArrayList<String>();
		List<String> defectFixedDate = new ArrayList<String>();
		List<String> defectSeverity = new ArrayList<String>();
		List<String> defectFixTime = new ArrayList<String>();
		List<String> defectStatus = new ArrayList<String>();
		
		SchedularReleaseDefectsVO schedReleaseDefectsVO = new SchedularReleaseDefectsVO();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmReleaseDefects.append("query={detected-in-rel[");
			queryAlmReleaseDefects.append(releaseId);
			queryAlmReleaseDefects.append("]}");
			queryAlmReleaseDefects.append("&fields=status,id,user-06,user-08,creation-time,closing-date,severity,actual-fix-time");

			log.info("AlmReleaseDefects Query : " + queryAlmReleaseDefects);

			String listFromReleaseDefectsCollectionAsXml = conn.httpGet(releaseDefectsUrl, queryAlmReleaseDefects.toString(), requestHeaders).toString();
			log.info("listFromReleaseDefectsCollectionAsXml : " + listFromReleaseDefectsCollectionAsXml);
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(listFromReleaseDefectsCollectionAsXml));

			Document doc = db.parse(is);
			
			doc.getDocumentElement().normalize();
			log.info("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("Field");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("status")) {
						defectStatus.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
							defectId.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("user-06")) {
							defectType.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("creation-time")) {
							defectRaisedDate.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("severity")) {
						defectSeverity.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("user-08")) {
						if(eElement.hasChildNodes()) {
							defectRootCause.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
						} else {
							defectRootCause.add("");
						}
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("closing-date")) {
						if(eElement.hasChildNodes()) {
							defectFixedDate.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
						} else {
							defectFixedDate.add("");
						}
					}
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("actual-fix-time")) {
						if(eElement.hasChildNodes()) {
							defectFixTime.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
						} else {
							defectFixTime.add("");
						}
					}
				}
			}
			
			schedReleaseDefectsVO.setDefectId(defectId);
			schedReleaseDefectsVO.setDefectType(defectType);
			schedReleaseDefectsVO.setDefectRootCause(defectRootCause);
			schedReleaseDefectsVO.setDefectRaisedDate(defectRaisedDate);
			schedReleaseDefectsVO.setDefectFixedDate(defectFixedDate);
			schedReleaseDefectsVO.setDefectSeverity(defectSeverity);
			schedReleaseDefectsVO.setDefectFixTime(defectFixTime);
			schedReleaseDefectsVO.setDefectStatus(defectStatus);
			
			return schedReleaseDefectsVO;
			
		} catch (Exception e) {
				log.error("Error in getting Alm Release Defects data : " + e.getMessage());
				throw e;
		}
	}
}
