package com.sqm.dashboard.VO;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class AlmVO implements Serializable {	private String domain;
private String project;
private String release;

private SchedularDefectsVO defectsVO;
private SchedularTCExecStatusVO testcaseVO;


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

public SchedularDefectsVO getDefectsVO() {
	return defectsVO;
}
public void setDefectsVO(SchedularDefectsVO defectsVO) {
	this.defectsVO = defectsVO;
}
public SchedularTCExecStatusVO getTestcaseVO() {
	return testcaseVO;
}
public void setTestcaseVO(SchedularTCExecStatusVO testcaseVO) {
	this.testcaseVO = testcaseVO;
}

@Override
public String toString() {
	return "AlmVO [domain=" + domain + ", release=" + release
			+ ", project=" + project + ", defectsVO=" + defectsVO
			+ ", testcaseVO=" + testcaseVO + "]";
}
}
