package com.sqm.dashboard.dao;

import javax.ws.rs.core.Response;

public interface DailyReportsDAO {

	public Response getDailyReportsInfo(String project,String release)throws Exception;
}
