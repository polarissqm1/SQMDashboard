package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.ArrayList;

public class AlmReleaseVO implements Serializable {

	private String domain;
	private String project;
	private String releaseId;
	private String releaseName;
	private String plannedTestcases;
	private ArrayList<String> cycleNames;
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
	public String getPlannedTestcases() {
		return plannedTestcases;
	}
	public void setPlannedTestcases(String plannedTestcases) {
		this.plannedTestcases = plannedTestcases;
	}
	public ArrayList<String> getCycleNames() {
		return cycleNames;
	}
	public void setCycleNames(ArrayList<String> cycleNames) {
		this.cycleNames = cycleNames;
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
				+ ", plannedTestcases=" + plannedTestcases + ", cycleNames="
				+ cycleNames + ", schedReleaseDefectsVO="
				+ schedReleaseDefectsVO + ", status=" + status + "]";
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
