package com.sqm.dashboard.VO;

public class RagVO {

	private String status;
	private String user;
	private String date;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "RagVO [status=" + status + ", user=" + user
				+ ", date=" + date + "]";
	}
}
