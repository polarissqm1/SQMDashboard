package com.sqm.dashboard.service;

import javax.ws.rs.core.Response;

public interface DashboardService {
	 public Response getLandingInfo(String project,String release);
}
