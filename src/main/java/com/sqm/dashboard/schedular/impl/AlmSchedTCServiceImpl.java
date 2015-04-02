package com.sqm.dashboard.schedular.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sqm.dashboard.VO.AlmTestcaseVO;
import com.sqm.dashboard.VO.SchedularManualVO;
import com.sqm.dashboard.schedular.AlmSchedTCService;
import com.sqm.dashboard.util.Constants;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedTCServiceImpl")
public class AlmSchedTCServiceImpl implements AlmSchedTCService {
	
	Logger log = Logger.getLogger(AlmSchedTCServiceImpl.class);

	@Autowired
	private AlmSchedTestcaseServiceImpl almSchedTestcaseServiceImpl;
	
	@Autowired
	private AlmTestcaseVO almTCVO;
	
	@Autowired
	private SchedularManualVO schedManualVO;
	
	@Value("${almEntityTestSetFolder}")
	private String almEntityTestSetFolder;
	
	@Value("${almEntityTestSet}")
	private String almEntityTestSet;
	
	@Value("${almEntityTestInstance}")
	private String almEntityTestInstance;
	
	@Value("${almEntityRun}")
	private String almEntityRun;
	
	@Value("${almEntityTestFolder}")
	private String almEntityTestFolder;
	
	@Value("${almEntityTest}")
	private String almEntityTest;
	
	private String tcPassed;
	private String tcFailed;
	private String tcNotRunAndNotCompleted;
	private String tcNA;
	private String tcDeferred;
	private String tcBlocked;
	
	@Override
	public AlmTestcaseVO getAlmTestcases(RestConnectorUtility conn, Map<String, String> requestHeaders, 
											String domain, String project, String releaseName,
											String releaseId, String testLabFolderName) throws Exception {
		
		try {
			log.info("domain : " + domain +
					" project : " + project +
					" releaseName : " + releaseName +
					" releaseId : " + releaseId +
					" testLabFolderName : " + testLabFolderName);
			
			/** TestSetFolders URL **/
			String testSetFolderUrl = conn.buildEntityCollectionUrl(almEntityTestSetFolder, domain, project);
			log.info("testSetFolderUrl : " + testSetFolderUrl);
			
			/** TestSets URL **/
			String testSetsUrl = conn.buildEntityCollectionUrl(almEntityTestSet, domain, project);
			log.info("testSetsUrl : " + testSetsUrl);
			
			/** TestInstances URL **/
			/*String testInstancesUrl = conn.buildEntityCollectionUrl(almEntityTestInstance, domain, project);
			log.info("testInstancesUrl : " + testInstancesUrl);*/
			
			/** Runs URL **/
			String runsUrl = conn.buildEntityCollectionUrl(almEntityRun, domain, project);
			log.info("testcases runsUrl : " + runsUrl);
			
			/** Get TestSetFolder Parent Id **/
			String testSetFolderId = almSchedTestcaseServiceImpl.getAlmTestSetFolderId(conn, requestHeaders, testSetFolderUrl, releaseName, releaseId, testLabFolderName);
			
			/** Get TestSets Id **/
			LinkedHashMap<String, String> testSetsIdName = almSchedTestcaseServiceImpl.getAlmTestSetsIdName(conn, requestHeaders, testSetsUrl, testSetFolderId);
			
			HashMap<String, Integer> statusCountZeroHashMap = new HashMap<String, Integer>(); 
			statusCountZeroHashMap.put("passed", 0);
			statusCountZeroHashMap.put("failed", 0);
			statusCountZeroHashMap.put("notRunNotCompleted", 0);
			statusCountZeroHashMap.put("nA", 0);
			statusCountZeroHashMap.put("deferred", 0);
			statusCountZeroHashMap.put("blocked", 0);
			
			HashMap<String, Integer> statusCounts = getTCExecCountsOfSubFolders(conn, requestHeaders, testSetFolderUrl, testSetsUrl, runsUrl, testSetsIdName, statusCountZeroHashMap);
			
			tcPassed = statusCounts.get("passed") + "";
			tcFailed = statusCounts.get("failed") + "";
			tcNotRunAndNotCompleted = statusCounts.get("notRunNotCompleted") + "";
			tcNA = statusCounts.get("nA") + "";
			tcDeferred = statusCounts.get("deferred") + "";
			tcBlocked = statusCounts.get("blocked") + "";
			
			schedManualVO.setPassed(tcPassed);
			schedManualVO.setFailed(tcFailed);
			schedManualVO.setNoRun(tcNotRunAndNotCompleted);
			//schedManualVO.setNA(tcNA);
			schedManualVO.setBlocked(tcBlocked);
			schedManualVO.setDeferred(tcDeferred);
		
			almTCVO.setSchedManualVO(schedManualVO);
		} catch (Exception e) {
			log.error("Error in getting Alm Manual Executed testcases for test sub folders : " + e.getMessage());
			throw e;
		}
	
		return almTCVO;
	}

