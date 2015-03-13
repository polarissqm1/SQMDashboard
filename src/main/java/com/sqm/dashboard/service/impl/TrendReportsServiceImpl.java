package com.sqm.dashboard.service.impl;

import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Response;

import com.sqm.dashboard.VO.DashboardVO;
import com.sqm.dashboard.VO.DefectIdsVO;
import com.sqm.dashboard.VO.StatusAndSeverityVO;
import com.sqm.dashboard.VO.TestCaseExecutionStatusVO;
import com.sqm.dashboard.VO.TrendReportsReleaseVO;
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
				
				TrendReportsVO trendReportsVO=null;
				Response.ResponseBuilder response =null;
			
			try{/*********************Defect Density***************************/
				ArrayList dashVOList=trendReportsDAO.getTrendingInfo(project, release,fromDate,toDate);
				log.info("DashVOList size "+dashVOList.size());
				log.info("DashVOList  "+dashVOList);
				ArrayList originalList=new ArrayList();
				
				
				DashboardVO dashVO=null;
				
				  for (int i=0;i<dashVOList.size();i++)
				  {
					  Float defectDensity;
					  Float badFix;
					  Float accept;
					  Float dsi;
					  trendReportsVO=new TrendReportsVO();
					  dashVO=(DashboardVO)dashVOList.get(i);
					  
					    Float passed=Float.parseFloat(dashVO.getManualVO().getPassed())+Float.parseFloat(dashVO.getAutomationVO().getPassed());
					    Float failed=Float.parseFloat(dashVO.getManualVO().getFailed())+Float.parseFloat(dashVO.getAutomationVO().getFailed());
						Float noRun=Float.parseFloat(dashVO.getManualVO().getNoRun())+Float.parseFloat(dashVO.getAutomationVO().getNoRun());
						Float blocked=Float.parseFloat(dashVO.getManualVO().getBlocked())+Float.parseFloat(dashVO.getAutomationVO().getBlocked());
						Float deffered=Float.parseFloat(dashVO.getManualVO().getDefered())+Float.parseFloat(dashVO.getAutomationVO().getDefered());
						Float totalTC=passed+failed+noRun+blocked+deffered;
					    Float actual=passed+failed+blocked+deffered;
					    Float totalDefect=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getTotal());
					    if(totalDefect==0){
					    	defectDensity=(float) 0;
					    	trendReportsVO.setDefectDensity(defectDensity);
					    	badFix=(float) 0;
					    	trendReportsVO.setBadFix(badFix);
					    	accept=(float) 0;
					    	trendReportsVO.setDefectAcceptance(accept);
					    	dsi=(float) 0;
					    	trendReportsVO.setDefectSeverityIndex(dsi);
					    	
					    }
					    else{
						defectDensity=(float) (totalTC/totalDefect);
						trendReportsVO.setDefectDensity(defectDensity);
						/************************Reopened Defects*******************************/
						String reopened=(String) dashVO.getStatusAndSeverityVO().get(0).getTotal();
						badFix=Float.parseFloat(reopened)*100/totalDefect;
						log.info("Inside bad Fix "+ badFix);
						trendReportsVO.setBadFix(badFix);
						/******************************Acceptance Rate**************************/
						String reject=(String)dashVO.getStatusAndSeverityVO().get(2).getTotal();
						accept=1-(Float.parseFloat(reject)/totalDefect);
						trendReportsVO.setDefectAcceptance(accept);
						/***************************Defect Severity Index*****************************/
						Float urgent=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getUrgent());
						Float high=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getHigh());
						Float medium=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getMedium());
						Float low=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getLow());
						dsi=((urgent*urgent_4)+(high*high_3)+(medium*medium_2)+(low*low_1))/totalDefect;
						trendReportsVO.setDefectSeverityIndex(dsi);
					    }
						
						
						/*****************************Status Severity*******************************/
						String opened=(String) dashVO.getStatusAndSeverityVO().get(0).getTotal();
						String closed=(String) dashVO.getStatusAndSeverityVO().get(4).getTotal();
						trendReportsVO.setClosed(closed);
						trendReportsVO.setOpen(opened);
						
						
						
						/*************************Passed VS Failed**************************/
						trendReportsVO.setPass(passed);
						trendReportsVO.setFailed(failed);
						/*************************Defect Severity Break up******************************/
						trendReportsVO.setUrgent(dashVO.getStatusAndSeverityVO().get(5).getUrgent().toString());
						trendReportsVO.setHigh(dashVO.getStatusAndSeverityVO().get(5).getHigh().toString());
						trendReportsVO.setMedium(dashVO.getStatusAndSeverityVO().get(5).getMedium().toString());
						trendReportsVO.setLow(dashVO.getStatusAndSeverityVO().get(5).getLow().toString());
						/************************releasedate****************/
						String rDate=dashVO.getRdate();
						trendReportsVO.setRdate(rDate);
						/*************************planned VS actual**********/
						/*String plan=dashVO.getPlan();
						trendReportsVO.setPlan(plan);*/
						trendReportsVO.setActual(Float.toString(actual));
						originalList.add(trendReportsVO);
				     
				  }
				
				  log.info("******************************"+originalList);
				 response = Response.ok(originalList);
				//log.info(response);
				
			}
			
			catch (Exception e) {
				log.debug("Service layer in project data Layer");
				throw e;
				
			}
			return response.build();
		}
	@Override		
