package com.sqm.dashboard.VO;

public class AlmDomainProjectRelease {
	
	public AlmDomainProjectRelease() {
	}
	
	public AlmDomainProjectRelease(String domain, String project, AlmReleaseDetails releaseDetails) {
		this.domain = domain;
	    this.project = project;
	    this.releaseDetails = releaseDetails;
	}
	
	private String domain;
	private String project;
	private AlmReleaseDetails releaseDetails;
	
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

	public AlmReleaseDetails getReleaseDetails() {
		return releaseDetails;
	}

	public void setReleaseDetails(AlmReleaseDetails releaseDetails) {
		this.releaseDetails = releaseDetails;
	}
		
}
