package com.sqm.dashboard.schedular;

import com.sqm.dashboard.util.RestConnectorUtility;

public interface DashboardAuthService {
	
	public String login(RestConnectorUtility conn, String username, String password) throws Exception;
	
	public String login(RestConnectorUtility conn, String loginUrl, String username, String password) throws Exception;
	
	public boolean logout(RestConnectorUtility conn) throws Exception;
	
	public String isAuthenticated(RestConnectorUtility conn) throws Exception;

}