	@Override
	public String getAlmPlannedTC(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String domain, String project, String releaseName, String releaseId, String testPlanFolderName) throws Exception {
		
		Integer plannedTestcases = 0;
		
		try {
			log.info("domain : " + domain +
					" project : " + project +
					" releaseName : " + releaseName +
					" releaseId : " + releaseId +
					" testPlanFolderName : " + testPlanFolderName);
			
			/** TestFolder URL **/
			String testFolderUrl = conn.buildEntityCollectionUrl(almEntityTestFolder, domain, project);
			log.info("testFolderUrl : " + testFolderUrl);
			
			/** Test URL **/
			String testUrl = conn.buildEntityCollectionUrl(almEntityTest, domain, project);
			log.info("testUrl : " + testUrl);
			
			/** Get Parent TestFolder Id **/
			String testParentFolderId = almSchedTestcaseServiceImpl.getAlmTestFolderId(conn, requestHeaders, testFolderUrl, releaseName, releaseId, testPlanFolderName);
			
			/** Get Test Folders Id **/
			LinkedHashMap<String, String> testFoldersIdName = almSchedTestcaseServiceImpl.getAlmTestFoldersIdName(conn, requestHeaders, testFolderUrl, testParentFolderId);
			
			plannedTestcases = getPlannedTCOfSubFolders(conn, requestHeaders, testFolderUrl, testUrl, testFoldersIdName, plannedTestcases);
			
			return plannedTestcases + "";
			
		} catch (Exception e) {
			log.error("Error in getting Alm planned testcases : " + e.getMessage());
			throw e;
		}
	}
	
	private Integer getPlannedTCOfSubFolders(RestConnectorUtility conn, Map<String, String> requestHeaders, 
											String testFolderUrl, String testUrl, LinkedHashMap<String, String> testFoldersHashMap, Integer plannedTC) throws Exception {
		
		log.info("plannedTC in getPlannedTCOfSubFolders : " + plannedTC);
		/*LinkedHashMap<String, String> tests = new LinkedHashMap<String, String>();*/
		LinkedHashMap<String, String> testSubFoldersIdName = new LinkedHashMap<String, String>();
		
		try {
			for (Map.Entry<String, String> testFoldersEntry : testFoldersHashMap.entrySet()) {
				String testFolderId = testFoldersEntry.getKey();
				String testFolderName = testFoldersEntry.getValue();
				log.info("TestFolderId : " + testFolderId + " # TestFolderName : " + testFolderName);
		    
				Integer subFoldersCount = 	almSchedTestcaseServiceImpl.getAlmTestSubFoldersCount(conn, requestHeaders, testFolderUrl, testFolderId);
				log.info("SubFoldersCount : " + subFoldersCount);

				if(subFoldersCount == 0){
					log.info("subFoldersCount : " + subFoldersCount);
		    	
					/*tests = almSchedTestcaseServiceImpl.getAlmTestsIdName(conn, requestHeaders, testUrl, testFolderId);
			    	plannedTC += tests.size();
			    	log.info("plannedTC tests.size() : " + tests.size());*/
			    
			    	plannedTC += almSchedTestcaseServiceImpl.getAlmTestsCount(conn, requestHeaders, testUrl, testFolderId);
			    	log.info("plannedTC IF : " + plannedTC);
			    } else {
					testSubFoldersIdName = almSchedTestcaseServiceImpl.getAlmTestFoldersIdName(conn, requestHeaders, testFolderUrl, testFolderId);
					Integer plannedTCSubFolers = getPlannedTCOfSubFolders(conn, requestHeaders, testFolderUrl, testUrl, testSubFoldersIdName, plannedTC);
					log.info("plannedTCSubFolers : " + plannedTCSubFolers);
					plannedTC = plannedTCSubFolers;
					log.info("plannedTC ELSE : " + plannedTC);
				}
			}
			return plannedTC;
		} catch (Exception e) {
			log.error("Error in getting Alm planned testcases for test sub folders : " + e.getMessage());
			throw e;
		}
	}
	
