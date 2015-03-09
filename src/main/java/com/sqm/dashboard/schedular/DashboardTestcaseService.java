package com.sqm.dashboard.schedular;

import com.sqm.dashboard.VO.SchedularTCExecStatusVO;
import com.sqm.dashboard.util.RestConnectorUtility;

import java.util.Map;

public interface DashboardTestcaseService {


	public SchedularTCExecStatusVO getAlmTestcases(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String testcasesUrl, String releaseId) throws Exception;
	
}
