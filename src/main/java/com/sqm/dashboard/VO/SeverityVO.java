package com.sqm.dashboard.VO;

import java.io.Serializable;

public class SeverityVO implements Serializable {

	
	private String urgent;
	private String high;
	private String low;
	public String getUrgent() {
		return urgent;
	}
	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}
	@Override
	public String toString() {
		return "SeverityVO [urgent=" + urgent + ", high=" + high + ", low="
				+ low + ", medium=" + medium + "]";
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	private String medium;

}
