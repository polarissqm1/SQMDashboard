package com.sqm.dashboard.VO;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SchedularReleasesVO {

	/*private List<String> releaseName;
	
	private List<String> releaseName;*/
	
	private String release_sdate;

	public String getRelease_sdate() {
		return release_sdate;
	}

	public void setRelease_sdate(String release_sdate) {
		this.release_sdate = release_sdate;
	}

	/*public List<String> getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(List<String> releaseName) {
		this.releaseName = releaseName;
	}*/
	
	
}
