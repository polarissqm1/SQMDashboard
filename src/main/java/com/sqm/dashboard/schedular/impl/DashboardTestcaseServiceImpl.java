package com.sqm.dashboard.schedular.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.sqm.dashboard.schedular.DashboardTestcaseService;
import com.sqm.dashboard.util.Constants;
import com.sqm.dashboard.util.EntitiesUtility;
import com.sqm.dashboard.util.MarshallingUtility;
import com.sqm.dashboard.util.RestConnectorUtility;
import com.sqm.dashboard.VO.SchedularTCExecStatusVO;


@Service("dbTestcaseServiceImpl")
public class DashboardTestcaseServiceImpl implements DashboardTestcaseService {static final Log log = LogFactory.getLog(DashboardTestcaseServiceImpl.class);

/*@Autowired*/
SchedularTCExecStatusVO schedularTCExecStatusVO = new SchedularTCExecStatusVO();

/*	@Autowired
DashboardSchedularDAOImpl dbSchedularDAOImpl;

@Autowired
DashboardTestcaseServiceImpl dbTestcaseServiceImpl;
*/
//DashboardTestcaseServiceImpl dbTestcaseServiceImpl = new DashboardTestcaseServiceImpl();

private List<String> status = new ArrayList<String>();
private List<String> count = new ArrayList<String>();
private List<String> percentage = new ArrayList<String>();

private String tcPassed;
private String tcFailed;
private String tcNotRunAndNotCompleted;
private String tcNA;
private String tcDeferred;
private String tcBlocked;
	
private Double tcTotal;

@SuppressWarnings("unused")
public SchedularTCExecStatusVO getAlmTestcases(RestConnectorUtility conn, Map<String, String> requestHeaders, String testcasesUrl, String releaseId) {
	
	try {
		//tcPassed = dbTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_PASSED, releaseId);
		tcPassed = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_PASSED, releaseId);
		status.add(0, Constants.TESTCASES_STATUS_PASSED);
		count.add(0, tcPassed);
		
		//tcFailed = dbTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_FAILED, releaseId);
		tcFailed = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_FAILED, releaseId);
		status.add(1, Constants.TESTCASES_STATUS_FAILED);
		count.add(1, tcFailed);
		
