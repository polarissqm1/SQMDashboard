package com.sqm.dashboard.VO;

import java.util.HashMap;
import java.util.List;

public class TrendReportsReleaseVO {

	
	private String defectId;
	private HashMap defectType;
	private HashMap defectRootCause;
	private String defectRaisedDate;
	private String defectFixedDate;
	private HashMap oneDayToFour;
	private HashMap FourToEight;
	private HashMap greaterEight;
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	public HashMap getGreaterEight() {
		return greaterEight;
	}
	public void setGreaterEight(HashMap greaterEight) {
		this.greaterEight = greaterEight;
	}
	public HashMap getFourToEight() {
		return FourToEight;
	}
	public void setFourToEight(HashMap fourToEight) {
		FourToEight = fourToEight;
	}
	public HashMap getOneDayToFour() {
		return oneDayToFour;
	}
	public void setOneDayToFour(HashMap oneDayToFour) {
		this.oneDayToFour = oneDayToFour;
	}
	public HashMap getDefectType() {
		return defectType;
	}
	public void setDefectType(HashMap defectType) {
		this.defectType = defectType;
	}
	public HashMap getDefectRootCause() {
		return defectRootCause;
	}
	public void setDefectRootCause(HashMap defectRootCause) {
		this.defectRootCause = defectRootCause;
	}
	public String getDefectRaisedDate() {
		return defectRaisedDate;
	}
	public void setDefectRaisedDate(String defectRaisedDate) {
		this.defectRaisedDate = defectRaisedDate;
	}
	public String getDefectFixedDate() {
		return defectFixedDate;
	}
	public void setDefectFixedDate(String defectFixedDate) {
		this.defectFixedDate = defectFixedDate;
	}

	
}
