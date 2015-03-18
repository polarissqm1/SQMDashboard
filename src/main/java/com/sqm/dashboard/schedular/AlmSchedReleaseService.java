package com.sqm.dashboard.schedular;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sqm.dashboard.VO.AlmReleaseDetails;
import com.sqm.dashboard.VO.SchedularReleaseDefectsVO;
import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmSchedReleaseService {
	
	//public List<String> getAlmReleases(RestConnectorUtility conn, String testcasesUrl, Map<String, String> requestHeaders) throws Exception;

	public ArrayList getAlmReleasesDetails(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception; 
	
	public List<String> getAlmReleasesIds(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception;
	
	public List<String> getAlmReleasesNames(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception;
	
	public ArrayList<String> getAlmReleaseCycleNames(RestConnectorUtility conn, String releaseCyclesUrl, Map<String, String> requestHeaders, String releaseId) throws Exception;

	public SchedularReleaseDefectsVO getAlmReleaseDefectsData(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception;

	public ArrayList<String> getAlmReleaseDefectIds(RestConnectorUtility conn, Map<String, String> requestHeaders, String releaseDefectsUrl, String releaseId) throws Exception;
}
