package com.sqm.dashboard.schedular;

import java.util.List;
import java.util.Map;

import com.sqm.dashboard.util.RestConnectorUtility;

public interface DashboardReleaseService {
	
	//public List<String> getAlmReleases(RestConnectorUtility conn, String testcasesUrl, Map<String, String> requestHeaders) throws Exception;

		public List<String> getAlmReleasesNames(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception;

		public List<String> getAlmReleasesIds(RestConnectorUtility conn, String releasesUrl, Map<String, String> requestHeaders) throws Exception;

}
