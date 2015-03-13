package com.sqm.dashboard.VO;

import java.util.HashMap;
import java.util.List;

public class TrendReportsReleaseVO {

	
	private String defectId;
	private HashMap defectType;
	private HashMap defectRootCause;
	private String defectRaisedDate;
	private String defectFixedDate;
	private String defectAgeingVO;
	public String getDefectId() {
		return defectId;
	}
	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}
	
	
	
	
	
	public String getDefectAgeingVO() {
		return defectAgeingVO;
	}
	public void setDefectAgeingVO(String defectAgeingVO) {
		this.defectAgeingVO = defectAgeingVO;
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
