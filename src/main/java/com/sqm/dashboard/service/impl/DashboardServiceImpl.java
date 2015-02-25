package com.sqm.dashboard.service.impl;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.core.Response;










import com.sqm.dashboard.controller.DashboardController;
import com.sqm.dashboard.dao.DashboardDAO;
import com.sqm.dashboard.service.DashboardService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;



@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService{
	
	
	final Logger log=Logger.getLogger(DashboardController.class);

	@Autowired
	DashboardDAO dashboardDAO;
	
	@Override
	public Response getLandingInfo(String project,String release) throws Exception {
		
		try{
			/*
			int a[] = new int[2];
	         System.out.println("Access element three :" + a[3]);*/
			
			
		Gson gson=new Gson();
		System.out.println(dashboardDAO.getLandingInfo(project,release).toString());
		
	Set ss=	dashboardDAO.getLandingInfo(project,release).getMetadata().keySet();
		Iterator itr=ss.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
		}
		
		catch (Exception e) {
			log.debug("Service layer in project data Layer");
			throw e;
			
		}
	
		
		return dashboardDAO.getLandingInfo(project,release);
	}

}
