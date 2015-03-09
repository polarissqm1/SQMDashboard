package com.sqm.dashboard.schedular;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sqm.dashboard.schedular.*;
import com.sqm.dashboard.schedular.impl.DashboardAuthServiceImpl;
import com.sqm.dashboard.schedular.impl.DashboardSchedularServiceImpl;
import com.sqm.dashboard.util.RestConnectorUtility;
import com.sqm.dashboard.util.TimeAnalyserUtility;

public class AlmSchedularImpl implements AlmSchedular {
	/*@Autowired*/
	DashboardAuthServiceImpl dbAuthServiceImpl = new DashboardAuthServiceImpl();

	/*@Autowired(required=true)*/
	DashboardSchedularServiceImpl dbSchedularServiceImpl = new DashboardSchedularServiceImpl();
	
	private String almHost = "ealm11.jpmchase.net";
	private String almPort = "80";
	
	public final RestConnectorUtility conn = RestConnectorUtility.getInstance().init(new HashMap<String, String>(), almHost, almPort);
	
	final Logger log = Logger.getLogger(AlmSchedularImpl.class);
	@Override
	public void startAlmInsert(AlmSchedularImpl almSchedular) throws Exception {
		try{
		
		almAuthentication("murlikrishnamohan.kakarla", "Welcome07$");
		}catch(Exception e){
			log.error("Exception Alm Satrt level");
			throw e;
		}
		

	}
	
	public void almAuthentication(String username, String password) throws Exception{
		
		long startTime=System.currentTimeMillis();
		Response jsonDomains = null;
		try{
		log.info("##almSchedular##");
		log.info("almHost : " + almHost + " ## almPort : " + almPort);
		
		
		log.info("almSchedular startTime : " + startTime);
		
		
		HashMap<String,String> jsonDomainsMap = new HashMap<String,String>();
		HashMap<String,String> jsonProjectsMap = null;
		HashMap<String,String> jsonNo = new HashMap<String,String>();
		
		
		
			log.info("Rest Connection : " + conn);
			
			String response = dbAuthServiceImpl.login(conn, username, password);
			log.info("Response AlmDomains : " + response);

			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept","application/xml");
			
			log.info("requestHeaders AlmDomains : " + requestHeaders);
			log.info("Cookies that contain LWSSO_COOKIE_KEY):" + conn.getCookieString());

			if (response.equals("200")) {
				String schedular = dbSchedularServiceImpl.saveAlmDetails(conn, requestHeaders, username, password);
			} else if (response.equals("500")) {
				log.info("Login failed");
			}
		} catch (Exception e) {
				log.error("Login failed / Unable to get domains : " + e.getMessage());
				throw e;
		}
		TimeAnalyserUtility.calculateTime(startTime, "almSchedular");
		//return jsonDomains;
	}}
