package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;



@Component
public class SchedularDefectsVO implements Serializable {

	private List<String> statusSeverity;
	private List<String> urgent;
	private List<String> high;
	private List<String> medium;
	private List<String> low;
	private List<String> totalDefects;
	
	public List<String> getStatusSeverity() {
		return statusSeverity;
	}
	public void setStatusSeverity(List<String> statusSeverity) {
		this.statusSeverity = statusSeverity;
	}
	public List<String> getUrgent() {
		return urgent;
	}
	public void setUrgent(List<String> urgent) {
		this.urgent = urgent;
	}
	public List<String> getHigh() {
		return high;
	}
	public void setHigh(List<String> high) {
		this.high = high;
	}
	public List<String> getMedium() {
		return medium;
	}
	public void setMedium(List<String> medium) {
		this.medium = medium;
	}
	public List<String> getLow() {
		return low;
	}
	public void setLow(List<String> low) {
		this.low = low;
	}
	public List<String> getTotalDefects() {
		return totalDefects;
	}
	public void setTotalDefects(List<String> totalDefects) {
		this.totalDefects = totalDefects;
	}
	
	@Override
	public String toString() {
		return "SchedularDefectsVO [statusSeverity=" + statusSeverity
				+ ", urgent=" + urgent + ", high=" + high + ", medium="
				+ medium + ", low=" + low + ", totalDefects=" + totalDefects
				+ "]";
	}
	
}
