package com.sqm.dashboard.VO;

import java.io.Serializable;

public class ManualVO implements Serializable {
	
	private String passed;
	private String failed;
	private String noRun;
	private String blocked;
	private String defered;
	public String getPassed() {
		return passed;
	}
	@Override
	public String toString() {
		return "ManualVO [passed=" + passed + ", failed=" + failed + ", noRun="
				+ noRun + ", blocked=" + blocked + ", defered=" + defered + "]";
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
	public String getDefered() {
		return defered;
	}
	public void setDefered(String defered) {
		this.defered = defered;
	}

}
