package com.sqm.dashboard.VO;

import java.io.Serializable;

public class EffortsVO implements Serializable {

	@Override
	public String toString() {
		return "EffortsVO [sqm=" + sqm + ", non_sqm=" + non_sqm + "]";
	}
	private String sqm;
	public String getSqm() {
		return sqm;
	}
	public void setSqm(String sqm) {
		this.sqm = sqm;
	}
	public String getNon_sqm() {
		return non_sqm;
	}
	public void setNon_sqm(String non_sqm) {
		this.non_sqm = non_sqm;
	}
	private String non_sqm;
	
}
