package com.sqm.dashboard.VO;

public class AlmDomProjRelCycleDetails {

	public AlmDomProjRelCycleDetails() {
	}
	
	public AlmDomProjRelCycleDetails(String domain, String project, AlmReleaseDetails releaseDetails, AlmReleaseCycleDetails relCycleDetails) {
		this.domain = domain;
	    this.project = project;
	    this.releaseDetails = releaseDetails;
	    this.relCycleDetails = relCycleDetails;
	}
	
	private String domain;
	private String project;
	private AlmReleaseDetails releaseDetails;
	private AlmReleaseCycleDetails relCycleDetails;
	
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

	public AlmReleaseCycleDetails getRelCycleDetails() {
		return relCycleDetails;
	}

	public void setRelCycleDetails(AlmReleaseCycleDetails relCycleDetails) {
		this.relCycleDetails = relCycleDetails;
	}

	@Override
	public String toString() {
		return "AlmDomProjRelCycleDetails [domain=" + domain + ", project="
				+ project + ", releaseDetails=" + releaseDetails
				+ ", relCycleDetails=" + relCycleDetails + "]";
	}
	
}
