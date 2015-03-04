package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

public class JiraSchedulerVO implements Serializable {
	
	private String project;
	private String release;
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
	public List<EffortJiraVO> getEffort() {
		return effort;
	}
	public void setEffort(List<EffortJiraVO> effort) {
		this.effort = effort;
	}
	public String getDocreatedby() {
		return docreatedby;
	}
	public void setDocreatedby(String docreatedby) {
		this.docreatedby = docreatedby;
	}
	public String getDoccreateddate() {
		return doccreateddate;
	}
	public void setDoccreateddate(String doccreateddate) {
		this.doccreateddate = doccreateddate;
	}
	public String getDocupdatedby() {
		return docupdatedby;
	}
	public void setDocupdatedby(String docupdatedby) {
		this.docupdatedby = docupdatedby;
	}
	public String getLastupdateddate() {
		return lastupdateddate;
	}
	public void setLastupdateddate(String lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}
	public List<JiraIdVO> getJiraids() {
		return jiraids;
	}
	public void setJiraids(List<JiraIdVO> jiraids) {
		this.jiraids = jiraids;
	}
	private List<EffortJiraVO> effort;
	private String docreatedby;
	private String doccreateddate;
	private String docupdatedby;
	private String lastupdateddate;
	private List<JiraIdVO> jiraids;

}
