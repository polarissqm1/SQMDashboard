package com.sqm.dashboard.schedular.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqm.dashboard.VO.AlmTestcaseVO;
import com.sqm.dashboard.VO.SchedularManualVO;
import com.sqm.dashboard.VO.SchedularTCExecStatusVO;
import com.sqm.dashboard.VO.SchedularTestcaseExecVO;
import com.sqm.dashboard.schedular.AlmSchedTestcaseService;
import com.sqm.dashboard.util.Constants;
import com.sqm.dashboard.util.EntitiesUtility;
import com.sqm.dashboard.util.MarshallingUtility;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedTestcaseServiceImpl")
public class AlmSchedTestcaseServiceImpl implements AlmSchedTestcaseService {
	
	static final Logger log = Logger.getLogger(AlmSchedTestcaseServiceImpl.class);

	@Autowired
	private SchedularTCExecStatusVO schedularTCExecStatusVO;
	
	@Autowired
	private AlmTestcaseVO almTCVO;
	
	@Autowired
	private SchedularManualVO schedManualVO;
	
	@Autowired
	private SchedularTestcaseExecVO schedTestcaseExecVO;
	
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
	private Integer total;

	public AlmTestcaseVO getAlmTestcases(RestConnectorUtility conn, Map<String, String> requestHeaders, String testcasesUrl, String releaseId) {
		System.out.println("testcasesUrl : " + testcasesUrl);
		try {
			
			tcPassed = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_PASSED, releaseId);
			
			status.add(0, Constants.TESTCASES_STATUS_PASSED);
			count.add(0, tcPassed);
		
			tcFailed = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_FAILED, releaseId);
			status.add(1, Constants.TESTCASES_STATUS_FAILED);
			count.add(1, tcFailed);
		
			tcNotRunAndNotCompleted = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_NORUNANDNOTCOMPLETED, releaseId);
			status.add(2, Constants.TESTCASES_STATUS_NORUNANDNOTCOMPLETED);
			count.add(2, tcNotRunAndNotCompleted);
		
			tcNA = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_NOTAPPLICABLE, releaseId);
			status.add(3, Constants.TESTCASES_STATUS_NOTAPPLICABLE);
			count.add(3, tcNA);
		
			tcDeferred = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_DEFERRED, releaseId);
			status.add(4, Constants.TESTCASES_STATUS_DEFERRED);
			count.add(4, tcDeferred);
		
			tcBlocked = getAlmTestcases(conn, requestHeaders, testcasesUrl, Constants.TESTCASES_STATUS_BLOCKED, releaseId);
			status.add(5, Constants.TESTCASES_STATUS_BLOCKED);
			count.add(5, tcBlocked);
		
			tcTotal = Double.valueOf(tcPassed) + Double.valueOf(tcFailed) + Double.valueOf(tcNotRunAndNotCompleted) +
						Double.valueOf(tcNA) + Double.valueOf(tcDeferred) + Double.valueOf(tcBlocked);
		
			log.info("tcTotal : " + tcTotal);
			
			total = tcTotal.intValue();
			log.info("total : " + total);
			
			status.add(6, "Total");
			count.add(6, total.toString());
			if(total > 0){
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
				
				String percentageTotal = null;
				
				if(Double.valueOf(percentagePassed) > 0.0 || Double.valueOf(percentageFailed) > 0 ||
						Double.valueOf(percentageNotRunAndNotApplicable) > 0 || Double.valueOf(percentageNotApplicable) > 0 ||
						Double.valueOf(percentageBlocked) > 0 || Double.valueOf(percentageDeferred) > 0) {
					
					BigDecimal percentTotal = new BigDecimal((Double.valueOf(percentagePassed) + Double.valueOf(percentageFailed)
																+ Double.valueOf(percentageNotRunAndNotApplicable) + Double.valueOf(percentageNotApplicable)
																+ Double.valueOf(percentageBlocked) + Double.valueOf(percentageDeferred)));
					
					percentageTotal = percentTotal.setScale(2, RoundingMode.CEILING).toString();
					
				} else {
					percentageTotal = "100.00";
				}
          
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
		
			schedTestcaseExecVO.setStatus(status);
			schedTestcaseExecVO.setCount(count);
			schedTestcaseExecVO.setPercentage(percentage);
		
			String passed = tcPassed;
			String failed = tcFailed;
			String noRun = tcNotRunAndNotCompleted;
			String blocked = tcBlocked;
			String deferred = tcDeferred;
		
			schedManualVO.setPassed(passed);
			schedManualVO.setFailed(failed);
			schedManualVO.setNoRun(noRun);
			schedManualVO.setBlocked(blocked);
			schedManualVO.setDeferred(deferred);
		
			almTCVO.setSchedManualVO(schedManualVO);
			almTCVO.setSchedTestcaseExecVO(schedTestcaseExecVO);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return almTCVO;
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

			log.info("Testcases Entities : " + entitiesTestcases);
			log.info(testcaseStatus + "#" + "Count : " + entitiesTestcases.getTotalResults());
			
			return entitiesTestcases.getTotalResults();
		} catch (Exception e) {
			log.error("Error in getting testcases count : " + e.getMessage());
			throw e;
		}
	}
}
