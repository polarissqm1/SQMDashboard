package com.sqm.dashboard.VO;

public class AlmReleaseCycleDetails {
	
	public AlmReleaseCycleDetails() {
	}
	
	public AlmReleaseCycleDetails(String cycleId, String cycleName, String cycleStartDate, String cycleEndDate) {
	    this.cycleId = cycleId;
	    this.cycleName = cycleName;
	    this.cycleStartDate = cycleStartDate;
	    this.cycleEndDate = cycleEndDate;
	}
	
	private String cycleId;
	private String cycleName;
	private String cycleStartDate;
	private String cycleEndDate;
	
	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public String getCycleName() {
		return cycleName;
	}

	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}

	public String getCycleStartDate() {
		return cycleStartDate;
	}

	public void setCycleStartDate(String cycleStartDate) {
		this.cycleStartDate = cycleStartDate;
	}

	public String getCycleEndDate() {
		return cycleEndDate;
	}

	public void setCycleEndDate(String cycleEndDate) {
		this.cycleEndDate = cycleEndDate;
	}
	
}
