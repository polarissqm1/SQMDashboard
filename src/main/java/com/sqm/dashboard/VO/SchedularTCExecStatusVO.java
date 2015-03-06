package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SchedularTCExecStatusVO implements Serializable {

	private String passed;
	private String failed;
	private String noRun;
	private String blocked;
	private String deferred;
	
	public String getPassed() {
		return passed;
	}
	public void setPassed(String passed) {
		this.passed = passed;
	}
	public String getFailed() {
		return failed;
	}
	public void setFailed(String failed) {
		this.failed = failed;
	}
	public String getNoRun() {
		return noRun;
	}
	public void setNoRun(String noRun) {
		this.noRun = noRun;
	}
	public String getBlocked() {
		return blocked;
	}
	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}
	public String getDeferred() {
		return deferred;
	}
	public void setDeferred(String deferred) {
		this.deferred = deferred;
	}
	@Override
	public String toString() {
		return "SchedularTCExecStatusVO [passed=" + passed + ", failed="
				+ failed + ", noRun=" + noRun + ", blocked=" + blocked
				+ ", deferred=" + deferred + "]";
	}
	
	
/*	private List<String> status;
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
		return "SchedularTCExecStatusVO [status=" + status + ", count=" + count
				+ ", percentage=" + percentage + "]";
	}

*/
	
}