package com.sqm.dashboard.VO;

import java.io.Serializable;

public class TestCaseExecutionStatusVO implements Serializable {

	
	private String status;
	private String count;
	private String percentage;
	
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	//private String total;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	/*public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}*/
	
}
