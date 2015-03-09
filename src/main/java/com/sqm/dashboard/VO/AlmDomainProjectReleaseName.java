package com.sqm.dashboard.VO;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class AlmDomainProjectReleaseName implements Serializable {

	public AlmDomainProjectReleaseName() {
	}
	
	public AlmDomainProjectReleaseName(String domain, String project, String releaseName) {
		this.domain = domain;
	    this.project = project;
	    this.releaseName = releaseName;
	}

	private String domain;
	private String project;
	private String releaseName;
	
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

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseId(String releaseName) {
		this.releaseName = releaseName;
	}
	
}
