package com.sqm.dashboard.service;

import javax.ws.rs.core.Response;

import com.sqm.dashboard.VO.DashboardVO;

public interface TrendReportsService {
	public Response getTrendingInfo(String project,String release,String fromDate,String toDate)throws Exception;
}
