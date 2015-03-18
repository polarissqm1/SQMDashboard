package com.sqm.dashboard.VO;

public class AlmReleaseDetails {
	
	public AlmReleaseDetails() {
	}
	
	public AlmReleaseDetails(String releaseId, String releaseName, String relStartDate, String relEndDate) {
	    this.releaseId = releaseId;
	    this.releaseName = releaseName;
	    this.relStartDate = relStartDate;
	    this.relEndDate = relEndDate;
	}
	
	private String releaseId;
	private String releaseName;
	private String relStartDate;
	private String relEndDate;
	
	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	
	public String getRelStartDate() {
		return relStartDate;
	}

	public void setRelStartDate(String relStartDate) {
		this.relStartDate = relStartDate;
	}

	public String getRelEndDate() {
		return relEndDate;
	}

	public void setRelEndDate(String relEndDate) {
		this.relEndDate = relEndDate;
	}

	@Override
	public String toString() {
		return "AlmReleaseDetails [releaseId=" + releaseId + ", releaseName="
				+ releaseName + ", relStartDate=" + relStartDate
				+ ", relEndDate=" + relEndDate + "]";
	}
	
}
