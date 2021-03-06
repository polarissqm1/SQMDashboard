package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

public class DashboardVO implements Serializable {
	
	/*private String domain;
	private String project;
	private ReleaseVO releaseVO;*/
	private ManualVO manualVO;
	private AutomationVO automationVO;
	private SeverityVO severityVO;
	private EffortsVO effortsVO;
	private RagVO ragVO;
	private List<StatusAndSeverityVO> statusAndSeverityVO;
	private List<TestCaseExecutionStatusVO> testCaseExecutionStatusVO;
	private List<DefectIdsVO> defectVO;
	private String rdate;
	private String plan;
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	/*public String getDomain() {
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
	public ReleaseVO getReleaseVO() {
		return releaseVO;
	}
	public void setReleaseVO(ReleaseVO releaseVO) {
		this.releaseVO = releaseVO;
	}*/
	public ManualVO getManualVO() {
		return manualVO;
	}
	public void setManualVO(ManualVO manualVO) {
		this.manualVO = manualVO;
	}
	public AutomationVO getAutomationVO() {
		return automationVO;
	}
	public void setAutomationVO(AutomationVO automationVO) {
		this.automationVO = automationVO;
	}
	public SeverityVO getSeverityVO() {
		return severityVO;
	}
	public void setSeverityVO(SeverityVO severityVO) {
		this.severityVO = severityVO;
	}
	public EffortsVO getEffortsVO() {
		return effortsVO;
	}
	public void setEffortsVO(EffortsVO effortsVO) {
		this.effortsVO = effortsVO;
	}
	public List<StatusAndSeverityVO> getStatusAndSeverityVO() {
		return statusAndSeverityVO;
	}
	public void setStatusAndSeverityVO(List<StatusAndSeverityVO> statusAndSeverityVO) {
		this.statusAndSeverityVO = statusAndSeverityVO;
	}
	public List<TestCaseExecutionStatusVO> getTestCaseExecutionStatusVO() {
		return testCaseExecutionStatusVO;
	}
	public void setTestCaseExecutionStatusVO(
			List<TestCaseExecutionStatusVO> testCaseExecutionStatusVO) {
		this.testCaseExecutionStatusVO = testCaseExecutionStatusVO;
	}
	
	
	
	public List<DefectIdsVO> getDefectVO() {
		return defectVO;
	}
	public void setDefectVO(List<DefectIdsVO> defectVO) {
		this.defectVO = defectVO;
	}
	public RagVO getRagVO() {
		return ragVO;
	}
	public void setRagVO(RagVO ragVO) {
		this.ragVO = ragVO;
	}
	
	
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	@Override
	public String toString() {
		return "DashboardVO [manualVO=" + manualVO + ", automationVO="
				+ automationVO + ", severityVO=" + severityVO + ", effortsVO="
				+ effortsVO + ", statusAndSeverityVO=" + statusAndSeverityVO
				+ ", testCaseExecutionStatusVO=" + testCaseExecutionStatusVO
				+ ", ragVO=" + ragVO
				+ ", rdate=" + rdate
				+ "]";
	}
	
	
	
	

}
