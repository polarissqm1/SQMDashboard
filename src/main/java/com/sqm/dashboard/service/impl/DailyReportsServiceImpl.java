package com.sqm.dashboard.service.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqm.dashboard.dao.DailyReportsDAO;
import com.sqm.dashboard.service.DailyReportsService;
@Service
public class DailyReportsServiceImpl implements DailyReportsService {

	@Autowired
	private DailyReportsDAO dailyReportsDao;
	@Override
	public Response getDailyReportsInfo(String project, String release) throws Exception {
			
		try{
		
		return dailyReportsDao.getDailyReportsInfo(project, release);
	}catch(Exception e){
		throw e;
	}
		
		}

}
