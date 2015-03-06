package com.sqm.dashboard.VO;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class AlmDomainProjectReleaseId implements Serializable {

	public AlmDomainProjectReleaseId() {
	}
	
	public AlmDomainProjectReleaseId(String domain, String project, String releaseId) {
		this.domain = domain;
	    this.project = project;
	    this.releaseId = releaseId;
	}

	private String domain;
	private String project;
	private String releaseId;
	
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

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}
	
}