	private HashMap<String, Integer> getTCExecCountsOfSubFolders(RestConnectorUtility conn, Map<String, String> requestHeaders, 
							String testSetFolderUrl, String testSetsUrl, String runsUrl, LinkedHashMap<String, String> testSetsHashMap, 
							HashMap<String, Integer> statusCountHashMap) throws Exception {

		log.info("statusCountHashMap in getTCExecCountsOfSubFolders : " + statusCountHashMap.toString());
		
		LinkedHashMap<String, String> testSetsSubFoldersIdName = new LinkedHashMap<String, String>();
		
		try {
			for (Map.Entry<String, String> testSetsEntry : testSetsHashMap.entrySet()) {
				String testSetId = testSetsEntry.getKey();
				String testSetName = testSetsEntry.getValue();
				log.info("TestSetId : " + testSetId + "#TestSetName : " + testSetName);
		    
				Integer subFoldersCount = 	almSchedTestcaseServiceImpl.getAlmTestExecSubFoldersCount(conn, requestHeaders, testSetFolderUrl, testSetId);
				log.info("SubFoldersCount : " + subFoldersCount);
				
				if(subFoldersCount == 0){
					log.info("subFoldersCount : " + subFoldersCount);
					
					Integer passed = statusCountHashMap.get("passed");
					Integer failed = statusCountHashMap.get("failed");
					Integer notRunNotCompleted = statusCountHashMap.get("notRunNotCompleted");
					Integer nA = statusCountHashMap.get("nA");
					Integer deferred = statusCountHashMap.get("deferred");
					Integer blocked = statusCountHashMap.get("blocked");
					
					/*ArrayList<String> testInstanceIds = almSchedTestcaseServiceImpl.getAlmTestInstanceIds(conn, requestHeaders, testInstancesUrl, testSetId);*/
		   
					passed += Integer.parseInt(almSchedTestcaseServiceImpl.getAlmTCExecCount(conn, requestHeaders, runsUrl, testSetId, Constants.TESTCASES_STATUS_PASSED));
					failed += Integer.parseInt(almSchedTestcaseServiceImpl.getAlmTCExecCount(conn, requestHeaders, runsUrl, testSetId, Constants.TESTCASES_STATUS_FAILED));
					notRunNotCompleted += Integer.parseInt(almSchedTestcaseServiceImpl.getAlmTCExecCount(conn, requestHeaders, runsUrl, testSetId, Constants.TESTCASES_STATUS_NORUNANDNOTCOMPLETED));
					nA += Integer.parseInt(almSchedTestcaseServiceImpl.getAlmTCExecCount(conn, requestHeaders, runsUrl, testSetId, Constants.TESTCASES_STATUS_NOTAPPLICABLE));
					deferred += Integer.parseInt(almSchedTestcaseServiceImpl.getAlmTCExecCount(conn, requestHeaders, runsUrl, testSetId, Constants.TESTCASES_STATUS_DEFERRED));
					blocked += Integer.parseInt(almSchedTestcaseServiceImpl.getAlmTCExecCount(conn, requestHeaders, runsUrl, testSetId, Constants.TESTCASES_STATUS_BLOCKED));
					
					statusCountHashMap.put("passed", passed);
					statusCountHashMap.put("failed", failed);
					statusCountHashMap.put("notRunNotCompleted", notRunNotCompleted);
					statusCountHashMap.put("nA", nA);
					statusCountHashMap.put("deferred", deferred);
					statusCountHashMap.put("blocked", blocked);
					
					log.info("statusCountHashMap IF : " + statusCountHashMap.toString());
				} else {
					testSetsSubFoldersIdName = almSchedTestcaseServiceImpl.getAlmTestSetsIdName(conn, requestHeaders, testSetsUrl, testSetId);
					HashMap<String, Integer> statusCountsSubFolder = getTCExecCountsOfSubFolders(conn, requestHeaders, testSetFolderUrl, testSetsUrl, runsUrl, testSetsSubFoldersIdName, statusCountHashMap);
					log.info("statusCountsSubFolder HashMap : " + statusCountsSubFolder.toString());
					
					statusCountHashMap = statusCountsSubFolder;
					log.info("statusCountHashMap ELSE : " + statusCountHashMap.toString());
				}
			}
			return statusCountHashMap;
		}  catch (Exception e) {
			log.error("Error in getting Alm executed testcases for sub folders : " + e.getMessage());
			throw e;
		} 
	}
}
