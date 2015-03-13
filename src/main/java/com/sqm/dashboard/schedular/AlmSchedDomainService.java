package com.sqm.dashboard.schedular;

import com.sqm.dashboard.util.RestConnectorUtility;

import java.util.HashMap;
import java.util.Map;

public interface AlmSchedDomainService {
	
	public HashMap<String,String> getAlmDomains(RestConnectorUtility connection, Map<String, String> requestHeaders) throws Exception;

}
