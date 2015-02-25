package com.sqm.dashboard.VO;

import java.io.Serializable;

public class ReleaseVO implements Serializable {


	private String release;
	private String release_sdate;
	private String release_edate;
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getRelease_sdate() {
		return release_sdate;
	}
	public void setRelease_sdate(String release_sdate) {
		this.release_sdate = release_sdate;
	}
	public String getRelease_edate() {
		return release_edate;
	}
	public void setRelease_edate(String release_edate) {
		this.release_edate = release_edate;
	}
	
}
