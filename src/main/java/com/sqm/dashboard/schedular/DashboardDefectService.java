package com.sqm.dashboard.schedular;

import java.util.Map;

import com.sqm.dashboard.VO.SchedularDefectsVO;
import com.sqm.dashboard.util.RestConnectorUtility;

public interface DashboardDefectService {
	
	public SchedularDefectsVO getAlmDefects(RestConnectorUtility connection, Map<String, String> requestHeaders,
			String defectsUrl, String releaseId) throws Exception;
	
}
