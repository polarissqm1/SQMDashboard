package com.sqm.dashboard.VO;

import java.util.Date;

public class RagVO {

	private String Status;
	private String user;

	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
	@Override
	public String toString() {
		return "RagVO [status=" + Status + ", user=" + user
				+ "]";
	}
}
