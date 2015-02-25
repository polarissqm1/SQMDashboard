package com.sqm.dashboard.dao;

import javax.ws.rs.core.Response;

public interface DashboardDAO {
	public Response getLandingInfo(String project,String release);
}
