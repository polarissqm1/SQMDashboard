package com.sqm.dashboard.service.impl;

import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.core.Response;






import com.sqm.dashboard.dao.DashboardDAO;
import com.sqm.dashboard.service.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;



@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService{

	@Autowired
	DashboardDAO dashboardDAO;
	
	@Override
	public Response getLandingInfo(String project,String release) {
		Gson gson=new Gson();
		System.out.println(dashboardDAO.getLandingInfo(project,release).toString());
		
	Set ss=	dashboardDAO.getLandingInfo(project,release).getMetadata().keySet();
		Iterator itr=ss.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
		return dashboardDAO.getLandingInfo(project,release);
	}

}