public Response getReleaseInfo(String project,String release,String fromDate,String toDate) throws Exception {
		
		
		Response.ResponseBuilder response =null;
		TrendReportsReleaseVO trendReportsReleaseVO=null;
		/***************************Defect RootCause******************************/
		int count_incorrectUnderstanding=0;
		int count_implementation=0;
		int count_automatedTestScript=0;
		int count_coding=0;
		int count_data=0;
		int count_datatable=0;
		int count_design=0;
		int count_hardwareDesign=0;
		int count_interface=0;
		int count_JCL=0;
		int count_manualTestScript=0;
		int count_requirements=0;
		/***************************Defect Type******************************/
		int count_Performance=0;
		int count_DataConversion=0;
		int count_deferresItem=0;
		int count_documentationReview=0;
		int count_Enhancement=0;
		int count_Environment=0;
		int count_functional=0;
		int count_testExecution=0;
		int count_unknown=0;
		
		
		
		HashMap rootCauseMap=new HashMap();
		HashMap defectTypeMap=new HashMap();
		try{
			ArrayList dashVOList=trendReportsDAO.getReleaseInfo(project, release,fromDate,toDate);
			log.info("DashVO List size "+dashVOList.size());
			log.info("DashVO list  "+dashVOList);
			ArrayList originalList=new ArrayList();
			
			DashboardVO dashVO=null;
			for (int i=0;i<dashVOList.size();i++)
			  {
				  
				  dashVO=(DashboardVO)dashVOList.get(i);
				  for(int j=0;j<dashVO.getDefectVO().size();j++)
				  {
					  trendReportsReleaseVO=new TrendReportsReleaseVO();
					  DefectIdsVO defectVO=dashVO.getDefectVO().get(j);
					  String rootCause=defectVO.getDefectRootCause();
					  log.info("rootCause is"+rootCause);
                       if(rootCause.equals("incorrectUnderstanding")){
                    	   count_incorrectUnderstanding++;
                       }                          
                    	   else if(rootCause.equals("implementation")){                               
					                   count_implementation++;
					     }
                    	   else if(rootCause.equals("coding")){
                    		           count_coding++;   
                    	   }
                           
                       String defectType=defectVO.getDefectType();
                       
                           if(defectType.equals("Performance")){
                    	           count_Performance++;
                           }
					                                 
                    	   else if(defectType.equals("DataConversion")){                               
                    		   count_DataConversion++;
					     }
                       
                      String defectId=defectVO.getDefectId();
                      trendReportsReleaseVO.setDefectId(defectId);
                      rootCauseMap.put("incorrectUnderstanding",count_incorrectUnderstanding);
     				  rootCauseMap.put("implementation", count_implementation); 
     				 rootCauseMap.put("coding", count_coding);
     				    
     				  
     				  defectTypeMap.put("Performance", count_Performance);
					  defectTypeMap.put("DataConversion", count_DataConversion);
					  
					  /*String defectRaisedDate=defectVO.getDefectFixedDate();
					  String defectFixedDate=defectVO.getDefectFixedDate();
					  
					  Date d1 = new SimpleDateFormat("yyyy-M-dd").parse(defectRaisedDate);
					  Date d2 = new SimpleDateFormat("yyyy-M-dd").parse(defectFixedDate);
					  
					  long diff = Math.abs(d1.getTime() - d2.getTime());
					  long diffDays = diff / (24 * 60 * 60 * 1000);*/
					
					  
					  
     				  trendReportsReleaseVO.setDefectRootCause(rootCauseMap);
     				  trendReportsReleaseVO.setDefectType(defectTypeMap);
     				  originalList.add(trendReportsReleaseVO);
                      
				  }
				  
				  
				
				
			}
			response=Response.ok(originalList);
	
      	}	catch (Exception e) {
	       log.debug("Service layer in project data Layer");
	       throw e;
      	}
	       return response.build();
	
}
}

	

		
	
	


