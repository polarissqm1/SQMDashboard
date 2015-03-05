package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

public class TrendReportsVO implements Serializable {
	
	private Float defectDensity;
	public Float getDefectDensity() {
		return defectDensity;
	}
	public void setDefectDensity(Float defectDensity) {
		this.defectDensity = defectDensity;
	}
	public Float getDefectSeverityIndex() {
		return defectSeverityIndex;
	}
	public void setDefectSeverityIndex(Float defectSeverityIndex) {
		this.defectSeverityIndex = defectSeverityIndex;
	}
	public Float getBadFix() {
		return badFix;
	}
	public void setBadFix(Float badFix) {
		this.badFix = badFix;
	}
	public String getDefectAgeing() {
		return defectAgeing;
	}
	public void setDefectAgeing(String defectAgeing) {
		this.defectAgeing = defectAgeing;
	}
	public String getDefectSeverityBrkp() {
		return defectSeverityBrkp;
	}
	public void setDefectSeverityBrkp(String defectSeverityBrkp) {
		this.defectSeverityBrkp = defectSeverityBrkp;
	}
	public DashboardVO getDashboardVO() {
		return dashboardVO;
	}
	public void setDashboardVO(DashboardVO dashboardVO) {
		this.dashboardVO = dashboardVO;
	}
	private Float defectSeverityIndex;
	private Float badFix;
	private String defectAgeing;
	private String defectSeverityBrkp;
	private String pass;
	private String failed;
	private String rdate;
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getFailed() {
		return failed;
	}
	public void setFailed(String failed) {
		this.failed = failed;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getClosed() {
		return closed;
	}
	public void setClosed(String closed) {
		this.closed = closed;
	}
	private Float defectAcceptance;
	private String open;
	private String closed;
	private String urgent;
	private String high;
	private String medium;
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
	private String low;
	public Float getDefectAcceptance() {
		return defectAcceptance;
	}
	public void setDefectAcceptance(Float defectAcceptance) {
		this.defectAcceptance = defectAcceptance;
	}
	private DashboardVO dashboardVO;
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	
	

}
