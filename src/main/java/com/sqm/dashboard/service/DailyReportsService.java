package com.sqm.dashboard.service;

import javax.ws.rs.core.Response;

public interface DailyReportsService {

	public Response getDailyReportsInfo(String project,String release)throws Exception;
}
