package com.sqm.dashboard.schedular;

import com.sqm.dashboard.util.RestConnectorUtility;

import java.util.HashMap;
import java.util.Map;

public interface AlmSchedProjectService {
	
	public HashMap<String,String> getAlmProjects(RestConnectorUtility connection, Map<String, String> requestHeaders, 
													String domainName) throws Exception;

}
