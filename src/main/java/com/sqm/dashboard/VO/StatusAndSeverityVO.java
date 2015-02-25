package com.sqm.dashboard.VO;

import java.io.Serializable;

public class StatusAndSeverityVO implements Serializable {
	
	private String statusSeverity;
	private String urgent;
	private String high;
	private String medium;
	private String low;
	private String Total;
public String getTotal() {
		return Total;
	}
	public void setTotal(String total) {
		Total = total;
	}
	/*	private String total;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}*/
	public String getStatusSeverity() {
		return statusSeverity;
	}
	public void setStatusSeverity(String statusSeverity) {
		this.statusSeverity = statusSeverity;
	}
	public String getUrgent() {
		return urgent;
	}
	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	
}
