package com.sqm.dashboard.VO;

import java.util.List;

public class SchedularReleaseCyclesVO {
	
	private List<String> cycleId;
	private List<String> cycleName;
	private List<String> cycleStartDate;
	private List<String> cycleEndDate;
	
	public List<String> getCycleId() {
		return cycleId;
	}
	public void setCycleId(List<String> cycleId) {
		this.cycleId = cycleId;
	}
	public List<String> getCycleName() {
		return cycleName;
	}
	public void setCycleName(List<String> cycleName) {
		this.cycleName = cycleName;
	}
	public List<String> getCycleStartDate() {
		return cycleStartDate;
	}
	public void setCycleStartDate(List<String> cycleStartDate) {
		this.cycleStartDate = cycleStartDate;
	}
	public List<String> getCycleEndDate() {
		return cycleEndDate;
	}
	public void setCycleEndDate(List<String> cycleEndDate) {
		this.cycleEndDate = cycleEndDate;
	}
	
	@Override
	public String toString() {
		return "SchedularReleaseCyclesVO [cycleId=" + cycleId + ", cycleName="
				+ cycleName + ", cycleStartDate=" + cycleStartDate
				+ ", cycleEndDate=" + cycleEndDate + "]";
	}
}
