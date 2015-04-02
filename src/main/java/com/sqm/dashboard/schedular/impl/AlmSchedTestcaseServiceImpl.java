package com.sqm.dashboard.schedular.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

import com.sqm.dashboard.schedular.AlmSchedTestcaseService;
import com.sqm.dashboard.util.EntitiesUtility;
import com.sqm.dashboard.util.MarshallingUtility;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedTestcaseServiceImpl")
public class AlmSchedTestcaseServiceImpl implements AlmSchedTestcaseService {

	Logger log = Logger.getLogger(AlmSchedTestcaseServiceImpl.class);
	
	@Override
	public String getAlmTestSetFolderId(RestConnectorUtility conn, Map<String, String> requestHeaders, String testSetFolderUrl,
										String releaseName, String releaseId, String testLabFolderName) throws Exception {
		
		StringBuilder queryAlmTestSetId = new StringBuilder();
		String testSetFolderId = null;
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmTestSetId.append("query={name[");
			queryAlmTestSetId.append("\"");
			queryAlmTestSetId.append(testLabFolderName.replace(" ", "%20"));
			queryAlmTestSetId.append("\"]}");
			
			log.info("AlmTestSetFolderId Query : " + queryAlmTestSetId);
			
			String listFromTestSetFolderIdCollectionAsXml = conn.httpGet(testSetFolderUrl, queryAlmTestSetId.toString(), requestHeaders).toString();
			log.info("listFromTestSetFolderIdCollectionAsXml : " + listFromTestSetFolderIdCollectionAsXml);

			NodeList nList = getNodeList(listFromTestSetFolderIdCollectionAsXml);
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						testSetFolderId = eElement.getElementsByTagName("Value").item(0).getTextContent();
					}
				}
			}
			
			return testSetFolderId;
		} catch (Exception e) {
			log.error("Error in getting Alm testset folder id : " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public LinkedHashMap<String, String> getAlmTestSetsIdName(RestConnectorUtility conn, Map<String, String> requestHeaders, String testSetsUrl,
										String testSetFolderId) throws Exception {
		
		StringBuilder queryAlmTestSetsIdName = new StringBuilder();
		ArrayList<String> testSetsIds = new ArrayList<String>();
		ArrayList<String> testSetsNames = new ArrayList<String>();
		LinkedHashMap<String ,String> testSets = new LinkedHashMap<String, String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmTestSetsIdName.append("query={parent-id[");
			queryAlmTestSetsIdName.append(testSetFolderId);
			queryAlmTestSetsIdName.append("]}");
			queryAlmTestSetsIdName.append("&fields=name,id");

			log.info("AlmTestSetsIds Query : " + queryAlmTestSetsIdName);

			String listFromAlmTestSetsIdsCollectionAsXml = conn.httpGet(testSetsUrl, queryAlmTestSetsIdName.toString(), requestHeaders).toString();
			log.info("listFromAlmTestSetsIdsCollectionAsXml : " + listFromAlmTestSetsIdsCollectionAsXml);

			NodeList nList = getNodeList(listFromAlmTestSetsIdsCollectionAsXml);
					
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						testSetsIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						testSetsNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			for(int i=0; i<testSetsIds.size(); i++) {
				testSets.put(testSetsIds.get(i), testSetsNames.get(i));
			}
			
			return testSets;
		} catch (Exception e) {
				log.error("Error in getting Alm test sets ids and names : " + e.getMessage());
				throw e;
		}

	}
	
	@Override
	public ArrayList<String> getAlmTestInstanceIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String testInstancesUrl,
														String testSetId) throws Exception {
		
		StringBuilder queryAlmTestInstanceIds = new StringBuilder();
		ArrayList<String> testInstancesIds = new ArrayList<String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmTestInstanceIds.append("query={cycle-id[");
			queryAlmTestInstanceIds.append(testSetId);
			queryAlmTestInstanceIds.append("]}");
			queryAlmTestInstanceIds.append("&fields=id,test-id,test-config-id,assign-rcyc");
			
			log.info("AlmTestInstanceIds Query : " + queryAlmTestInstanceIds);
			
			String listFromTestInstanceIdCollectionAsXml = conn.httpGet(testInstancesUrl, queryAlmTestInstanceIds.toString(), requestHeaders).toString();
			log.info("listFromTestInstanceIdCollectionAsXml : " + listFromTestInstanceIdCollectionAsXml);

			NodeList nList = getNodeList(listFromTestInstanceIdCollectionAsXml);
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						testInstancesIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			return testInstancesIds;
		} catch (Exception e) {
			log.error("Error in getting Alm test instance ids : " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public String getAlmTCExecCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String runsUrl, String testSetId, String tcStatus) throws Exception {
	
		StringBuilder queryAlmTCExec = new StringBuilder();
		try {
			queryAlmTCExec.append("query={status[");
			queryAlmTCExec.append(tcStatus.replace(" ", "%20"));
			queryAlmTCExec.append("];");
			queryAlmTCExec.append("cycle-id[");
			queryAlmTCExec.append(testSetId);
			queryAlmTCExec.append("]}");
			queryAlmTCExec.append("&fields=id,name,test-id,test-config-id,assign-rcyc,status");

			log.info("AlmTestcases Execution Query : " + queryAlmTCExec);

			String listFromTCExecCollectionAsXml = conn.httpGet(runsUrl, queryAlmTCExec.toString(), requestHeaders).toString();
			log.info("listFromTCExecCollectionAsXml : " + listFromTCExecCollectionAsXml);

			EntitiesUtility entitiesTestcases = MarshallingUtility.marshal(EntitiesUtility.class, listFromTCExecCollectionAsXml);
			log.info("Testcases Entities : " + entitiesTestcases);
			log.info(tcStatus + "#" + "Count : " + entitiesTestcases.getTotalResults());

			return entitiesTestcases.getTotalResults();
		} catch (Exception e) {
			log.error("Error in getting testcases execution count : " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public String getAlmTestFolderId(RestConnectorUtility conn, Map<String, String> requestHeaders, String testFolderUrl,
										String releaseName, String releaseId, String testPlanFolderName) throws Exception {
		
		StringBuilder queryAlmTestId = new StringBuilder();
		String testFolderId = null;
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmTestId.append("query={name[");
			queryAlmTestId.append("\"");
			queryAlmTestId.append(testPlanFolderName.replace(" ", "%20"));
			queryAlmTestId.append("\"]}");
			
			log.info("AlmTestFolderId Query : " + queryAlmTestId);
			
			String listFromTestFolderIdCollectionAsXml = conn.httpGet(testFolderUrl, queryAlmTestId.toString(), requestHeaders).toString();
			log.info("listFromTestFolderIdCollectionAsXml : " + listFromTestFolderIdCollectionAsXml);

			NodeList nList = getNodeList(listFromTestFolderIdCollectionAsXml);
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						testFolderId = eElement.getElementsByTagName("Value").item(0).getTextContent();
					}
				}
			}
			
			return testFolderId;
		} catch (Exception e) {
			log.error("Error in getting Alm test folder id : " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public LinkedHashMap<String, String> getAlmTestFoldersIdName(RestConnectorUtility conn, Map<String, String> requestHeaders, String testFolderUrl,
																String testParentFolderId) throws Exception {
		
		StringBuilder queryAlmTestFoldersIdName = new StringBuilder();
		ArrayList<String> testFolderIds = new ArrayList<String>();
		ArrayList<String> testFolderNames = new ArrayList<String>();
		LinkedHashMap<String ,String> testFolders = new LinkedHashMap<String, String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmTestFoldersIdName.append("query={parent-id[");
			queryAlmTestFoldersIdName.append(testParentFolderId);
			queryAlmTestFoldersIdName.append("]}");
			queryAlmTestFoldersIdName.append("&fields=id,name");

			log.info("AlmTestFoldersIds Query : " + queryAlmTestFoldersIdName);

			String listFromAlmTestFolderIdsCollectionAsXml = conn.httpGet(testFolderUrl, queryAlmTestFoldersIdName.toString(), requestHeaders).toString();
			log.info("listFromAlmTestFolderIdsCollectionAsXml : " + listFromAlmTestFolderIdsCollectionAsXml);

			NodeList nList = getNodeList(listFromAlmTestFolderIdsCollectionAsXml);
					
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						testFolderIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						testFolderNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			for(int i=0; i<testFolderIds.size(); i++) {
				testFolders.put(testFolderIds.get(i), testFolderNames.get(i));
			}
			
			return testFolders;
		} catch (Exception e) {
				log.error("Error in getting Alm test folders ids and names : " + e.getMessage());
				throw e;
		}

	}
	
	@Override
	public LinkedHashMap<String, String> getAlmTestsIdName(RestConnectorUtility conn, Map<String, String> requestHeaders, String testUrl,
																String testFolderId) throws Exception {
		
		StringBuilder queryAlmTestsIdName = new StringBuilder();
		ArrayList<String> testsIds = new ArrayList<String>();
		ArrayList<String> testsNames = new ArrayList<String>();
		LinkedHashMap<String ,String> tests = new LinkedHashMap<String, String>();
		
		Node nNode = null;
		Element eElement = null;
		
		try{
			queryAlmTestsIdName.append("query={parent-id[");
			queryAlmTestsIdName.append(testFolderId);
			queryAlmTestsIdName.append("]}");
			queryAlmTestsIdName.append("&fields=name,id");

			log.info("AlmTestIds Query : " + queryAlmTestsIdName);

			String listFromAlmTestIdsCollectionAsXml = conn.httpGet(testUrl, queryAlmTestsIdName.toString(), requestHeaders).toString();
			log.info("listFromAlmTestIdsCollectionAsXml : " + listFromAlmTestIdsCollectionAsXml);

			NodeList nList = getNodeList(listFromAlmTestIdsCollectionAsXml);
					
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("id")) {
						testsIds.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
					if(eElement.getAttributeNode("Name").getTextContent().equalsIgnoreCase("name")) {
						testsNames.add(eElement.getElementsByTagName("Value").item(0).getTextContent());
					}
				}
			}
			
			for(int i=0; i<testsIds.size(); i++) {
				tests.put(testsIds.get(i), testsNames.get(i));
			}
			
			return tests;
		} catch (Exception e) {
				log.error("Error in getting Alm tests ids and names : " + e.getMessage());
				throw e;
		}

	}
	
	@Override
	public Integer getAlmTestsCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String testUrl,
																String testFolderId) throws Exception {
		
		StringBuilder queryAlmTests = new StringBuilder();
		
		try{
			queryAlmTests.append("query={parent-id[");
			queryAlmTests.append(testFolderId);
			queryAlmTests.append("]}");
			queryAlmTests.append("&fields=id,name");

			log.info("AlmTests Query : " + queryAlmTests);

			String listFromAlmTestsCollectionAsXml = conn.httpGet(testUrl, queryAlmTests.toString(), requestHeaders).toString();
			log.info("listFromAlmTestsCollectionAsXml : " + listFromAlmTestsCollectionAsXml);

			EntitiesUtility entitiesTests = MarshallingUtility.marshal(EntitiesUtility.class, listFromAlmTestsCollectionAsXml);
			log.info("Tests Entities : " + entitiesTests);
			log.info("Tests Count : " + entitiesTests.getTotalResults());

			return Integer.parseInt(entitiesTests.getTotalResults());
		} catch (Exception e) {
				log.error("Error in getting Alm tests count : " + e.getMessage());
				throw e;
		}
	}
	
	@Override
	public Integer getAlmTestSubFoldersCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String testFolderUrl,
																String testFolderId) throws Exception {
		
		StringBuilder queryAlmTestsSubFolders = new StringBuilder();
	
		try{
			queryAlmTestsSubFolders.append("query={parent-id[");
			queryAlmTestsSubFolders.append(testFolderId);
			queryAlmTestsSubFolders.append("]}");
			queryAlmTestsSubFolders.append("&fields=name,id");

			log.info("AlmTestIds Query : " + queryAlmTestsSubFolders);

			String listFromAlmTestSubFolderCollectionAsXml = conn.httpGet(testFolderUrl, queryAlmTestsSubFolders.toString(), requestHeaders).toString();
			log.info("listFromAlmTestSubFolderCollectionAsXml : " + listFromAlmTestSubFolderCollectionAsXml);

			EntitiesUtility entitiesTestSubFolders = MarshallingUtility.marshal(EntitiesUtility.class, listFromAlmTestSubFolderCollectionAsXml);
			log.info("TestSubFolders Entities : " + entitiesTestSubFolders);
			log.info("TestSubFolders Count : " + entitiesTestSubFolders.getTotalResults());

			return Integer.parseInt(entitiesTestSubFolders.getTotalResults());
		} catch (Exception e) {
				log.error("Error in getting Alm test sub folders count : " + e.getMessage());
				throw e;
		}
	}
	
	@Override
	public Integer getAlmTestExecSubFoldersCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String testSetFolderUrl,
																String testSetId) throws Exception {
		
		StringBuilder queryAlmTestSetsSubFolders = new StringBuilder();
	
		try{
			queryAlmTestSetsSubFolders.append("query={parent-id[");
			queryAlmTestSetsSubFolders.append(testSetId);
			queryAlmTestSetsSubFolders.append("]}");
			queryAlmTestSetsSubFolders.append("&fields=id,name");

			log.info("AlmTestIds Query : " + queryAlmTestSetsSubFolders);

			String listFromAlmTestSetsSubFolderCollectionAsXml = conn.httpGet(testSetFolderUrl, queryAlmTestSetsSubFolders.toString(), requestHeaders).toString();
			log.info("listFromAlmTestSetsSubFolderCollectionAsXml : " + listFromAlmTestSetsSubFolderCollectionAsXml);

			EntitiesUtility entitiesTestSetsSubFolders = MarshallingUtility.marshal(EntitiesUtility.class, listFromAlmTestSetsSubFolderCollectionAsXml);
			log.info("TestSets SubFolders Entities : " + entitiesTestSetsSubFolders);
			log.info("TestSets SubFolders Count : " + entitiesTestSetsSubFolders.getTotalResults());

			return Integer.parseInt(entitiesTestSetsSubFolders.getTotalResults());
		} catch (Exception e) {
				log.error("Error in getting Alm test sets sub folders count : " + e.getMessage());
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
