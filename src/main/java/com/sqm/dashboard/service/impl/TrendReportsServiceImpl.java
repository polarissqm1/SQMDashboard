package com.sqm.dashboard.service.impl;

import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.joda.time.DateTime;
import org.joda.time.Days;
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
						Float deffered=Float.parseFloat(dashVO.getManualVO().getDeferred())+Float.parseFloat(dashVO.getAutomationVO().getDeferred());
						/*Float deffered=Float.parseFloat(dashVO.getManualVO().getDeferred());*/
						Float totalTC=passed+failed+noRun+blocked+deffered;
					    Float actual=passed+failed+blocked+deffered;
					    Float totalDefect=Float.parseFloat(dashVO.getStatusAndSeverityVO().get(5).getTotal());
					    if(totalDefect==0){
					    	badFix=(float) 0;
					    	trendReportsVO.setBadFix(badFix);
					    	accept=(float) 0;
					    	trendReportsVO.setDefectAcceptance(accept);
					    	dsi=(float) 0;
					    	trendReportsVO.setDefectSeverityIndex(dsi);
					    	
					    }
					    else{
						
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
					    
					    if(totalTC==0){
					    	defectDensity=(float) 0;
					    	trendReportsVO.setDefectDensity(defectDensity);
					    	
					    }
					    else{
					    	defectDensity=(float) (totalDefect/totalTC);
							trendReportsVO.setDefectDensity(defectDensity);
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
		int count_TestScript=0;
		int count_requirements=0;
		int count_Environment_root=0;
		int count_userError=0;
		/***************************Defect Type******************************/
		int count_Performance=0;
		int count_DataConversion=0;
		int count_deferresItem=0;
		int count_Others=0;
		int count_Enhancement=0;
		int count_Environment=0;
		int count_functional=0;
		int count_testExecution=0;
		int count_inquiry=0;
		int count_unknown=0;
		int count_ID=0;
		int count_UI=0;
		/**********************************Defect Ageing*************************/
		int urgent_1D=0;
		int high_1D=0;
		int medium_1D=0;
		int low_1D=0;
		
		int urgent_4D=0;
		int high_4D=0;
		int medium_4D=0;
		int low_4D=0;
		
		int urgent_final=0;
		int high_final=0;
		int medium_final=0;
		int low_final=0;
		
		
		int urgent_1D_Open=0;
		int high_1D_Open=0;
		int medium_1D_Open=0;
		int low_1D_Open=0;
		
		int urgent_4D_Open=0;
		int high_4D_Open=0;
		int medium_4D_Open=0;
		int low_4D_Open=0;
		
		int urgent_final_Open=0;
		int high_final_Open=0;
		int medium_final_Open=0;
		int low_final_Open=0;
		
		Date date=new Date();
		HashMap rootCauseMap=new HashMap();
		HashMap defectTypeMap=new HashMap();
		HashMap oneDayToFour=new HashMap();
		HashMap fourToEight=new HashMap();
		HashMap greaterEight=new HashMap();
		HashMap lessThanFive_Open=new HashMap();
		HashMap fiveToTen_Open=new HashMap();
		HashMap greaterTen_Open=new HashMap();
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
					  if(defectVO.getDefectStatus().equalsIgnoreCase("Closed")){
						  
						  int fixTime=Integer.parseInt(defectVO.getDefectFixTime());
						  if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("1 - Urgent")){
							  urgent_1D++;
						  }
						  else if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("2 - High")){
							  high_1D++;
						  }
						  else if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("3 - Medium")){
							  
							  medium_1D++;
						  }
                              else if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("4 - Low")){
							  
                            	  low_1D++;
						  }
                              else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("1 - Urgent")){
    							  
                            	  urgent_4D++;
						  }
                                else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("2 - High")){
    							  
                                	high_4D++;
						  }
                                else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("3 - Medium")){
      							  
                                	medium_4D++;
  						  }
                                else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("4 - Low")){
        							  
                                	low_4D++;
  						  }
						  
                                else if(defectVO.getDefectSeverity().equalsIgnoreCase("4 - Low")){
                                	
                                	low_final++;
                                	
                                }
						  
                                else if(defectVO.getDefectSeverity().equalsIgnoreCase("3 - Medium")){
                                	
                                	medium_final++;
                                	
                                }
                               else if(defectVO.getDefectSeverity().equalsIgnoreCase("2 - High")){
                                	
                            	   high_final++;
                                	
                                }
                               else if(defectVO.getDefectSeverity().equalsIgnoreCase("1 - Urgent")){
 	
                            	   urgent_final++;
 	
                                     }
						  
					/**********************************Root Cause******************************************************/
							 
                       if(rootCause.equalsIgnoreCase("Implementation")||rootCause.equalsIgnoreCase("Design")||rootCause.equalsIgnoreCase("Coding")||rootCause.equalsIgnoreCase("Incorrect Understanding")){                               
					                   count_implementation++;
					        }
                    	   
                    	  
                    	   else if(rootCause.equalsIgnoreCase("Data")||rootCause.equalsIgnoreCase("Data Table")){
                    		   count_data++;   
            	               }
                    	   
                    	  
                    	   else if(rootCause.equalsIgnoreCase("Manual Test Script")||rootCause.equalsIgnoreCase("Automated Test Script")||rootCause.equalsIgnoreCase("JCL")){
                    		   count_TestScript++;   
            	               }
                    	   else if(rootCause.equalsIgnoreCase("Requirements")){
                    		   count_requirements++;   
            	               }
                    	   else if(rootCause.equalsIgnoreCase("Environment")||rootCause.equalsIgnoreCase("Hardware/Infrastructure")||rootCause.equalsIgnoreCase("Interface")){
                    		   count_Environment_root++;
                    		   
                    	   }
                    	   else if(rootCause.equalsIgnoreCase("User Error/Not a Defect")){
                    		   count_userError++;
                    	   }
                    	   		
					  }
					  
					  
					  /**************************************Open Defects**********************************************/
					  else if(defectVO.getDefectStatus().equals("New")||(defectVO.getDefectStatus()).equals("Re-Opened")||defectVO.getDefectStatus().equals("Open")){
						    String raisedDate = defectVO.getDefectRaisedDate();
							SimpleDateFormat formated = new SimpleDateFormat("yyyy-MM-dd");
							Date raisedate = formated.parse(raisedDate);
							log.info("raisedDate is "+raisedate);
							DateTime defRaisedDate=new DateTime(raisedate);
							DateTime todayDate=new DateTime(date);
							int fixTime=Math.abs(Days.daysBetween(todayDate, defRaisedDate).getDays());
							log.info("Fixtime is"+ fixTime);
							 if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("1 - Urgent")){
								 urgent_1D_Open++;
							  }
							  else if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("2 - High")){
								  high_1D_Open++;
							  }
							  else if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("3 - Medium")){
								  
								  medium_1D_Open++;
							  }
	                              else if(((1<=fixTime)&&(fixTime<=5))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("4 - Low")){
								  
	                            	  low_1D_Open++;
							  }
	                              else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("1 - Urgent")){
	    							  
	                            	  urgent_4D_Open++;
							  }
	                                else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("2 - High")){
	    							  
	                                	high_4D_Open++;
							  }
	                                else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("3 - Medium")){
	      							  
	                                	medium_4D_Open++;
	  						  }
	                                else if(((6<=fixTime)&&(fixTime<=10))&& (defectVO.getDefectSeverity()).equalsIgnoreCase("4 - Low")){
	        							  
	                                	low_4D_Open++;
	  						  }
							  
	                                else if(defectVO.getDefectSeverity().equalsIgnoreCase("4 - Low")){
	                                	
	                                	low_final_Open++;
	                                	
	                                }
							  
	                                else if(defectVO.getDefectSeverity().equalsIgnoreCase("3 - Medium")){
	                                	
	                                	medium_final_Open++;
	                                	
	                                }
	                               else if(defectVO.getDefectSeverity().equalsIgnoreCase("2 - High")){
	                                	
	                            	   high_final_Open++;
	                                	
	                                }
	                               else if(defectVO.getDefectSeverity().equalsIgnoreCase("1 - Urgent")){
	 	
	                            	   urgent_final_Open++;
	 	
	                                     }
                       
					  }
					  
					  /**************************************Defect Type*******************************************/
                       String defectType=defectVO.getDefectType();
                      if(defectType.equalsIgnoreCase("Performance")){
                    	           count_Performance++;
                           }
					       
                    	   else if(defectType.equalsIgnoreCase("Documentation Review")||defectType.equalsIgnoreCase("Data Conversion")||defectType.equals("ID")||defectType.equals("Unknown")||defectType.equalsIgnoreCase("Deferred Item")){                               
                    		   count_Others++;
					          }
                    	   else if(defectType.equals("Enhancement")){                               
                    		   count_Enhancement++;
					          }
                    	   else if(defectType.equals("Environment")){                               
                    		   count_Environment++;
					          }
                    	   else if(defectType.equals("Functional")||defectType.equals("Inquiry")||defectType.equals("UI")){                               
                    		   count_functional++;
					          }
                    	   else if(defectType.equals("Test Execution")){                               
                    		   count_testExecution++;
					          }
                    String defectId=defectVO.getDefectId();
                      trendReportsReleaseVO.setDefectId(defectId);
                    }
				  
 				  rootCauseMap.put("implementation", count_implementation); 
 				  rootCauseMap.put("Data", count_data);
 				  rootCauseMap.put("TestScript", count_TestScript);
 				  rootCauseMap.put("Requirements", count_requirements);
 				  rootCauseMap.put("Environment", count_Environment_root);
 				  rootCauseMap.put("UserError_NotaDefect", count_userError);
 				  
 				  defectTypeMap.put("Performance", count_Performance);
				  defectTypeMap.put("others", count_Others);
				  defectTypeMap.put("Enhancement", count_Enhancement);
				  defectTypeMap.put("Environment", count_Environment);
				  defectTypeMap.put("Functional", count_functional);
				  defectTypeMap.put("TestExecution", count_testExecution);
				  
				  oneDayToFour.put("Urgent", urgent_1D);
				  oneDayToFour.put("High", high_1D);
				  oneDayToFour.put("Medium", medium_1D);
				  oneDayToFour.put("Low", low_1D);
				  
				  fourToEight.put("Urgent", urgent_4D);
				  fourToEight.put("High", high_4D);
				  fourToEight.put("Medium", medium_4D);
				  fourToEight.put("Low", low_4D);
				  
				  greaterEight.put("Urgent", urgent_final);
				  greaterEight.put("Medium", medium_final);
				  greaterEight.put("High", high_final);
				  greaterEight.put("Low", low_final);
				  
				  lessThanFive_Open.put("Urgent", urgent_1D_Open);
				  lessThanFive_Open.put("High", high_1D_Open);
				  lessThanFive_Open.put("Medium", medium_1D_Open);
				  lessThanFive_Open.put("Low", low_1D_Open);
				  
				  fiveToTen_Open.put("Urgent", urgent_4D_Open);
				  fiveToTen_Open.put("High", high_4D_Open);
				  fiveToTen_Open.put("Medium", medium_4D_Open);
				  fiveToTen_Open.put("Low", low_4D_Open);
				  
				  greaterTen_Open.put("Urgent", urgent_final_Open);
				  greaterTen_Open.put("High", high_final_Open);
				  greaterTen_Open.put("Medium", medium_final_Open);
				  greaterTen_Open.put("Low", low_final_Open);
				
				  String plan=dashVO.getPlan();
                  trendReportsReleaseVO.setPlanned(plan);
				  trendReportsReleaseVO.setOneDayToFour(oneDayToFour);
				  trendReportsReleaseVO.setFourToEight(fourToEight);
				  trendReportsReleaseVO.setGreaterEight(greaterEight);
 				  trendReportsReleaseVO.setDefectRootCause(rootCauseMap);
 				  trendReportsReleaseVO.setDefectType(defectTypeMap);
 				  trendReportsReleaseVO.setLessThanFive_Open(lessThanFive_Open);
 				  trendReportsReleaseVO.setFiveToTen_Open(fiveToTen_Open);
 				  trendReportsReleaseVO.setGreaterTen_Open(greaterTen_Open);
 				  originalList.add(trendReportsReleaseVO);
  			}
			response=Response.ok(originalList);
	
      	}	catch (Exception e) {
	       log.debug("Service layer in project data Layer");
	       throw e;
      	}
	       return response.build();
	
}
}
