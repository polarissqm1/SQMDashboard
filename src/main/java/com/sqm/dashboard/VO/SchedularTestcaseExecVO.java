package com.sqm.dashboard.VO;

import java.util.List;

public class SchedularTestcaseExecVO {

	private List<String> status;
	private List<String> count;
	private List<String> percentage;
	
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	public List<String> getCount() {
		return count;
	}
	public void setCount(List<String> count) {
		this.count = count;
	}
	public List<String> getPercentage() {
		return percentage;
	}
	public void setPercentage(List<String> percentage) {
		this.percentage = percentage;
	}
	@Override
	public String toString() {
		return "SchedularTestcaseExecVO [status=" + status + ", count=" + count
				+ ", percentage=" + percentage + "]";
	}
}
