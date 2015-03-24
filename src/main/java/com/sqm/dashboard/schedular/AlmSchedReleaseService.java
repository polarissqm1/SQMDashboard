package com.sqm.dashboard.schedular;

import java.util.ArrayList;
import java.util.Map;

import com.sqm.dashboard.VO.AlmReleaseCycleDetails;
import com.sqm.dashboard.VO.AlmReleaseDetails;
import com.sqm.dashboard.VO.SchedularReleaseCyclesVO;
import com.sqm.dashboard.VO.SchedularReleaseDefectsVO;
import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmSchedReleaseService {
	
	public ArrayList<AlmReleaseDetails> getAlmReleasesDetails(RestConnectorUtility conn, 
											String releasesUrl, 
											Map<String, String> requestHeaders) throws Exception; 
	
	public SchedularReleaseDefectsVO getAlmReleaseDefectsData(RestConnectorUtility conn, 
																Map<String, String> requestHeaders, 
																String releaseDefectsUrl, 
																String releaseId) throws Exception;

	public ArrayList<String> getAlmReleaseDefectIds(RestConnectorUtility conn, 
														Map<String, String> requestHeaders, 
														String releaseDefectsUrl, 
														String releaseId) throws Exception;

	public ArrayList<AlmReleaseCycleDetails> getAlmReleaseCyclesDetails(RestConnectorUtility conn, 
																			Map<String, String> requestHeaders, 
																			String releaseCyclesUrl, 
																			String releaseId) throws Exception;

	public SchedularReleaseCyclesVO getAlmReleaseCyclesData(RestConnectorUtility conn, 
																			Map<String, String> requestHeaders, 
																			String releaseCyclesUrl, 
																			String releaseId) throws Exception;
	
}
