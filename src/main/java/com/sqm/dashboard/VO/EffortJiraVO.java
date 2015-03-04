package com.sqm.dashboard.VO;

import java.io.Serializable;
import java.util.List;

public class EffortJiraVO implements Serializable {

	private int sqm;
	
	private int nonsqm;

	public int getSqm() {
		return sqm;
	}

	public void setSqm(int sqm) {
		this.sqm = sqm;
	}

	public int getNonsqm() {
		return nonsqm;
	}

	public void setNonsqm(int nonsqm) {
		this.nonsqm = nonsqm;
	}
	
}
