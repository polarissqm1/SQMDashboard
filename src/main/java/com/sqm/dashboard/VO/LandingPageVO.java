package com.sqm.dashboard.VO;

import java.util.HashMap;

public class LandingPageVO {
	
	private HashMap manual;
	private HashMap automation;
	private HashMap statusAndSeverity;
	public HashMap getManual() {
		return manual;
	}
	public void setManual(HashMap manual) {
		this.manual = manual;
	}
	public HashMap getAutomation() {
		return automation;
	}
	public void setAutomation(HashMap automation) {
		this.automation = automation;
	}
	public HashMap getStatusAndSeverity() {
		return statusAndSeverity;
	}
	public void setStatusAndSeverity(HashMap statusAndSeverity) {
		this.statusAndSeverity = statusAndSeverity;
	}
    
}
