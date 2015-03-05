package com.sqm.dashboard.service.impl;

import java.net.UnknownHostException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Response;

import com.sqm.dashboard.VO.DashboardVO;
import com.sqm.dashboard.VO.StatusAndSeverityVO;
import com.sqm.dashboard.VO.TestCaseExecutionStatusVO;
import com.sqm.dashboard.VO.TrendReportsVO;
import com.sqm.dashboard.controller.DashboardController;
import com.sqm.dashboard.dao.DashboardDAO;
import com.sqm.dashboard.dao.TrendReportsDAO;
import com.sqm.dashboard.service.DashboardService;
import com.sqm.dashboard.service.TrendReportsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;



@Service("trendReportsService")
public class TrendReportsServiceImpl implements TrendReportsService{
	
	
	final Logger log=Logger.getLogger(DashboardController.class);
	public static final int urgent_4=4;
	public static final int high_3=3;
	public static final int medium_2=2;
	public static final int low_1=1;

	@Autowired
	TrendReportsDAO trendReportsDAO;
	
	
			@Override
		public Response getTrendingInfo(String project,String release,String fromDate,String toDate) throws Exception {
				
				TrendReportsVO trendReportsVO=new TrendReportsVO();
				Response.ResponseBuilder response = Response.ok(trendReportsVO);
			try{/*********************Defect Density***************************/
				DashboardVO dashVO=trendReportsDAO.getTrendingInfo(project, release,fromDate,toDate);
				String testcaseVO=(String) dashVO.getTestCaseExecutionStatusVO().get(6).getCount();
				log.info("Inside TestCase Execution "+ testcaseVO);
				String statusVO=(String)dashVO.getStatusAndSeverityVO().get(5).getTotal();
				log.info("total for status VO"+dashVO.getStatusAndSeverityVO().get(5).getTotal());
				Float defectDensity=Float.parseFloat(testcaseVO)/Float.parseFloat(statusVO);
				log.info("Inside defectDensity Execution "+ defectDensity);
				trendReportsVO.setDefectDensity(defectDensity);
				/*************************************Bad Fix*************/
				String reopened=(String) dashVO.getStatusAndSeverityVO().get(0).getTotal();
				Float badFix=Float.parseFloat(reopened)/Float.parseFloat(statusVO);
				log.info("Inside bad Fix "+ badFix);
				trendReportsVO.setBadFix(badFix);
				/*****************************Status Severity*******************************/
				String opened=(String) dashVO.getStatusAndSeverityVO().get(0).getTotal();
				String closed=(String) dashVO.getStatusAndSeverityVO().get(2).getTotal();
				trendReportsVO.setClosed(closed);
				trendReportsVO.setOpen(opened);
				/***************************Defect Acceptance**********************************/
				String reject=(String)dashVO.getStatusAndSeverityVO().get(3).getTotal();
				Float accept=1-(Float.parseFloat(reject)/Float.parseFloat(statusVO));
				trendReportsVO.setDefectAcceptance(accept);
				/***************************Defect Severity Index*****************************/
				Float urgent=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getUrgent());
				Float high=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getHigh());
				Float medium=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getMedium());
				Float low=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getLow());
				Float dsi=((urgent*urgent_4)+(high*high_3)+(medium*medium_2)+(low*low_1))/Float.parseFloat(statusVO);
				trendReportsVO.setDefectSeverityIndex(dsi);
				/*************************Passed VS Failed**************************/
				String pass=(String) dashVO.getTestCaseExecutionStatusVO().get(0).getCount();
				String fail=(String) dashVO.getTestCaseExecutionStatusVO().get(1).getCount();
				trendReportsVO.setPass(pass);
				trendReportsVO.setFailed(fail);
				/*************************Defect Severity Break up******************************/
				trendReportsVO.setUrgent(urgent.toString());
				trendReportsVO.setHigh(high.toString());
				trendReportsVO.setMedium(medium.toString());
				trendReportsVO.setUrgent(low.toString());
				/************************releasedate****************/
				String rDate=dashVO.getRdate();
				trendReportsVO.setRdate(rDate);
				
			}
			
			catch (Exception e) {
				log.debug("Service layer in project data Layer");
				throw e;
				
			}
			return response.build();
		}
		
		
	
	}	
	

		
	
	


