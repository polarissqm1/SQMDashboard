package com.sqm.dashboard.VO;

import java.io.Serializable;

public class AlmReleaseVO implements Serializable {

	private String domain;
	private String project;
	private String id;

	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ReleaseVO [domain=" + domain + ", project=" + project + ", id="
				+ id + "]";
	}
	
}
	/*private String release;
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
	}*/
