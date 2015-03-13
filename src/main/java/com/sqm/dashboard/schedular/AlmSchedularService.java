package com.sqm.dashboard.schedular;

import java.util.Map;

import com.mongodb.DBCollection;
import com.sqm.dashboard.util.RestConnectorUtility;

public interface AlmSchedularService {
	
	public void saveAlmDetails(RestConnectorUtility connection, Map<String, String> requestHeaders,
			String username, String password, DBCollection collection) throws Exception;
	
}