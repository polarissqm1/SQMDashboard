package com.sqm.dashboard.schedular;

import java.util.Map;

import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmReleasesSchedularService {
	
	public String saveReleasesDetails(RestConnectorUtility connection, Map<String, String> requestHeaders,
			String username, String password) throws Exception;
	
}