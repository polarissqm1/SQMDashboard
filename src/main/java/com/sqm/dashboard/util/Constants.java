package com.sqm.dashboard.util;

public class Constants {
	
	public static final String DB_JIRA_PROJECT = "project";
	/*alm Collection Fields*/
	public static final String DB_ALM_USERID = "userId";
	public static final String DB_ALM_DEFECT_IDS = "defectIds";
	public static final String DB_ALM_MANUAL_TC_EXECSTATUS = "manual_TCExecutionStatus";
	public static final String DB_ALM_PASSED = "passed";
	public static final String DB_ALM_FAILED = "failed";
	public static final String DB_ALM_NORUN = "noRun";
	public static final String DB_ALM_BLOCKED = "blocked";
	public static final String DB_ALM_DEFERRED = "deferred";
	public static final String DB_ALM_AUTOMATION_TC_EXECSTATUS = "automation_TCExecutionStatus";
	public static final String DB_ALM_STATUS_AND_SEVERITY = "statusAndSeverity";
	public static final String DB_ALM_STATUS_OP_NW_REOP_ASSIGN_VAL = "Open/New/Re-Opened/Assigned";
	public static final String DB_ALM_STATUS_FIX_READY_VAL = "Fixed/Ready for Re-test";
	public static final String DB_ALM_STATUS_DUP_REJ_VAL = "Duplicate/Rejected";
	public static final String DB_ALM_STATUS_DEF_VAL = "Deferred";
	public static final String DB_ALM_STATUS_CLOSED_VAL = "Closed";
	public static final String DB_ALM_STATUS_TOTAL_VAL = "Total";
	public static final String DB_ALM_STATUS_PER_VAL = "Percentage(%)";
	
	public static final String DB_ALM_STATUS_SEVERITY = "statusSeverity";
	public static final String DB_ALM_URGENT = "Urgent";
	public static final String DB_ALM_HIGH = "High";
	public static final String DB_ALM_MEDIUM = "Medium";
	public static final String DB_ALM_LOW = "Low";
	public static final String DB_ALM_TOTAL = "Total";
	public static final String DB_ALM_RAG_SYSTEM = "ragStatus_System";
	public static final String DB_ALM_RAG_STATUS = "Status";
	public static final String DB_ALM_RAG_USER = "user";
	public static final String DB_ALM_RAG_DATE = "date";
	public static final String DB_ALM_RAG_MANUAL = "ragStatus_Manual";
	public static final String DB_ALM_JIRAID = "jiraId";
	
	/*release Collection Fields*/
	public static final String DB_RELEASE_RELFOLDER = "releaseFolder";
	public static final String DB_RELEASE_CYCLES = "cycles";
	public static final String DB_RELEASE_CYCLENAME = "cycleName";
	public static final String DB_RELEASE_CYCLE_SDATE = "cycle_SDate";
	public static final String DB_RELEASE_CYCLE_EDATE = "cycle_EDate";
	public static final String DB_RELEASE_PLANNEDTC = "plannedTestcases";
	public static final String DB_RELEASE_DEFECT_ID = "defectId";
	public static final String DB_RELEASE_DEFECT_TYPE = "defectType";
	public static final String DB_RELEASE_DEFECT_ROOTCAUSE = "defectRootCause";
	public static final String DB_RELEASE_DEFECT_RAISEDDATE = "defectRaisedDate";
	public static final String DB_RELEASE_DEFECT_FIXEDDATE = "defectFixedDate";
	public static final String DB_RELEASE_DEFECT_SEVERITY = "defectSeverity";
	public static final String DB_RELEASE_DEFECT_FIXTIME = "defectFixTime";
	public static final String DB_RELEASE_DEFECTSTATUS = "defectStatus";
	public static final String DB_RELEASE_DEFECTS = "defects";
	public static final String DB_RELEASE_STATUS = "status";
	public static final String DB_RELEASE_ENVDATES = "envDates";

	/*Common Fields*/
	public static final String DB_DOMAIN = "domain";
	public static final String DB_PROJECT = "project";
	public static final String DB_RELEASE = "release";
	public static final String DB_REL_SDATE = "release_SDate";
	public static final String DB_REL_EDATE = "release_EDate";
	public static final String DB_CREATED_ON = "CreatedOn";
	public static final String DB_CREATED_BY = "CreatedBy";
	public static final String DB_UPDATED_ON = "UpdatedOn";
	public static final String DB_UPDATED_BY = "UpdatedBy";
	public static final String KEY = "key";
	
	/*Defect Constants*/
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
	
	/*Testcases Constants*/
	public static final String TESTCASES_STATUS_PASSED = "Passed";
	public static final String TESTCASES_STATUS_FAILED = "Failed";
	public static final String TESTCASES_STATUS_NORUNANDNOTCOMPLETED = "\"No Run\" or \"Not Completed\"";
	public static final String TESTCASES_STATUS_NOTAPPLICABLE = "N/A";
	public static final String TESTCASES_STATUS_DEFERRED = "Deferred";
	public static final String TESTCASES_STATUS_BLOCKED = "Blocked";
	
}