package com.sqm.dashboard.dao;

import java.net.UnknownHostException;

import javax.ws.rs.core.Response;

public interface DashboardDAO {
	public Response getLandingInfo(String project,String release)throws Exception;
}
