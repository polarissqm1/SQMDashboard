package com.sqm.dashboard.VO;

public class AlmTestcaseVO {

	SchedularManualVO schedManualVO;
	SchedularTestcaseExecVO schedTestcaseExecVO;
	
	public SchedularManualVO getSchedManualVO() {
		return schedManualVO;
	}
	public void setSchedManualVO(SchedularManualVO schedManualVO) {
		this.schedManualVO = schedManualVO;
	}
	public SchedularTestcaseExecVO getSchedTestcaseExecVO() {
		return schedTestcaseExecVO;
	}
	public void setSchedTestcaseExecVO(SchedularTestcaseExecVO schedTestcaseExecVO) {
		this.schedTestcaseExecVO = schedTestcaseExecVO;
	}
	
	@Override
	public String toString() {
		return "AlmTestcaseVO [schedManualVO=" + schedManualVO
				+ ", schedTestcaseExecVO=" + schedTestcaseExecVO + "]";
	}
	
	
}
