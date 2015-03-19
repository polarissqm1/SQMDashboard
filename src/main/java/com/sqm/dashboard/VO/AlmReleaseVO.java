package com.sqm.dashboard.VO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AlmReleaseVO implements Serializable {

	private String domain;
	private String project;
	private String releaseId;
	private String releaseName;
	private String releaseStartDate;
	private String releaseEndDate;
	private String releaseFolder;
	private SchedularReleaseCyclesVO schedReleaseCyclesVO;
	private String plannedTestcases;
	private SchedularReleaseDefectsVO schedReleaseDefectsVO;
	private String status;
	
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
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getReleaseStartDate() {
		return releaseStartDate;
	}
	public void setReleaseStartDate(String releaseStartDate) {
		this.releaseStartDate = releaseStartDate;
	}
	public String getReleaseEndDate() {
		return releaseEndDate;
	}
	public void setReleaseEndDate(String releaseEndDate) {
		this.releaseEndDate = releaseEndDate;
	}
	public String getReleaseFolder() {
		return releaseFolder;
	}
	public void setReleaseFolder(String releaseFolder) {
		this.releaseFolder = releaseFolder;
	}
	public SchedularReleaseCyclesVO getSchedReleaseCyclesVO() {
		return schedReleaseCyclesVO;
	}
	public void setSchedReleaseCyclesVO(
			SchedularReleaseCyclesVO schedReleaseCyclesVO) {
		this.schedReleaseCyclesVO = schedReleaseCyclesVO;
	}
	public String getPlannedTestcases() {
		return plannedTestcases;
	}
	public void setPlannedTestcases(String plannedTestcases) {
		this.plannedTestcases = plannedTestcases;
	}
	public SchedularReleaseDefectsVO getSchedReleaseDefectsVO() {
		return schedReleaseDefectsVO;
	}
	public void setSchedReleaseDefectsVO(
			SchedularReleaseDefectsVO schedReleaseDefectsVO) {
		this.schedReleaseDefectsVO = schedReleaseDefectsVO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "AlmReleaseVO [domain=" + domain + ", project=" + project
				+ ", releaseId=" + releaseId + ", releaseName=" + releaseName
				+ ", releaseStartDate=" + releaseStartDate
				+ ", releaseEndDate=" + releaseEndDate + ", releaseFolder="
				+ releaseFolder + ", schedReleaseCyclesVO="
				+ schedReleaseCyclesVO + ", plannedTestcases="
				+ plannedTestcases + ", schedReleaseDefectsVO="
				+ schedReleaseDefectsVO + ", status=" + status + "]";
	}
	
}