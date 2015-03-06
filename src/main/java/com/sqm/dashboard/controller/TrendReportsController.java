package com.sqm.dashboard.controller;


import java.util.List;









import com.sqm.dashboard.VO.UserVO;
import com.sqm.dashboard.service.DashboardService;
import com.sqm.dashboard.service.TrendReportsService;
import com.sqm.dashboard.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.Response;


@Controller
@RequestMapping("/trendreports")
public class TrendReportsController {

	
	static final Logger log=Logger.getLogger(TrendReportsController.class);
	@Autowired
	private TrendReportsService trendReportsService;

	//, produces = "application/json"
	
	
	@RequestMapping(value = "/getTrendingInfo", method = RequestMethod.GET )
	public @ResponseBody Response  getTrendingInfo(@RequestParam(value="projectName") String project,
			@RequestParam(value="releaseName") String release,@RequestParam(value="fromDate") String fromDate,@RequestParam(value="toDate") String toDate){
		
		try{
		
		log.info("project :"+project);
		log.info("Release :"+release);
		
		log.info("Inside getTrendingInfo method :");
		System.out.println("Inside getTrendingInfo method");
		//log.debug(dashboardService);
		return trendReportsService.getTrendingInfo(project,release,fromDate,toDate);
           } 
		
		catch (Exception e) {
			
			log.error("Exception ocurred : ",e);
			return null;
		}
		
		
	}
	
	

}