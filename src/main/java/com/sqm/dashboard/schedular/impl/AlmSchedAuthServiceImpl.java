package com.sqm.dashboard.schedular.impl;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import Decoder.BASE64Encoder;

import com.sqm.dashboard.schedular.AlmSchedAuthService;
import com.sqm.dashboard.util.ResponseUtility;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedAuthServiceImpl")
public class AlmSchedAuthServiceImpl implements AlmSchedAuthService{
	
	final Logger log = Logger.getLogger(AlmSchedAuthServiceImpl.class);

	/**
	 * @param connection
	 * @param username
	 * @param password
	 * @return response status code
	 * @throws Exception
	 */
	@Override
	public String login(RestConnectorUtility conn, String username,
							String password) throws Exception {
		
		String authenticationPoint;
		try {
			authenticationPoint = this.isAuthenticated(conn);
			log.info("authenticationPoint : " + authenticationPoint);
			if (authenticationPoint != null) {
				return this.login(conn, authenticationPoint, username, password);
			}
			return "500";
		} catch (Exception e) {
			log.error("login failed : ", e);
			throw e;
		}
	}

	/**
	 * @param connection
	 * @param loginUrl
	 *            to authenticate at
	 * @param username
	 * @param password
	 * @return response
	 * @throws Exception
	 * 
	 *             logging in to our system is standard http login (basic
	 *             authentication), where one must store the returned cookies
	 *             for further use.
	 */
	/*
	 * @Override public String login(RestConnectorUtility conn, String loginUrl,
	 * String username, String password) throws Exception { try{ byte[]
	 * credBytes = (username + ":" + password).getBytes(); String
	 * credEncodedString = "Basic " + new BASE64Encoder().encode(credBytes);
	 * Map<String, String> map = new HashMap<String, String>();
	 * map.put("Authorization", credEncodedString); ResponseUtility response =
	 * conn.httpGet(loginUrl, null, map); //boolean ret =
	 * response.getStatusCode() == HttpURLConnection.HTTP_OK; String resp =
	 * response.getStatusCode() + ""; return resp; } catch (Exception e) {
	 * log.error("login failed : ", e); throw e; } }
	 */
	/**
	 * @return true if logout successful
	 * @throws Exception
	 *             close session on server and clean session cookies on client
	 */
	@Override
	public boolean logout(RestConnectorUtility conn) throws Exception {
		try {
			ResponseUtility response = conn.httpGet(conn.buildUrl("qcbin/authentication-point/logout"), null, null);
			return (response.getStatusCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			log.error("login failed : ", e);
			throw e;
		}
	}

	/**
	 * @return null if authenticated a url to authenticate against if not
	 *         authenticated.
	 * @throws Exception
	 */
	@Override
	public String isAuthenticated(RestConnectorUtility conn) throws Exception {
		String isAuthenticateUrl = conn.buildUrl("qcbin/rest/is-authenticated");
		log.info("isAuthenticateUrl : " + isAuthenticateUrl);

		String ret;
		ResponseUtility response = conn.httpGet(isAuthenticateUrl, null, null);
		int responseCode = response.getStatusCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			ret = null;
		}
		
		/**
		 * If not authenticated - get the address where to authenticate via
		 * WWW-Authenticate
		 */
		else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
			Iterable<String> authenticationHeader = response
					.getResponseHeaders().get("WWW-Authenticate");
			String newUrl = authenticationHeader.iterator().next().split("=")[1];
			newUrl = newUrl.replace("\"", "");
			newUrl += "/authenticate";
			ret = newUrl;
		}
		
		/**
		 * Not OK and not unauthorized - means some kind of error, like 404, or
		 * 500
		 */
		else {
			throw response.getFailure();
		}
		return ret;
	}

	@Override
	public String login(RestConnectorUtility conn, String loginUrl,
			String username, String password) throws Exception {

		try {

			byte[] credBytes = (username + ":" + password).getBytes();

			String credEncodedString = "Basic " + new BASE64Encoder().encode(credBytes);

			log.info("The Encoded credentails are : " + new String(credEncodedString));

			//String[] splited = credEncodedString.split("\\s");

			//String str = splited[1];
			//byte[] byteArray = Base64.decode(str.getBytes());
			//String credDecodedString = new String(byteArray);

			Map<String, String> map = new HashMap<String, String>();
			map.put("Authorization", credEncodedString);

			ResponseUtility response = conn.httpGet(loginUrl, null, map);
			String resp = response.getStatusCode() + "";
			log.info("resp : " + resp);
			return resp;
		} catch (Exception e) {
			log.error("login failed : ", e);
			throw e;
		}
	}

}
