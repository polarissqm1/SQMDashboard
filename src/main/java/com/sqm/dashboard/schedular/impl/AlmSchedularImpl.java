package com.sqm.dashboard.schedular.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.dao.impl.DashboardDAOImpl;
import com.sqm.dashboard.schedular.AlmSchedular;
import com.sqm.dashboard.util.RestConnectorUtility;
import com.sqm.dashboard.util.TimeAnalyserUtility;

@Service("almSchedularImpl")
public class AlmSchedularImpl implements AlmSchedular {
	
	final Logger log = Logger.getLogger(AlmSchedularImpl.class);
	
	@Autowired
	private AlmSchedAuthServiceImpl almSchedAuthServiceImpl;

	@Autowired
	private AlmSchedularServiceImpl almSchedSchedularServiceImpl;
	
	@Value("${almHost}")
	private String almHost;
	
	@Value("${almPort}")
	private String almPort;
	
	@Value("${almUser}")
	private String almUser;
	
	@Value("${almPwd}")
	private String almPwd;
	
	@Value("${almCollectionAlm}")
	private String almCollectionAlm;
	
	public void startAlmInsert(AlmSchedularImpl almSchedularImpl) throws Exception {
		try{
			log.info("Inside startAlmInsert");
			almSchedularImpl.almAuthentication(almUser, almPwd);
		}catch(Exception e){
			log.error("Exception Alm Start level");
			throw e;
		}
	}
	
	public void almAuthentication(String username, String password) throws Exception{
		
		long startTime = System.currentTimeMillis();
		final RestConnectorUtility conn = RestConnectorUtility.getInstance().init(new HashMap<String, String>(), almHost, almPort);
		
		try{
			log.info("##AlmSchedular##");
			log.info("almHost : " + almHost + " # almPort : " + almPort);
			log.info("almSchedular startTime : " + startTime);
			log.info("Rest Connection : " + conn);
			
			String response = almSchedAuthServiceImpl.login(conn, username, password);
			log.info("Response AlmDomains : " + response);

			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept","application/xml");
			
			log.info("requestHeaders AlmDomains : " + requestHeaders);
			log.info("Cookies that contain LWSSO_COOKIE_KEY):" + conn.getCookieString());

			if (response.equals("200")) {
				DBCollection collection = DashboardDAOImpl.getDbCollection(almCollectionAlm);
				almSchedSchedularServiceImpl.saveAlmDetails(conn, requestHeaders, username, password, collection);
			} else if (response.equals("500")) {
				log.info("Login failed");
			}
		} catch (Exception e) {
				log.error("Login failed / Unable to get domains : " + e.getMessage());
				throw e;
		}
		TimeAnalyserUtility.calculateTime(startTime, "AlmSchedular");
	}
}
