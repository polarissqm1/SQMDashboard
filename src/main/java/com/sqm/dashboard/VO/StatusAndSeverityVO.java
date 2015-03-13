package com.sqm.dashboard.VO;

import java.io.Serializable;

public class StatusAndSeverityVO implements Serializable {
	
	private String statusSeverity;
	private String Urgent;
	private String High;
	private String Medium;
	private String Low;
	private String Total;
	public String getStatusSeverity() {
		return statusSeverity;
	}
	public void setStatusSeverity(String statusSeverity) {
		this.statusSeverity = statusSeverity;
	}
	public String getUrgent() {
		return Urgent;
	}
	public void setUrgent(String urgent) {
		Urgent = urgent;
	}
	public String getHigh() {
		return High;
	}
	public void setHigh(String high) {
		High = high;
	}
	public String getMedium() {
		return Medium;
	}
	public void setMedium(String medium) {
		Medium = medium;
	}
	public String getLow() {
		return Low;
	}
	public void setLow(String low) {
		Low = low;
	}
	public String getTotal() {
		return Total;
	}
	public void setTotal(String total) {
		Total = total;
	}

}
