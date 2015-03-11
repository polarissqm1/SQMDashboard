package com.sqm.dashboard.schedular;

import java.util.Map;

import com.sqm.dashboard.VO.AlmTestcaseVO;
import com.sqm.dashboard.util.RestConnectorUtility;

public interface DashboardTestcaseService {


	public AlmTestcaseVO getAlmTestcases(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String testcasesUrl, String releaseId) throws Exception;
	
}
