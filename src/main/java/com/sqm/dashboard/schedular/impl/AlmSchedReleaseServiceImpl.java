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

import com.sqm.dashboard.VO.AlmReleaseDetails;
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
  	
  	@Override
  	public ArrayList<String> getAlmReleaseDefectIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception {
		
  		ArrayList<String> relDefectIds = getAlmReleasesDefectIds(conn, requestHeaders, releaseDefectsUrl, releaseId);
	    return relDefectIds;

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
			queryAlmReleasesDetails.append(">");
			queryAlmReleasesDetails.append(currentDate);
			queryAlmReleasesDetails.append("]");
			queryAlmReleasesDetails.append("}");
			queryAlmReleasesDetails.append("&fields=id,name,start-date,end-date");

			log.info("AlmReleases Query : " + queryAlmReleasesDetails);
			
			String listFromReleaseDetailsCollectionAsXml = conn.httpGet(releasesUrl, queryAlmReleasesDetails.toString(), requestHeaders).toString();
			log.info("listFromReleasesCollectionAsXml : " + listFromReleaseDetailsCollectionAsXml);
		
			DocumentBuilder db1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is1 = new InputSource();
			is1.setCharacterStream(new StringReader(listFromReleaseDetailsCollectionAsXml));

			Document doc1 = db1.parse(is1);
			
			doc1.getDocumentElement().normalize();
			
			NodeList nList = doc1.getElementsByTagName("Field");
			
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
			
			DocumentBuilder db2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is2 = new InputSource();
			is2.setCharacterStream(new StringReader(listFromReleasesCollectionAsXml));

			Document doc2 = db2.parse(is2);
			
			doc2.getDocumentElement().normalize();
			System.out.println("Root element :" + doc2.getDocumentElement().getNodeName());
		 
			NodeList nList = doc2.getElementsByTagName("Field");
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
			
			DocumentBuilder db3 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is3 = new InputSource();
			is3.setCharacterStream(new StringReader(listFromReleasesCollectionAsXml));

			Document doc3 = db3.parse(is3);
			
			doc3.getDocumentElement().normalize();
			System.out.println("Root element :" + doc3.getDocumentElement().getNodeName());
		 
			NodeList nList = doc3.getElementsByTagName("Field");
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
			
			DocumentBuilder db4 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is4 = new InputSource();
			is4.setCharacterStream(new StringReader(listFromReleaseCyclesCollectionAsXml));

			Document doc4 = db4.parse(is4);
			
			doc4.getDocumentElement().normalize();
			log.info("Root element :" + doc4.getDocumentElement().getNodeName());
		 
			NodeList nList = doc4.getElementsByTagName("Field");

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
			
			DocumentBuilder db5 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is5 = new InputSource();
			is5.setCharacterStream(new StringReader(listFromReleaseDefectsCollectionAsXml));

			Document doc5 = db5.parse(is5);
			
			doc5.getDocumentElement().normalize();
			log.info("Root element :" + doc5.getDocumentElement().getNodeName());
		 
			NodeList nList = doc5.getElementsByTagName("Field");

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
			
			DocumentBuilder db6 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is6 = new InputSource();
			is6.setCharacterStream(new StringReader(listFromReleaseDefectIdsCollectionAsXml));

			Document doc6 = db6.parse(is6);
			
			doc6.getDocumentElement().normalize();
			log.info("Root element :" + doc6.getDocumentElement().getNodeName());
		 
			NodeList nList = doc6.getElementsByTagName("Field");

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
}
