package com.sqm.dashboard.dao;

import java.net.UnknownHostException;

import javax.ws.rs.core.Response;

public interface DashboardDAO {
	public Response getLandingInfo(String project,String release)throws Exception;
	public Response getApplicationsList()throws Exception;
	public Response getReleaseList(String project )throws Exception;
}
