package com.sqm.dashboard.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class RestConnectorUtility {
	
	final Logger log = Logger.getLogger(RestConnectorUtility.class);
	
	protected Map<String, String> cookies;
	protected String host;
	protected String port;
	
	
	
	/**
	 * 
	 * @param cookies
	 * @param host
	 * @param port
	 * @return
	 */

	public RestConnectorUtility init(Map<String, String> cookies, String host,
			String port) {
		this.cookies = cookies;
		this.host = host;
		this.port = port;
		return this;
	}

	
	/**
	 * DefaultConstructor for Class RestConnector
	 */
	private RestConnectorUtility() {
	}

	private static RestConnectorUtility instance = new RestConnectorUtility();

	
	/**
	 * 
	 * @return instance of RestConnector Class
	 */
	public static RestConnectorUtility getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param entityType
	 * @return Url of type String
	 */

	public String buildEntityCollectionUrl(String entityType, String domain, String project) {
		return buildUrl("qcbin/rest/domains/" + domain + "/projects/" + project
				+ "/" + entityType + "s");
	}
	
	public String buildUrl(String path) {
		return String.format("http://%1$s:%2$s/%3$s", host, port, path);
	}
	
	/**
	 * 
	 * @return Map collection of Cookies
	 */

	public Map<String, String> getCookies() {
		return cookies;
	}

	
	/**
	 * 
	 * @param cookies
	 */
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	
	/**
	 * 
	 * @return host
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * 
	 * @param host
	 */

	public void setHost(String host) {
		this.host = host;
	}

	
	/**
	 * @return port
	 */
	public String getPort() {
		return port;
	}

	
	/**
	 * 
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	
	
	/**
	 * 
	 * @param url
	 * @param queryString
	 * @param headers
	 * @return response
	 * @throws Exception
	 */
	public ResponseUtility httpGet(String url, String queryString,
			Map<String, String> headers) throws Exception {
		return doHttp("GET", url, queryString, null, headers, cookies);
	}

	
	/**
	 * 
	 * @param type
	 * @param url
	 * @param queryString
	 * @param data
	 * @param headers
	 * @param cookies
	 * @return response
	 * @throws Exception
	 */
	private ResponseUtility doHttp(String type, String url, String queryString,
			byte[] data, Map<String, String> headers,
			Map<String, String> cookies) throws Exception {
		if ((queryString != null) && !queryString.isEmpty()) {
			url += "?" + queryString;
			System.out.println("URL : " + url);
			log.info("URL : " + url);
		}
		HttpURLConnection con = (HttpURLConnection) new URL(url)
				.openConnection();
		con.setRequestMethod(type);
		String cookieString = getCookieString();
		prepareHttpRequest(con, headers, data, cookieString);
		con.connect();
		
		ResponseUtility ret = retrieveHtmlResponse(con);
		updateCookies(ret);
		return ret;
	}
	
	/**
	 * 
	 * @param con
	 * @param headers
	 * @param bytes
	 * @param cookieString
	 * @throws IOException
	 */

	private void prepareHttpRequest(HttpURLConnection con,
			Map<String, String> headers, byte[] bytes, String cookieString)
			throws IOException {
		String contentType = null;
		if ((cookieString != null) && !cookieString.isEmpty()) {
			con.setRequestProperty("Cookie", cookieString);
		}
		if (headers != null) {
			contentType = headers.remove("Content-Type");
			Iterator<Entry<String, String>> headersIterator = headers
					.entrySet().iterator();
			while (headersIterator.hasNext()) {
				Entry<String, String> header = headersIterator.next();
				con.setRequestProperty(header.getKey(), header.getValue());
			}
		}
		if ((bytes != null) && (bytes.length > 0)) {
			con.setDoOutput(true);
			if (contentType != null) {
				con.setRequestProperty("Content-Type", contentType);
			}
			OutputStream out = con.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
		}
	}

	
	/**
	 * 
	 * @param con
	 * @return response
	 * @throws Exception
	 */
	private ResponseUtility retrieveHtmlResponse(HttpURLConnection con)
			throws Exception {
		ResponseUtility ret = new ResponseUtility();
		ret.setStatusCode(con.getResponseCode());
		ret.setResponseHeaders(con.getHeaderFields());
		InputStream inputStream;
		try {
			inputStream = con.getInputStream();
		}
		catch (Exception e) {
			inputStream = con.getErrorStream();
			ret.setFailure(e);
		}
		ByteArrayOutputStream container = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int read;
		while ((read = inputStream.read(buf, 0, 1024)) > 0) {
			container.write(buf, 0, read);
		}
		ret.setResponseData(container.toByteArray());
		return ret;
	}

	
	/**
	 * 
	 * @param response
	 */
	private void updateCookies(ResponseUtility response) {
		Iterable<String> newCookies = response.getResponseHeaders().get(
				"Set-Cookie");
		if (newCookies != null) {
			for (String cookie : newCookies) {
				int equalIndex = cookie.indexOf('=');
				int semicolonIndex = cookie.indexOf(';');
				String cookieKey = cookie.substring(0, equalIndex);
				String cookieValue = cookie.substring(equalIndex + 1,
						semicolonIndex);
				cookies.put(cookieKey, cookieValue);
			}
		}
	}

	
	/**
	 * 
	 * @return Cookies
	 */
	public String getCookieString() {
		StringBuilder sb = new StringBuilder();
		if (!cookies.isEmpty()) {
			Set<Entry<String, String>> cookieEntries = cookies.entrySet();
			for (Entry<String, String> entry : cookieEntries) {
				sb.append(entry.getKey()).append("=").append(entry.getValue())
						.append(";");
			}
		}
		String ret = sb.toString();
		return ret;
	}

	
	/**
	 * 
	 * @param xml
	 * @return json
	 */
   public String XMLtoJSON(String xml) {
           JSONObject jsonObj;
           String json="";
		try {
			jsonObj = XML.toJSONObject(xml);
			json = jsonObj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
       }
   
   /**
    * 
    * @param xml
    * @return json object
    */
   
   public JSONObject XMLtoJSONObject(String xml) {
       JSONObject jsonObj = null;
       try {
		jsonObj = XML.toJSONObject(xml);
       } catch (JSONException e) {
		e.printStackTrace();
       }
       return jsonObj;
   }
}
