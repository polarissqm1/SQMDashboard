package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class SchedularReleaseDefectsVO implements Serializable {
	
	private List<String> defectId;
	private List<String> defectType;
	private List<String> defectRootCause;
	private List<String> defectRaisedDate;
	private List<String> defectFixedDate;
	private List<String> defectSeverity;
	private List<String> defectFixTime;
	private List<String> defectStatus;
	
	public List<String> getDefectId() {
		return defectId;
	}
	public void setDefectId(List<String> defectId) {
		this.defectId = defectId;
	}
	public List<String> getDefectType() {
		return defectType;
	}
	public void setDefectType(List<String> defectType) {
		this.defectType = defectType;
	}
	public List<String> getDefectRootCause() {
		return defectRootCause;
	}
	public void setDefectRootCause(List<String> defectRootCause) {
		this.defectRootCause = defectRootCause;
	}
	public List<String> getDefectRaisedDate() {
		return defectRaisedDate;
	}
	public void setDefectRaisedDate(List<String> defectRaisedDate) {
		this.defectRaisedDate = defectRaisedDate;
	}
	public List<String> getDefectFixedDate() {
		return defectFixedDate;
	}
	public void setDefectFixedDate(List<String> defectFixedDate) {
		this.defectFixedDate = defectFixedDate;
	}
	public List<String> getDefectSeverity() {
		return defectSeverity;
	}
	public void setDefectSeverity(List<String> defectSeverity) {
		this.defectSeverity = defectSeverity;
	}
	public List<String> getDefectFixTime() {
		return defectFixTime;
	}
	public void setDefectFixTime(List<String> defectFixTime) {
		this.defectFixTime = defectFixTime;
	}
	public List<String> getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(List<String> defectStatus) {
		this.defectStatus = defectStatus;
	}
	
	@Override
	public String toString() {
		return "SchedularReleaseDefectsVO [defectId=" + defectId
				+ ", defectType=" + defectType + ", defectRootCause="
				+ defectRootCause + ", defectRaisedDate=" + defectRaisedDate
				+ ", defectFixedDate=" + defectFixedDate + ", defectSeverity="
				+ defectSeverity + ", defectFixTime=" + defectFixTime
				+ ", defectStatus=" + defectStatus + "]";
	}	
}
