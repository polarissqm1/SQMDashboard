package com.sqm.dashboard.VO;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class AlmDomainProject implements Serializable {
	
	public AlmDomainProject() {
	}
	
	public AlmDomainProject(String domain, String project) {
		this.domain = domain;
	    this.project = project;
	}

	private String domain;
	private String project;
	
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

}