		//tcNotRunAndNotCompleted = dbTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_NORUNANDNOTCOMPLETED, releaseId);
		tcNotRunAndNotCompleted = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_NORUNANDNOTCOMPLETED, releaseId);
		status.add(2, Constants.TESTCASES_STATUS_NORUNANDNOTCOMPLETED);
		count.add(2, tcNotRunAndNotCompleted);
		
		//tcNA = dbTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_NOTAPPLICABLE, releaseId);
		tcNA = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_NOTAPPLICABLE, releaseId);
		status.add(3, Constants.TESTCASES_STATUS_NOTAPPLICABLE);
		count.add(3, tcNA);
		
		//tcDeferred = dbTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_DEFERRED, releaseId);
		tcDeferred = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_DEFERRED, releaseId);
		status.add(4, Constants.TESTCASES_STATUS_DEFERRED);
		count.add(4, tcDeferred);
		
		//tcBlocked = dbTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_BLOCKED, releaseId);
		tcBlocked = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_BLOCKED, releaseId);
		status.add(5, Constants.TESTCASES_STATUS_BLOCKED);
		count.add(5, tcBlocked);
		
		tcTotal = Double.valueOf(tcPassed) + Double.valueOf(tcFailed) + Double.valueOf(tcNotRunAndNotCompleted) +
					Double.valueOf(tcNA) + Double.valueOf(tcDeferred) + Double.valueOf(tcBlocked);
		
		System.out.println("tcTotal : " + tcTotal);
		
		status.add(6, "Total");
		count.add(6, tcTotal.toString());
		
		log.info("tcTotal : " + tcTotal);
		
		if(tcTotal > 0.0){
			BigDecimal percentPassed = new BigDecimal((Double.valueOf(tcPassed)/tcTotal) * 100);
			BigDecimal percentFailed = new BigDecimal((Double.valueOf(tcFailed)/tcTotal) * 100);
			BigDecimal percentNotRunAndNotCompleted = new BigDecimal((Double.valueOf(tcNotRunAndNotCompleted)/tcTotal) * 100);
			BigDecimal percentNotApplicable = new BigDecimal((Double.valueOf(tcNA)/tcTotal) * 100);
			BigDecimal percentBlocked = new BigDecimal((Double.valueOf(tcDeferred)/tcTotal) * 100);
			BigDecimal percentDeferred = new BigDecimal((Double.valueOf(tcBlocked)/tcTotal) * 100);
        
			String percentagePassed = percentPassed.setScale(2, RoundingMode.CEILING).toString();
			String percentageFailed = percentFailed.setScale(2, RoundingMode.CEILING).toString();
			String percentageNotRunAndNotApplicable = percentNotRunAndNotCompleted.setScale(2, RoundingMode.CEILING).toString();
			String percentageNotApplicable = percentNotApplicable.setScale(2, RoundingMode.CEILING).toString();
			String percentageBlocked = percentBlocked.setScale(2, RoundingMode.CEILING).toString();
			String percentageDeferred = percentDeferred.setScale(2, RoundingMode.CEILING).toString();
			String percentageTotal = "100.00";
          
			percentage.add(0, percentagePassed);
			percentage.add(1, percentageFailed);
			percentage.add(2, percentageNotRunAndNotApplicable);
			percentage.add(3, percentageNotApplicable);
			percentage.add(4, percentageBlocked);
			percentage.add(5, percentageDeferred);
			percentage.add(6, percentageTotal);
		} else {
			percentage.add(0, "0.00");
			percentage.add(1, "0.00");
			percentage.add(2, "0.00");
			percentage.add(3, "0.00");
			percentage.add(4, "0.00");
			percentage.add(5, "0.00");
			percentage.add(6, "0.00");
		}
		
		/*schedularTCExecStatusVO.setStatus(status);
		schedularTCExecStatusVO.setCount(count);
		schedularTCExecStatusVO.setPercentage(percentage);
		
		log.info("schedularTCExecStatusVO.getStatus() : " + schedularTCExecStatusVO.getStatus().toString() +
				"schedularTCExecStatusVO.getCount() : " + schedularTCExecStatusVO.getCount().toString() +
				"schedularTCExecStatusVO.getPercentage() : " + schedularTCExecStatusVO.getPercentage().toString());*/
		
		String passed = tcPassed;
		String failed = tcFailed;
		String noRun = tcNotRunAndNotCompleted;
		String blocked = tcBlocked;
		String deferred = tcDeferred;
		
		schedularTCExecStatusVO.setPassed(passed);
		schedularTCExecStatusVO.setFailed(failed);
		schedularTCExecStatusVO.setNoRun(noRun);
		schedularTCExecStatusVO.setBlocked(blocked);
		schedularTCExecStatusVO.setDeferred(deferred);
		
		log.info("schedularTCExecStatusVO.getStatus() : " + schedularTCExecStatusVO.getPassed().toString() +
				"schedularTCExecStatusVO.getCount() : " + schedularTCExecStatusVO.getFailed().toString() +
				"schedularTCExecStatusVO.getPercentage() : " + schedularTCExecStatusVO.getNoRun().toString() +
				"schedularTCExecStatusVO.getCount() : " + schedularTCExecStatusVO.getBlocked().toString() + 
				"schedularTCExecStatusVO.getCount() : " + schedularTCExecStatusVO.getDeferred().toString());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return schedularTCExecStatusVO;
}

static String getAlmTestcases(RestConnectorUtility connection, Map<String, String> requestHeaders,  String testcasesUrl, 
		String testcaseStatus, String releaseId) throws Exception {

	StringBuilder queryAlmTestcases = new StringBuilder();
	try{
		queryAlmTestcases.append("query={status[");
		queryAlmTestcases.append(testcaseStatus.replace(" ", "%20"));
		queryAlmTestcases.append("];");
		queryAlmTestcases.append("id[");
		queryAlmTestcases.append(releaseId);
		queryAlmTestcases.append("]}");
		queryAlmTestcases.append("&fields=id");

		log.info("AlmTestcases Query : " + queryAlmTestcases);

		String listFromTestcasesCollectionAsXml = connection.httpGet(testcasesUrl, queryAlmTestcases.toString(), requestHeaders).toString();
		log.info("listFromTestcasesCollectionAsXml : " + listFromTestcasesCollectionAsXml);

		EntitiesUtility entitiesTestcases = MarshallingUtility.marshal(EntitiesUtility.class, listFromTestcasesCollectionAsXml);
		log.info("Testcases Entities : " + entitiesTestcases);
		log.info(testcaseStatus + "#" + "Count : " + entitiesTestcases.getTotalResults());

		return entitiesTestcases.getTotalResults();
	} catch (Exception e) {
		log.error("Error in getting testcases count : " + e.getMessage());
		throw e;
	}
}}
