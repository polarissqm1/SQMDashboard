package com.sqm.dashboard.schedular;

import java.util.ArrayList;
import java.util.Map;

import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmSchedProjectService {
	
	public ArrayList<String> getAlmProjects(RestConnectorUtility connection, Map<String, String> requestHeaders, 
													String domainName) throws Exception;

}
