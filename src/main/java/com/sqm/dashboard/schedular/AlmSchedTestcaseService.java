package com.sqm.dashboard.schedular;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmSchedTestcaseService {

	public String getAlmTestSetFolderId(RestConnectorUtility conn, Map<String, String> requestHeaders,
									String testSetFolderUrl, String releaseName, String releaseId, String testLabFolder) throws Exception;
	
	public LinkedHashMap<String, String> getAlmTestSetsIdName(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String testSetsUrl, String testSetFolderId) throws Exception;
	
	public ArrayList<String> getAlmTestInstanceIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String testInstancesUrl,
									String testSetId) throws Exception;
	
	public String getAlmTCExecCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String runsUrl, 
									String testSetId, String status) throws Exception; 
	
	public String getAlmTestFolderId(RestConnectorUtility conn, Map<String, String> requestHeaders, String testFolderUrl,
									String releaseName, String releaseId, String testPlanFolderName) throws Exception;
	
	public LinkedHashMap<String, String> getAlmTestFoldersIdName(RestConnectorUtility conn, Map<String, String> requestHeaders, String testFolderUrl,
									String testParentFolderId) throws Exception;
			
	public LinkedHashMap<String, String> getAlmTestsIdName(RestConnectorUtility conn, Map<String, String> requestHeaders, String testUrl,
									String testFolderId) throws Exception;
	
	public Integer getAlmTestSubFoldersCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String testFolderUrl,
									String testFolderId) throws Exception;
	
	public Integer getAlmTestsCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String testUrl,
									String testFolderId) throws Exception;
	
	public Integer getAlmTestExecSubFoldersCount(RestConnectorUtility conn, Map<String, String> requestHeaders, String testFolderUrl,
									String testFolderId) throws Exception;
}