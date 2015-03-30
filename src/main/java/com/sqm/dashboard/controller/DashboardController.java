package com.sqm.dashboard.controller;


import java.util.List;









import com.sqm.dashboard.VO.UserVO;
import com.sqm.dashboard.service.DashboardService;
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
@RequestMapping("/dashboard")
public class DashboardController {

	
	static final Logger log=Logger.getLogger(DashboardController.class);
	@Autowired
	private DashboardService dashboardService;

	//, produces = "application/json"
	
	
	@RequestMapping(value = "/getLandingInfo", method = RequestMethod.GET )
	public @ResponseBody Response  getLandingInfo(@RequestParam(value="projectName") String project,
			@RequestParam(value="releaseName") String release){
		
		try{
		
		log.info("project :"+project);
		log.info("Release :"+release);
		
		log.info("Inside getLandingInfo method :");
		
		//log.debug(dashboardService);
		return dashboardService.getLandingInfo(project,release);
           } 
		
		catch (Exception e) {
			
			log.error("Exception ocurred : ",e);
			return null;
		}
		
		
	}
	
	

	@RequestMapping(value = "/getApplicationsList", method = RequestMethod.GET )
	public @ResponseBody Response  getApplicationsList(){
		
		try{
		
		
		
		
		
		//log.debug(dashboardService);
		return dashboardService.getApplicationsList();
           } 
		
		catch (Exception e) {
			
			log.error("Exception ocurred : ",e);
			return null;
		}
		
		
	}
	
	
	@RequestMapping(value = "/getReleaseList", method = RequestMethod.GET )
	public @ResponseBody Response  getReleaseList(@RequestParam(value="projectName") String project ){
		
		try{
		
		
		
		
		
		//log.debug(dashboardService);
		return dashboardService.getReleaseList(project);
           } 
		
		catch (Exception e) {
			
			log.error("Exception ocurred : ",e);
			return null;
		}
		
		
	}


}