package com.sqm.dashboard.dao;

import java.net.UnknownHostException;

import javax.ws.rs.core.Response;

import com.sqm.dashboard.VO.DashboardVO;

public interface TrendReportsDAO {
	public DashboardVO getTrendingInfo(String project,String release,String fromDate,String toDate)throws UnknownHostException;
}

