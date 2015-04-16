package com.sqm.dashboard.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;










import com.sqm.dashboard.VO.DashboardVO;
import com.sqm.dashboard.VO.LandingPageVO;
import com.sqm.dashboard.VO.StatusAndSeverityVO;
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
			ArrayList dashboardVO=dashboardDAO.getLandingInfo(project,release);
			HashMap manual=new HashMap();
			HashMap automation=new HashMap();
			HashMap statusAndSeverity=new HashMap();
			
			
			LandingPageVO landVO=new LandingPageVO();
			Float pass_manual=0.0f;
			Float fail_manual=0.0f;
			Float norun_manual=0.0f;
			Float block_manual=0.0f;
			Float defer_manual=0.0f;
			
			Float pass_auto=0.0f;
			Float fail_auto=0.0f;
			Float norun_auto=0.0f;
			Float block_auto=0.0f;
			Float defer_auto=0.0f;
			
			Float urgent=0.0f;
			Float high=0.0f;
			Float medium=0.0f;
			Float low=0.0f;
			
			
			for(int i=0;i<dashboardVO.size();i++){
				
				DashboardVO dashVO=(DashboardVO) dashboardVO.get(i);
				pass_manual=pass_manual+Float.parseFloat(dashVO.getManualVO().getPassed());
				fail_manual=fail_manual+Float.parseFloat(dashVO.getManualVO().getFailed());
				norun_manual=norun_manual+Float.parseFloat(dashVO.getManualVO().getNoRun());
				block_manual=block_manual+Float.parseFloat(dashVO.getManualVO().getBlocked());
				defer_manual=defer_manual+Float.parseFloat(dashVO.getManualVO().getDeferred());
				
				pass_auto=pass_auto+Float.parseFloat(dashVO.getAutomationVO().getPassed());
				fail_auto=fail_auto+Float.parseFloat(dashVO.getAutomationVO().getFailed());
				norun_auto=norun_auto+Float.parseFloat(dashVO.getAutomationVO().getNoRun());
				block_auto=block_auto+Float.parseFloat(dashVO.getAutomationVO().getBlocked());
				defer_auto=defer_auto+Float.parseFloat(dashVO.getAutomationVO().getDeferred());
				
				
				urgent=urgent+Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getUrgent());
				high=high+Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getHigh());
				medium=medium+Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getMedium());
				low=low+Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getLow());
				
				
			}
			
			manual.put("Passed", pass_manual);
			manual.put("Failed", fail_manual);
			manual.put("noRun", norun_manual);
			manual.put("Blocked", block_manual);
			manual.put("Deferred", defer_manual);
			
			automation.put("Passed", pass_auto);
			automation.put("Failed", fail_auto);
			automation.put("noRun", norun_auto);
			automation.put("Blocked", block_auto);
			automation.put("Deferred", defer_auto);
			
			statusAndSeverity.put("Urgent", urgent);
			statusAndSeverity.put("High", high);
			statusAndSeverity.put("Medium", medium);
			statusAndSeverity.put("Low", low);
			
			
			landVO.setManual(manual);
			landVO.setAutomation(automation);
			landVO.setStatusAndSeverity(statusAndSeverity);
			
			ResponseBuilder response = Response.ok(landVO);
			
			return response.build();
		
		}
		
		catch (Exception e) {
			log.debug("Service layer in project data Layer");
			throw e;
			
		}
	
		
		
	}
	
	@Override
	public Response getApplicationsList() throws Exception {
		try{
			/*
			int a[] = new int[2];
	         System.out.println("Access element three :" + a[3]);*/
			
			
			return dashboardDAO.getApplicationsList();
		
		}
		
		catch (Exception e) {
			log.debug("Service layer in project data Layer");
			throw e;
			
		}
	}
	
	@Override
	public Response getReleaseList(String project) throws Exception {
		try{
			/*
			int a[] = new int[2];
	         System.out.println("Access element three :" + a[3]);*/
			
			
			return dashboardDAO.getReleaseList(project);
		
		}
		
		catch (Exception e) {
			log.debug("Service layer in project data Layer");
			throw e;
			
		}
	}



}
