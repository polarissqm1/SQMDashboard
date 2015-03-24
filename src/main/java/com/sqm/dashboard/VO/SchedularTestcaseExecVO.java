package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class SchedularTestcaseExecVO implements Serializable {

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
