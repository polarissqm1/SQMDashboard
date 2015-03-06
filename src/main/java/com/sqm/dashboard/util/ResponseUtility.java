package com.sqm.dashboard.util;

import java.util.Map;

public class ResponseUtility {
	private Map<String, ? extends Iterable<String>> responseHeaders = null;
	private byte[] responseData = null;
	private Exception failure = null;
	private int statusCode = 0;

	
	/**
	 * 
	 * @param responseHeaders
	 * @param responseData
	 * @param failure
	 * @param statusCode
	 */
	public ResponseUtility(Map<String, Iterable<String>> responseHeaders,
			byte[] responseData, Exception failure, int statusCode) {
		super();
		this.responseHeaders = responseHeaders;
		this.responseData = responseData;
		this.failure = failure;
		this.statusCode = statusCode;
	}

	
	/**
	 * DefaultConstructor of Class Response
	 */
	public ResponseUtility() {
	}

	
	/**
	 * 
	 * @return responseHeaders
	 */
	public Map<String, ? extends Iterable<String>> getResponseHeaders() {
		return responseHeaders;
	}

	
	/**
	 * 
	 * @param responseHeaders
	 */
	public void setResponseHeaders(
			Map<String, ? extends Iterable<String>> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	
	/**
	 * 
	 * @return responseData
	 */
	public byte[] getResponseData() {
		return responseData;
	}

	
	/**
	 * 
	 * @param responseData
	 */
	public void setResponseData(byte[] responseData) {
		this.responseData = responseData;
	}

	
	/**
	 * 
	 * @return Exception in case of any failure
	 */
	public Exception getFailure() {
		return failure;
	}
	
	/**
	 * 
	 * @param failure
	 */

	public void setFailure(Exception failure) {
		this.failure = failure;
	}

	
	/**
	 * 
	 * @return statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	
	/**
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return new String(this.responseData);
	}
}