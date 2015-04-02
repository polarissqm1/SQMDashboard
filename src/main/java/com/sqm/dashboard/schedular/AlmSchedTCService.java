package com.sqm.dashboard.schedular;

import java.util.Map;

import com.sqm.dashboard.VO.AlmTestcaseVO;
import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmSchedTCService {

	public AlmTestcaseVO getAlmTestcases(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String domain, String project, String releaseName, String releaseId, String testLabFolderName) throws Exception;
	
	public String getAlmPlannedTC(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String domain, String project, String releaseName, String releaseId, String testPlanFolderName) throws Exception;
	
}
