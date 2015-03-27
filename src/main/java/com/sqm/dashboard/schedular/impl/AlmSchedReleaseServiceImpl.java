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

import com.sqm.dashboard.VO.AlmReleaseCycleDetails;
import com.sqm.dashboard.VO.AlmReleaseDetails;
import com.sqm.dashboard.VO.SchedularReleaseCyclesVO;
import com.sqm.dashboard.VO.SchedularReleaseDefectsVO;
import com.sqm.dashboard.schedular.AlmSchedReleaseService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedReleaseServiceImpl")
public class AlmSchedReleaseServiceImpl implements AlmSchedReleaseService {
	
	final Logger log = Logger.getLogger(AlmSchedReleaseServiceImpl.class);
	
	@Override
	public ArrayList<AlmReleaseDetails> getAlmReleasesDetails(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception {
		
  		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  		Date date = new Date();
	    String currentDate = dateFormat.format(date);

	    ArrayList<AlmReleaseDetails> releaseDetails = getAlmReleaseDetails(conn, requestHeaders, releasesUrl, currentDate);
	    
	    return releaseDetails;
	}
	
	@Override
	public ArrayList<AlmReleaseCycleDetails> getAlmReleaseCyclesDetails(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseCyclesUrl, String releaseId) throws Exception {

  		ArrayList<AlmReleaseCycleDetails> releaseCycleDetails = getAlmReleasesCycleDetails(conn, requestHeaders, releaseCyclesUrl, releaseId);
	    return releaseCycleDetails;

	}
	
	@Override
	public SchedularReleaseCyclesVO getAlmReleaseCyclesData(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseCyclesUrl, String releaseId) throws Exception {

		SchedularReleaseCyclesVO releaseCyclesData = getAlmReleaseCycleData(conn, requestHeaders, releaseCyclesUrl, releaseId);
	    return releaseCyclesData;

	}
	
	@Override
	public SchedularReleaseDefectsVO getAlmReleaseDefectsData(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
  		SchedularReleaseDefectsVO schedReleaseDefectsVO = getAlmReleasesDefectsData(conn, requestHeaders, releaseDefectsUrl, releaseId);
	    return schedReleaseDefectsVO;
	    
	}
  	
	@Override
  	public ArrayList<String> getAlmReleaseDefectIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
  		ArrayList<String> relDefectIds = getAlmReleasesDefectIds(conn, requestHeaders, releaseDefectsUrl, releaseId);
	    return relDefectIds;

  	}
  	
