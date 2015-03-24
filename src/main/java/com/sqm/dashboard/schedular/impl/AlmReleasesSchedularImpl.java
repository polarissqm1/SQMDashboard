package com.sqm.dashboard.schedular.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.dao.impl.DashboardDAOImpl;
import com.sqm.dashboard.schedular.AlmReleasesSchedular;
import com.sqm.dashboard.util.RestConnectorUtility;
import com.sqm.dashboard.util.TimeAnalyserUtility;

@Service("almReleasesSchedularImpl")
public class AlmReleasesSchedularImpl implements AlmReleasesSchedular {

	final Logger log = Logger.getLogger(AlmReleasesSchedularImpl.class);
	
	@Autowired
	private AlmSchedAuthServiceImpl almSchedAuthServiceImpl;

	@Autowired
	private AlmReleasesSchedularServiceImpl almReleasesSchedularServiceImpl;
	
	@Value("${almHost}")
	private String almHost;
	
	@Value("${almPort}")
	private String almPort;
	
	@Value("${almUser}")
	private String almUser;
	
	@Value("${almPwd}")
	private String almPwd;
	
	@Value("${almCollectionRelease}")
	private String almCollectionRelease;
	
	public void startAlmReleasesInsert(AlmReleasesSchedularImpl almReleasesSchedularImpl) throws Exception {
		try{
			almReleasesSchedularImpl.almReleasesAuthentication(almUser, almPwd);
		}catch(Exception e){
			log.error("Exception Alm Start level");
			throw e;
		}
	}

	public void almReleasesAuthentication(String username, String password) throws Exception {
		
		long startTime = System.currentTimeMillis();
		final RestConnectorUtility conn = RestConnectorUtility.getInstance().init(new HashMap<String, String>(), almHost, almPort);
		try {
			log.info("##AlmReleasesSchedular##");
			log.info("almHost : " + almHost + " ## almPort : " + almPort);
			log.info("AlmReleasesSchedular startTime : " + startTime);
			log.info("Rest Connection : " + conn);
			
			String response = almSchedAuthServiceImpl.login(conn, username, password);
			log.info("Response AlmDomains : " + response);

			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept","application/xml");
			
			if (response.equals("200")) {
				DBCollection collection = DashboardDAOImpl.getDbCollection(almCollectionRelease);
				almReleasesSchedularServiceImpl.saveReleasesDetails(conn, requestHeaders, username, password, collection);
			} else if (response.equals("500")) {
				log.info("Authentication failed");
			}
		} catch (Exception e) {
				log.error("Authentication failed : " + e.getMessage());
				throw e;
		}
		TimeAnalyserUtility.calculateTime(startTime, "AlmReleasesSchedular");
	}	
}