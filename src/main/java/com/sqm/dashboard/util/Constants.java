package com.sqm.dashboard.util;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
	
	/**
	 * Default Constructor of class Constants
	 */
	
	private Constants() {
	}

/*	@Value("${almDefectONRA}")private String almDefectONRA;
	@Value("${almDefectOpNewReopAssig}")private static String almDefectOpNewReopAssig;
	@Value("${almDFixReady}")private static String almDFixReady;
	@Value("${almDClosed}")private static String almDClosed;
	@Value("${almDDupRej}")private static String almDDupRej;
	@Value("${almDDef}")private static String almDDef;

	@Value("${almDUrg}")private static String almDUrg;
	@Value("${almDHig}")private static String almDHig;
	@Value("${almDMed}")private static String almDMed;
	@Value("${almDLow}")private static String almDLow;
		
	@Value("${almUIONRA}")private static String almUIONRA;
	@Value("${almUIFR}")private static String almUIFR;
	@Value("${almUIC}")private static String almUIC;
	@Value("${almUIDR}")private static String almUIDR;
	@Value("${almUID}")private static String almUID;
		
	@Value("${almUISS}")private static String almUISS;
	@Value("${almUIUrg}")private static String almUIUrg;
	@Value("${almUIHig")private static String almUIHig;
	@Value("${almUIMed}")private static String almUIMed;
	@Value("${almUILow}")private static String almUILow;
	@Value("${almUITotDef}")private static String almUITotDef;
	@Value("${almUITot}")private static String almUITot;
	@Value("${almUIPer}")private static String almUIPer;
		
	@Value("${almUITS}")private static String almUITS;
	@Value("${almUITP}")private static String almUITP;
	@Value("${almUITF}")private static String almUITF;
	@Value("${almUITNRNC}")private static String almUITNRNC;
	@Value("${almUITNA}")private static String almUITNA;
	@Value("${almUITD}")private static String almUITD;
	@Value("${almUITB}")private static String almUITB;
	@Value("${almUITTot}")private static String almUITTot;
	@Value("${almUITCount}")private static String almUITCount;
	@Value("${almUITPer}")private static String almUITPer;
		
	@Value("${almUITSP}")private static String almUITSP;
	@Value("${almUITSF}")private static String almUITSF;
	@Value("${almUITSNRNC}")private static String almUITSNRNC;
	@Value("${almUITSNA}")private static String almUITSNA;
	@Value("${almUITSD}")private static String almUITSD;
	@Value("${almUITSB}")private static String almUITSB;
	
	
	public static final String DEFECT_STATUS_OPEN_NEW_REOPENED_ASSIGNED = almDefectOpNewReopAssig;
	public static final String DEFECT_STATUS_FIXED_READYFORRETEST = almDFixReady;
	public static final String DEFECT_STATUS_CLOSED = almDClosed;
	public static final String DEFECT_STATUS_DUPLICATE_REJECTED = almDDupRej;
	public static final String DEFECT_STATUS_DEFERRED = almDDef;

	public static final String DEFECT_PRIORITY_1_URGENT = almDUrg;
	public static final String DEFECT_PRIORITY_2_HIGH = almDHig;
	public static final String DEFECT_PRIORITY_3_MEDIUM = almDMed;
	public static final String DEFECT_PRIORITY_4_LOW = almDLow;
	
	// Defects - UI constants
	public static final String UI_OPEN_NEW_REOPENED_ASSIGNED = almUIONRA;
	public static final String UI_FIXED_READYFORRETEST = almUIFR;
	public static final String UI_CLOSED = almUIC;
	public static final String UI_DUPLICATE_REJECTED = almUIDR;
	public static final String UI_DEFERRED = almUID;
	
	public static final String UI_STATUS_SEVERITY = almUISS;
	public static final String UI_1_URGENT = almUIUrg;
	public static final String UI_2_HIGH = almUIHig;
	public static final String UI_3_MEDIUM = almUIMed;
	public static final String UI_4_LOW = almUILow;
	public static final String UI_TOTAL_DEFECTS = almUITotDef;
	public static final String UI_TOTAL = almUITot;
	public static final String UI_PERCENTAGE = almUIPer;
	
	//Test cases
	public static final String UI_TESTCASES_STATUS = almUITS;
	public static final String UI_TESTCASES_PASSED = almUITP;
	public static final String UI_TESTCASES_FAILED = almUITF;
	public static final String UI_TESTCASES_NRNC = almUITNRNC;
	public static final String UI_TESTCASES_NA = almUITNA;
	public static final String UI_TESTCASES_DEFERRED = almUITD;
	public static final String UI_TESTCASES_BLOCKED = almUITB;
	public static final String UI_TESTCASES_TOTAL = almUITTot;
	public static final String UI_TESTCASES_COUNT = almUITCount;
	public static final String UI_TESTCASES_PERCENTAGE = almUITPer;
	
	public static final String TESTCASES_STATUS_PASSED = almUITSP;
	public static final String TESTCASES_STATUS_FAILED = almUITSF;
	public static final String TESTCASES_STATUS_NORUNANDNOTCOMPLETED = almUITSNRNC;
	public static final String TESTCASES_STATUS_NOTAPPLICABLE = almUITSNA;
	public static final String TESTCASES_STATUS_DEFERRED = almUITSD;
	public static final String TESTCASES_STATUS_BLOCKED = almUITSB;
	
	// ALM login details
	/*public static final String HOST = "ealm11.jpmchase.net";
	public static final String PORT = "80";
	public static final String USERNAME = "ramu.suntaramoni";
	public static final String PASSWORD = "Polaris11";
	public static final String DOMAIN = "AWM";
	public static final String PROJECT = "ALT_INVEST";
	public static final boolean VERSIONED = true;
*/
	//Defect Status
	public static final String DEFECT_STATUS_OPEN_NEW_REOPENED_ASSIGNED = "Open or New or Re-Opened or Assigned";
	public static final String DEFECT_STATUS_FIXED_READYFORRETEST = "Fixed or \"Ready for Re-test\"";
	public static final String DEFECT_STATUS_CLOSED = "Closed";
	public static final String DEFECT_STATUS_DUPLICATE_REJECTED = "Duplicate or Rejected";
	public static final String DEFECT_STATUS_DEFERRED = "Deferred";
	public static final String DEFECT_STATUS_TOTAL = "Total";
	public static final String DEFECT_STATUS_PERCENTAGE = "Percentage";

	public static final String DEFECT_SEVERITY_1_URGENT = "1 - Urgent";
	public static final String DEFECT_SEVERITY_2_HIGH = "2 - High";
	public static final String DEFECT_SEVERITY_3_MEDIUM = "3 - Medium";
	public static final String DEFECT_SEVERITY_4_LOW = "4 - Low";
	
	public static final String TESTCASES_STATUS_PASSED = "Passed";
	public static final String TESTCASES_STATUS_FAILED = "Failed";
	public static final String TESTCASES_STATUS_NORUNANDNOTCOMPLETED = "\"No Run\" or \"Not Completed\"";
	public static final String TESTCASES_STATUS_NOTAPPLICABLE = "N/A";
	public static final String TESTCASES_STATUS_DEFERRED = "Deferred";
	public static final String TESTCASES_STATUS_BLOCKED = "Blocked";
	
}