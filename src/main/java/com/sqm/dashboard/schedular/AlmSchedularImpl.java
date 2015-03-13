package com.sqm.dashboard.schedular;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mongodb.DBCollection;
import com.sqm.dashboard.dao.impl.DashboardDAOImpl;
import com.sqm.dashboard.schedular.impl.AlmSchedAuthServiceImpl;
import com.sqm.dashboard.schedular.impl.AlmSchedSchedularServiceImpl;
import com.sqm.dashboard.util.RestConnectorUtility;
import com.sqm.dashboard.util.TimeAnalyserUtility;

public class AlmSchedularImpl implements AlmSchedular {
	
	final Logger log = Logger.getLogger(AlmSchedularImpl.class);
	//final Log log = LogFactory.getLog(AlmSchedularImpl.class);
	
	/*@Autowired*/
	AlmSchedAuthServiceImpl almSchedAuthServiceImpl = new AlmSchedAuthServiceImpl();

	/*@Autowired(required=true)*/
	AlmSchedSchedularServiceImpl almSchedSchedularServiceImpl = new AlmSchedSchedularServiceImpl();
	
	private String almHost = "ealm11.jpmchase.net";
	private String almPort = "80";
	
	public final RestConnectorUtility conn = RestConnectorUtility.getInstance().init(new HashMap<String, String>(), almHost, almPort);
	
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
		try{
			log.info("##almSchedular##");
			log.info("almHost : " + almHost + " ## almPort : " + almPort);
			log.info("almSchedular startTime : " + startTime);
			log.info("Rest Connection : " + conn);
			
			String response = almSchedAuthServiceImpl.login(conn, username, password);
			log.info("Response AlmDomains : " + response);

			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept","application/xml");
			
			log.info("requestHeaders AlmDomains : " + requestHeaders);
			log.info("Cookies that contain LWSSO_COOKIE_KEY):" + conn.getCookieString());

			if (response.equals("200")) {
				DBCollection collection = DashboardDAOImpl.getDbCollection("alm");
				almSchedSchedularServiceImpl.saveAlmDetails(conn, requestHeaders, username, password, collection);
			} else if (response.equals("500")) {
				log.info("Login failed");
			}
		} catch (Exception e) {
				log.error("Login failed / Unable to get domains : " + e.getMessage());
				throw e;
		}
		TimeAnalyserUtility.calculateTime(startTime, "almSchedular");
	}
}
