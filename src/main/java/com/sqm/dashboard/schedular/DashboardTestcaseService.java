package com.sqm.dashboard.schedular;

import com.sqm.dashboard.util.RestConnectorUtility;

import java.util.Map;

public interface DashboardTestcaseService {

	public String getAlmTestcases(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String testcasesUrl, String releaseId) throws Exception;
	
}
