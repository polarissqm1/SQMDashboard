package com.sqm.dashboard.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import com.sqm.dashboard.VO.DashboardVO;

public interface TrendReportsDAO {
	public ArrayList getTrendingInfo(String project,String release,String fromDate,String toDate)throws Exception;
	public ArrayList getReleaseInfo(String project,String release,String fromDate,String toDate)throws Exception;
}

