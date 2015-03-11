package com.sqm.dashboard.VO;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class AlmVO implements Serializable {	private String domain;
private String project;
private String release;

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
	return "AlmVO [domain=" + domain + ", project=" + project + ", release="
			+ release + ", defectsVO=" + defectsVO + ", almTCVO=" + almTCVO
			+ "]";
}





}