	@Override
  	public ArrayList<String> getAlmReleaseJiraIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
  		ArrayList<String> relJiraIds = getAlmReleasesJiraIds(conn, requestHeaders, releaseDefectsUrl, releaseId);
	    return relJiraIds;

  	}

  	public ArrayList<AlmReleaseDetails> getAlmReleaseDetails(RestConnectorUtility conn, Map<String, String> requestHeaders,  String releasesUrl, String currentDate) throws Exception {
		
		StringBuilder queryAlmReleasesDetails = new StringBuilder();
		ArrayList<AlmReleaseDetails> almReleaseDetails = new ArrayList<AlmReleaseDetails>();

		Node nNode = null;
		Element eElement = null;
		
		ArrayList<String> releaseIds = new ArrayList<String>();
		ArrayList<String> releaseNames = new ArrayList<String>();
		ArrayList<String> relStartDates = new ArrayList<String>();
		ArrayList<String> relEndDates = new ArrayList<String>();
		
		try{
			queryAlmReleasesDetails.append("query={end-date[");
			queryAlmReleasesDetails.append(">=");
			queryAlmReleasesDetails.append(currentDate);
			queryAlmReleasesDetails.append("]");
			queryAlmReleasesDetails.append("}");
			queryAlmReleasesDetails.append("&fields=id,name,start-date,end-date");

			log.info("AlmReleases Query : " + queryAlmReleasesDetails);
			
			String listFromReleaseDetailsCollectionAsXml = conn.httpGet(releasesUrl, queryAlmReleasesDetails.toString(), requestHeaders).toString();
			log.info("listFromReleasesCollectionAsXml : " + listFromReleaseDetailsCollectionAsXml);
		
			NodeList nList = getNodeList(listFromReleaseDetailsCollectionAsXml);
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						releaseIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						releaseNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("start-date")) {
						relStartDates.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("end-date")) {
						relEndDates.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			for(int i=0; i<releaseIds.size(); i++) {
				almReleaseDetails.add(new AlmReleaseDetails(releaseIds.get(i), releaseNames.get(i), relStartDates.get(i), relEndDates.get(i)));
			}
			
			return almReleaseDetails;
			
		} catch (Exception e) {
				log.error("Error in getting Alm active releases : " + e.getMessage());
				throw e;
		}
	}

	public ArrayList<AlmReleaseCycleDetails> getAlmReleasesCycleDetails(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseCyclesUrl, String releaseId) throws Exception {
		
		StringBuilder queryAlmRelCycleDetails = new StringBuilder();
		ArrayList<AlmReleaseCycleDetails> almReleaseCycleDetails = new ArrayList<AlmReleaseCycleDetails>();
		
		Node nNode = null;
		Element eElement = null;
		
		ArrayList<String> cycleIds = new ArrayList<String>();
		ArrayList<String> cycleNames = new ArrayList<String>();
		ArrayList<String> cycleStartDates = new ArrayList<String>();
		ArrayList<String> cycleEndDates = new ArrayList<String>();

		try{
			queryAlmRelCycleDetails.append("query={parent-id[");
			queryAlmRelCycleDetails.append(releaseId);
			queryAlmRelCycleDetails.append("]}");
			queryAlmRelCycleDetails.append("&fields=id,name,start-date,end-date");

			log.info("AlmReleaseCycleDetails Query : " + queryAlmRelCycleDetails);

			String listFromReleaseCycleDetailsCollectionAsXml = conn.httpGet(releaseCyclesUrl, queryAlmRelCycleDetails.toString(), requestHeaders).toString();
			log.info("listFromReleaseCycleDetailsCollectionAsXml : " + listFromReleaseCycleDetailsCollectionAsXml);

			NodeList nList = getNodeList(listFromReleaseCycleDetailsCollectionAsXml);
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						cycleIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						cycleNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("start-date")) {
						cycleStartDates.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("end-date")) {
						cycleEndDates.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			for(int i=0; i<cycleIds.size(); i++) {
				almReleaseCycleDetails.add(new AlmReleaseCycleDetails(cycleIds.get(i), cycleNames.get(i), cycleStartDates.get(i), cycleEndDates.get(i)));
			}
			
			return almReleaseCycleDetails;
			
		} catch (Exception e) {
				log.error("Error in getting Alm Releases Cycle Details : " + e.getMessage());
				throw e;
		}
	}
	
	public SchedularReleaseCyclesVO getAlmReleaseCycleData(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseCyclesUrl, String releaseId) throws Exception {
		
		StringBuilder queryAlmRelCycleDetails = new StringBuilder();
		
		Node nNode = null;
		Element eElement = null;
		
		SchedularReleaseCyclesVO schedReleaseCyclesVO = new SchedularReleaseCyclesVO();
		
		ArrayList<String> cycleIds = new ArrayList<String>();
		ArrayList<String> cycleNames = new ArrayList<String>();
		ArrayList<String> cycleStartDates = new ArrayList<String>();
		ArrayList<String> cycleEndDates = new ArrayList<String>();

		try{
			queryAlmRelCycleDetails.append("query={parent-id[");
			queryAlmRelCycleDetails.append(releaseId);
			queryAlmRelCycleDetails.append("]}");
			queryAlmRelCycleDetails.append("&fields=id,name,start-date,end-date");

			log.info("AlmReleaseCycleDetails Query : " + queryAlmRelCycleDetails);

			String listFromReleaseCycleDetailsCollectionAsXml = conn.httpGet(releaseCyclesUrl, queryAlmRelCycleDetails.toString(), requestHeaders).toString();
			log.info("listFromReleaseCycleDetailsCollectionAsXml : " + listFromReleaseCycleDetailsCollectionAsXml);
			
			NodeList nList = getNodeList(listFromReleaseCycleDetailsCollectionAsXml);
					
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						cycleIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						cycleNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("start-date")) {
						cycleStartDates.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("end-date")) {
						cycleEndDates.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			schedReleaseCyclesVO.setCycleId(cycleIds);
			schedReleaseCyclesVO.setCycleName(cycleNames);
			schedReleaseCyclesVO.setCycleStartDate(cycleStartDates);
			schedReleaseCyclesVO.setCycleEndDate(cycleEndDates);
			
			return schedReleaseCyclesVO;
			
		} catch (Exception e) {
				log.error("Error in getting Alm Releases Cycle Data : " + e.getMessage());
				throw e;
		}
	}

	public ArrayList<String> getAlmReleasesCycleNames(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseCyclesUrl, String releaseId) throws Exception {
		
		StringBuilder queryAlmReleaseCycleNames = new StringBuilder();
		ArrayList<String> releaseCycleNames = new ArrayList<String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmReleaseCycleNames.append("query={parent-id[");
			queryAlmReleaseCycleNames.append(releaseId);
			queryAlmReleaseCycleNames.append("]}");
			queryAlmReleaseCycleNames.append("&fields=id,name,start-date,end-date");

			log.info("AlmReleaseCycleNames Query : " + queryAlmReleaseCycleNames);

			String listFromReleaseCyclesCollectionAsXml = conn.httpGet(releaseCyclesUrl, queryAlmReleaseCycleNames.toString(), requestHeaders).toString();
			log.info("listFromReleaseCyclesCollectionAsXml : " + listFromReleaseCyclesCollectionAsXml);
			
			NodeList nList = getNodeList(listFromReleaseCyclesCollectionAsXml);
					
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
			
			NodeList nList = getNodeList(listFromReleaseDefectsCollectionAsXml);
					
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
	
	public ArrayList<String> getAlmReleasesDefectIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
		StringBuilder queryAlmReleaseDefectIds = new StringBuilder();
		ArrayList<String> defectIds = new ArrayList<String>();

		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmReleaseDefectIds.append("query={detected-in-rel[");
			queryAlmReleaseDefectIds.append(releaseId);
			queryAlmReleaseDefectIds.append("]}");
			queryAlmReleaseDefectIds.append("&fields=id");

			log.info("AlmReleaseDefectIds Query : " + queryAlmReleaseDefectIds);

			String listFromReleaseDefectIdsCollectionAsXml = conn.httpGet(releaseDefectsUrl, queryAlmReleaseDefectIds.toString(), requestHeaders).toString();
			log.info("listFromReleaseDefectIdsCollectionAsXml : " + listFromReleaseDefectIdsCollectionAsXml);

			NodeList nList = getNodeList(listFromReleaseDefectIdsCollectionAsXml);
					
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						defectIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			return defectIds;
			
		} catch (Exception e) {
				log.error("Error in getting Alm Release Defects Ids : " + e.getMessage());
				throw e;
		}
	}
	
	public ArrayList<String> getAlmReleasesJiraIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
		StringBuilder queryAlmReleaseJiraIds = new StringBuilder();
		ArrayList<String> jiraIds = new ArrayList<String>();

		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmReleaseJiraIds.append("query={detected-in-rel[");
			queryAlmReleaseJiraIds.append(releaseId);
			queryAlmReleaseJiraIds.append("]}");
			queryAlmReleaseJiraIds.append("&fields=user-20");

			log.info("AlmReleaseJiraIds Query : " + queryAlmReleaseJiraIds);

			String listFromReleaseJiraIdsCollectionAsXml = conn.httpGet(releaseDefectsUrl, queryAlmReleaseJiraIds.toString(), requestHeaders).toString();
			log.info("listFromReleaseJiraIdsCollectionAsXml : " + listFromReleaseJiraIdsCollectionAsXml);

			NodeList nList = getNodeList(listFromReleaseJiraIdsCollectionAsXml);
					
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("user-20")) {
						if(eElement.hasChildNodes() && (eElement.getElementsByTagName("Value").item(0).getTextContent()) != "") {
							jiraIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
						}
					}
				}
			}
			return jiraIds;
		} catch (Exception e) {
				log.error("Error in getting Alm Release Jira Ids : " + e.getMessage());
				throw e;
		}
	}

	public NodeList getNodeList(String inputXml) throws Exception {
	
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(inputXml));

		Document doc = db.parse(is);
		doc.getDocumentElement().normalize();
	
		NodeList nList = doc.getElementsByTagName("Field");
		
		return nList;
	}
}