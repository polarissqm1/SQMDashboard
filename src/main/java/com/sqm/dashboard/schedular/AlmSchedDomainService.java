package com.sqm.dashboard.schedular;

import java.util.ArrayList;
import java.util.Map;

import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmSchedDomainService {
	
	public ArrayList<String> getAlmDomains(RestConnectorUtility connection, Map<String, String> requestHeaders) throws Exception;

}
