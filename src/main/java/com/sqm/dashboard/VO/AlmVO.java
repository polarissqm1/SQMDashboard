package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class AlmVO implements Serializable {
	
	private String domain;
	private String project;
	private String release;
	private String relStartDate;
	private String relEndDate;
	private ArrayList<String> defectIds;
	private ArrayList<String> jiraIds;
	private SchedularDefectsVO defectsVO;
	private AlmTestcaseVO almTCVO;
	
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
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
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
	public ArrayList<String> getDefectIds() {
		return defectIds;
	}
	public void setDefectIds(ArrayList<String> defectIds) {
		this.defectIds = defectIds;
	}
	public ArrayList<String> getJiraIds() {
		return jiraIds;
	}
	public void setJiraIds(ArrayList<String> jiraIds) {
		this.jiraIds = jiraIds;
	}
	public SchedularDefectsVO getDefectsVO() {
		return defectsVO;
	}
	public void setDefectsVO(SchedularDefectsVO defectsVO) {
		this.defectsVO = defectsVO;
	}
	public AlmTestcaseVO getAlmTCVO() {
		return almTCVO;
	}
	public void setAlmTCVO(AlmTestcaseVO almTCVO) {
		this.almTCVO = almTCVO;
	}
	@Override
	public String toString() {
		return "AlmVO [domain=" + domain + ", project=" + project
				+ ", release=" + release + ", relStartDate=" + relStartDate
				+ ", relEndDate=" + relEndDate + ", defectIds=" + defectIds
				+ ", jiraIds=" + jiraIds + ", defectsVO=" + defectsVO
				+ ", almTCVO=" + almTCVO + "]";
	}
}
