package com.sqm.dashboard.schedular.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqm.dashboard.VO.SchedularDefectsVO;
import com.sqm.dashboard.schedular.AlmSchedDefectService;
import com.sqm.dashboard.util.Constants;
import com.sqm.dashboard.util.EntitiesUtility;
import com.sqm.dashboard.util.MarshallingUtility;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedDefectServiceImpl")
public class AlmSchedDefectServiceImpl implements AlmSchedDefectService {

	static final Logger log = Logger.getLogger(AlmSchedDefectServiceImpl.class);
	
	@Autowired
	private SchedularDefectsVO schedularDefectsVO;
	
	private List<String> statusSeverity = new ArrayList<String>();
	private List<String> urgent = new ArrayList<String>();
	private List<String> high = new ArrayList<String>();
	private List<String> medium = new ArrayList<String>();
	private List<String> low = new ArrayList<String>();
	private List<String> totalDefects = new ArrayList<String>();
	
	private String dOpenNewReopenedAssignedUrgent;
	private String dFixedReadyforretestUrgent;
	private String dClosedUrgent;
	private String dDuplicateRejectedUrgent;
	private String dDeferredUrgent;
	
	private String dOpenNewReopenedAssignedHigh;
	private String dFixedReadyforretestHigh;
	private String dClosedHigh;
	private String dDuplicateRejectedHigh;
	private String dDeferredHigh;
	
	private String dOpenNewReopenedAssignedMedium;
	private String dFixedReadyforretestMedium;
	private String dClosedMedium;
	private String dDuplicateRejectedMedium;
	private String dDeferredMedium;
	
	private String dOpenNewReopenedAssignedLow;
	private String dFixedReadyforretestLow;
	private String dClosedLow;
	private String dDuplicateRejectedLow;
	private String dDeferredLow;
	
	private Double totDefectsOpenNewReopenedAssigned;
	private Double totDefectsFixedReadyforretest;
	private Double totDefectsClosed;
	private Double totDefectsDuplicateRejected;
	private Double totDefectsDeferred;
	
	private Double totDefectsUrgent;
	private Double totDefectsHigh;
	private Double totDefectsMedium;
	private Double totDefectsLow;
	
	private Double totDefects;
	
	private Integer totalDefectsOpenNewReopenedAssigned;
	private Integer totalDefectsFixedReadyforretest;
	private Integer totalDefectsClosed;
	private Integer totalDefectsDuplicateRejected;
	private Integer totalDefectsDeferred;
	
	private Integer totalDefectsUrgent;
	private Integer totalDefectsHigh;
	private Integer totalDefectsMedium;
	private Integer totalDefectsLow;
	
	private Integer total;
	
	public SchedularDefectsVO getAlmDefects(RestConnectorUtility conn, Map<String, String> requestHeaders, String defectsUrl, String releaseId) {
	
			log.info("AlmSchedDefectServiceImpl almSchedDefectServiceImpl.getAlmDefects");
	
			try {
		
				dOpenNewReopenedAssignedUrgent = getAlmDefects(conn, requestHeaders, defectsUrl,
												Constants.DEFECT_STATUS_OPEN_NEW_REOPENED_ASSIGNED, Constants.DEFECT_SEVERITY_1_URGENT, releaseId);
				dOpenNewReopenedAssignedHigh = getAlmDefects(conn, requestHeaders, defectsUrl, 
												Constants.DEFECT_STATUS_OPEN_NEW_REOPENED_ASSIGNED, Constants.DEFECT_SEVERITY_2_HIGH, releaseId);
				dOpenNewReopenedAssignedMedium = getAlmDefects(conn, requestHeaders, defectsUrl, 
												Constants.DEFECT_STATUS_OPEN_NEW_REOPENED_ASSIGNED, Constants.DEFECT_SEVERITY_3_MEDIUM, releaseId);
				dOpenNewReopenedAssignedLow = getAlmDefects(conn, requestHeaders, defectsUrl, 
												Constants.DEFECT_STATUS_OPEN_NEW_REOPENED_ASSIGNED, Constants.DEFECT_SEVERITY_4_LOW, releaseId);


				log.info("dOpenNewReopenedAssignedUrgent : " + dOpenNewReopenedAssignedUrgent +
					"#dOpenNewReopenedAssignedHigh : " + dOpenNewReopenedAssignedHigh +
					"#dOpenNewReopenedAssignedMedium : " + dOpenNewReopenedAssignedMedium +
					"#dOpenNewReopenedAssignedLow : " + dOpenNewReopenedAssignedLow);
		
				statusSeverity.add(0, Constants.DEFECT_STATUS_OPEN_NEW_REOPENED_ASSIGNED);
				urgent.add(0, dOpenNewReopenedAssignedUrgent);
				high.add(0, dOpenNewReopenedAssignedHigh);
				medium.add(0, dOpenNewReopenedAssignedMedium);
				low.add(0, dOpenNewReopenedAssignedLow);
		
				dFixedReadyforretestUrgent = getAlmDefects(conn, requestHeaders, defectsUrl,
										Constants.DEFECT_STATUS_FIXED_READYFORRETEST, Constants.DEFECT_SEVERITY_1_URGENT, releaseId);
				dFixedReadyforretestHigh = getAlmDefects(conn, requestHeaders, defectsUrl, 
										Constants.DEFECT_STATUS_FIXED_READYFORRETEST, Constants.DEFECT_SEVERITY_2_HIGH, releaseId);
				dFixedReadyforretestMedium = getAlmDefects(conn, requestHeaders, defectsUrl, 
										Constants.DEFECT_STATUS_FIXED_READYFORRETEST, Constants.DEFECT_SEVERITY_3_MEDIUM, releaseId);
				dFixedReadyforretestLow = getAlmDefects(conn, requestHeaders, defectsUrl, 
										Constants.DEFECT_STATUS_FIXED_READYFORRETEST, Constants.DEFECT_SEVERITY_4_LOW, releaseId);

				log.info("dFixedReadyforretestUrgent : " + dFixedReadyforretestUrgent +
						"#dFixedReadyforretestHigh : " + dFixedReadyforretestHigh +
						"#dFixedReadyforretestMedium : " + dFixedReadyforretestMedium +
						"#dFixedReadyforretestLow : " + dFixedReadyforretestLow);
		
				statusSeverity.add(1, Constants.DEFECT_STATUS_FIXED_READYFORRETEST);
				urgent.add(1, dFixedReadyforretestUrgent);
				high.add(1, dFixedReadyforretestHigh);
				medium.add(1, dFixedReadyforretestMedium);
				low.add(1, dFixedReadyforretestLow);
		
				dClosedUrgent = getAlmDefects(conn, requestHeaders, defectsUrl,
						Constants.DEFECT_STATUS_CLOSED, Constants.DEFECT_SEVERITY_1_URGENT, releaseId);
				dClosedHigh = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_CLOSED, Constants.DEFECT_SEVERITY_2_HIGH, releaseId);
				dClosedMedium = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_CLOSED, Constants.DEFECT_SEVERITY_3_MEDIUM, releaseId);
				dClosedLow = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_CLOSED, Constants.DEFECT_SEVERITY_4_LOW, releaseId);

				log.info("dClosedUrgent : " + dClosedUrgent +
						"#dClosedHigh : " + dClosedHigh +
						"#dClosedMedium : " + dClosedMedium +
						"#dClosedLow : " + dClosedLow);
		
				statusSeverity.add(2, Constants.DEFECT_STATUS_CLOSED);
				urgent.add(2, dClosedUrgent);
				high.add(2, dClosedHigh);
				medium.add(2, dClosedMedium);
				low.add(2, dClosedLow);

				dDuplicateRejectedUrgent = getAlmDefects(conn, requestHeaders, defectsUrl,
						Constants.DEFECT_STATUS_DUPLICATE_REJECTED, Constants.DEFECT_SEVERITY_1_URGENT, releaseId);
				dDuplicateRejectedHigh = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_DUPLICATE_REJECTED, Constants.DEFECT_SEVERITY_2_HIGH, releaseId);
				dDuplicateRejectedMedium = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_DUPLICATE_REJECTED, Constants.DEFECT_SEVERITY_3_MEDIUM, releaseId);
				dDuplicateRejectedLow = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_DUPLICATE_REJECTED, Constants.DEFECT_SEVERITY_4_LOW, releaseId);

				log.info("dDuplicateRejectedUrgent : " + dDuplicateRejectedUrgent +
						"#dDuplicateRejectedHigh : " + dDuplicateRejectedHigh +
						"#dDuplicateRejectedMedium : " + dDuplicateRejectedMedium +
						"#dDuplicateRejectedLow : " + dDuplicateRejectedLow);
		
				statusSeverity.add(3, Constants.DEFECT_STATUS_DUPLICATE_REJECTED);
				urgent.add(3, dDuplicateRejectedUrgent);
				high.add(3, dDuplicateRejectedHigh);
				medium.add(3, dDuplicateRejectedMedium);
				low.add(3, dDuplicateRejectedLow);
		
				dDeferredUrgent = getAlmDefects(conn, requestHeaders, defectsUrl,
						Constants.DEFECT_STATUS_DEFERRED, Constants.DEFECT_SEVERITY_1_URGENT, releaseId);
				dDeferredHigh = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_DEFERRED, Constants.DEFECT_SEVERITY_2_HIGH, releaseId);
				dDeferredMedium = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_DEFERRED, Constants.DEFECT_SEVERITY_3_MEDIUM, releaseId);
				dDeferredLow = getAlmDefects(conn, requestHeaders, defectsUrl, 
						Constants.DEFECT_STATUS_DEFERRED, Constants.DEFECT_SEVERITY_4_LOW, releaseId);

				log.info("dDeferredUrgent : " + dDeferredUrgent +
						"#dDeferredHigh : " + dDeferredHigh +
						"#dDeferredMedium : " + dDeferredMedium +
						"#dDeferredLow : " + dDeferredLow);
		
				statusSeverity.add(4, Constants.DEFECT_STATUS_DEFERRED);
				urgent.add(4, dDeferredUrgent);
				high.add(4, dDeferredHigh);
				medium.add(4, dDeferredMedium);
				low.add(4, dDeferredLow);
		
				totDefectsUrgent = Double.valueOf(dOpenNewReopenedAssignedUrgent) + Double.valueOf(dFixedReadyforretestUrgent) + 
							Double.valueOf(dClosedUrgent) + Double.valueOf(dDuplicateRejectedUrgent) + Double.valueOf(dDeferredUrgent);

				totDefectsHigh = Double.valueOf(dOpenNewReopenedAssignedHigh) + Double.valueOf(dFixedReadyforretestHigh) + 
							Double.valueOf(dClosedHigh) + Double.valueOf(dDuplicateRejectedHigh) + Double.valueOf(dDeferredHigh);

				totDefectsMedium = Double.valueOf(dOpenNewReopenedAssignedMedium) + Double.valueOf(dFixedReadyforretestMedium) + 
							Double.valueOf(dClosedMedium) + Double.valueOf(dDuplicateRejectedMedium) + Double.valueOf(dDeferredMedium);

				totDefectsLow = Double.valueOf(dOpenNewReopenedAssignedLow) + Double.valueOf(dFixedReadyforretestLow) + 
							Double.valueOf(dClosedLow) + Double.valueOf(dDuplicateRejectedLow) + Double.valueOf(dDeferredLow);
		
				log.info("totDefectsUrgent : " + totDefectsUrgent +
						"#totDefectsHigh : " + totDefectsHigh +
						"#totDefectsMedium : " + totDefectsMedium +
						"#totDefectsLow : " + totDefectsLow);
		
				totalDefectsUrgent = totDefectsUrgent.intValue();
				totalDefectsHigh = totDefectsHigh.intValue();
				totalDefectsMedium = totDefectsMedium.intValue();
				totalDefectsLow = totDefectsLow.intValue();
				
				log.info("totalDefectsUrgent : " + totalDefectsUrgent +
						"#totalDefectsHigh : " + totalDefectsHigh +
						"#totalDefectsMedium : " + totalDefectsMedium +
						"#totalDefectsLow : " + totalDefectsLow);
				
				statusSeverity.add(5, Constants.DEFECT_STATUS_TOTAL);
				urgent.add(5, totalDefectsUrgent.toString());
				high.add(5, totalDefectsHigh.toString());
				medium.add(5, totalDefectsMedium.toString());
				low.add(5, totalDefectsLow.toString());
		
				totDefectsOpenNewReopenedAssigned = Double.valueOf(dOpenNewReopenedAssignedUrgent) + Double.valueOf(dOpenNewReopenedAssignedHigh) + 
											Double.valueOf(dOpenNewReopenedAssignedMedium) + Double.valueOf(dOpenNewReopenedAssignedLow);

				totDefectsFixedReadyforretest = Double.valueOf(dFixedReadyforretestUrgent) + Double.valueOf(dFixedReadyforretestHigh) + 
											Double.valueOf(dFixedReadyforretestMedium) + Double.valueOf(dFixedReadyforretestLow);

				totDefectsClosed = Double.valueOf(dClosedUrgent) + Double.valueOf(dClosedHigh) + 
											Double.valueOf(dClosedMedium) + Double.valueOf(dClosedLow);

				totDefectsDuplicateRejected = Double.valueOf(dDuplicateRejectedUrgent) + Double.valueOf(dDuplicateRejectedHigh) + 
											Double.valueOf(dDuplicateRejectedMedium) + Double.valueOf(dDuplicateRejectedLow);

				totDefectsDeferred = Double.valueOf(dDeferredUrgent) + Double.valueOf(dDeferredHigh) + 
											Double.valueOf(dDeferredMedium) + Double.valueOf(dDeferredLow);

				totDefects = totDefectsOpenNewReopenedAssigned + totDefectsFixedReadyforretest + 
							totDefectsClosed + totDefectsDuplicateRejected + totDefectsDeferred;
		
				log.info("totDefectsOpenNewReopenedAssigned : " + totDefectsOpenNewReopenedAssigned +
						"#totDefectsFixedReadyforretest : " + totDefectsFixedReadyforretest +
						"#totDefectsClosed : " + totDefectsClosed +
						"#totDefectsDuplicateRejected : " + totDefectsDuplicateRejected +
						"#totDefectsDeferred : " + totDefectsDeferred + 
						"#totDefects : " + totDefects);
		
				totalDefectsOpenNewReopenedAssigned = totDefectsOpenNewReopenedAssigned.intValue();
				totalDefectsFixedReadyforretest = totDefectsFixedReadyforretest.intValue();
				totalDefectsClosed = totDefectsClosed.intValue();
				totalDefectsDuplicateRejected = totDefectsDuplicateRejected.intValue();
				totalDefectsDeferred = totDefectsDeferred.intValue();
				
				total = totDefects.intValue();
				
				log.info("totalDefectsOpenNewReopenedAssigned : " + totalDefectsOpenNewReopenedAssigned +
						"#totalDefectsFixedReadyforretest : " + totalDefectsFixedReadyforretest +
						"#totalDefectsClosed : " + totalDefectsClosed +
						"#totalDefectsDuplicateRejected : " + totalDefectsDuplicateRejected +
						"#totalDefectsDeferred : " + totalDefectsDeferred + 
						"#total : " + total);
				
				totalDefects.add(0, totalDefectsOpenNewReopenedAssigned.toString());
				totalDefects.add(1, totalDefectsFixedReadyforretest.toString());
				totalDefects.add(2, totalDefectsClosed.toString());
				totalDefects.add(3, totalDefectsDuplicateRejected.toString());
				totalDefects.add(4, totalDefectsDeferred.toString());
				totalDefects.add(5, total.toString());
		
				if(total > 0) {
					BigDecimal percentUrgent = new BigDecimal((totDefectsUrgent/totDefects) * 100);
					BigDecimal percentHigh = new BigDecimal((totDefectsHigh/totDefects) * 100);
					BigDecimal percentMedium = new BigDecimal((totDefectsMedium/totDefects) * 100);
					BigDecimal percentLow = new BigDecimal((totDefectsLow/totDefects) * 100);
		
					
					
					
					String percentageUrgent = percentUrgent.setScale(2, RoundingMode.CEILING).toString()+"%";
					String percentageHigh = percentHigh.setScale(2, RoundingMode.CEILING).toString()+"%";
					String percentageMedium = percentMedium.setScale(2, RoundingMode.CEILING).toString()+"%";
					String percentageLow = percentLow.setScale(2, RoundingMode.CEILING).toString()+"%";
					
					
					/*String percentageUrgent = percentUrgent.setScale(2, RoundingMode.CEILING).toString();
					String percentageHigh = percentHigh.setScale(2, RoundingMode.CEILING).toString();
					String percentageMedium = percentMedium.setScale(2, RoundingMode.CEILING).toString();
					String percentageLow = percentLow.setScale(2, RoundingMode.CEILING).toString();
					*/
					log.info("PERCENTAGE IS "+percentageHigh);

					log.info("percentageUrgent : " + percentageUrgent + 
						"#percentageHigh : " + percentageHigh +
						"#percentageMedium : " + percentageMedium + 
						"#percentageLow : " + percentageLow);
		
					statusSeverity.add(6, Constants.DEFECT_STATUS_PERCENTAGE);
					urgent.add(6, percentageUrgent);
					high.add(6, percentageHigh);
					medium.add(6, percentageMedium);
					low.add(6, percentageLow);
			
					totalDefects.add(6, "100.00%");
					
				} else {
					statusSeverity.add(6, Constants.DEFECT_STATUS_PERCENTAGE);
					urgent.add(6, "0.00");
					high.add(6, "0.00");
					medium.add(6, "0.00");
					low.add(6, "0.00");
			
					totalDefects.add(6, "0.00");
				}

				schedularDefectsVO.setStatusSeverity(statusSeverity);
				schedularDefectsVO.setUrgent(urgent);
				schedularDefectsVO.setHigh(high);
				schedularDefectsVO.setMedium(medium);
				schedularDefectsVO.setLow(low);
				schedularDefectsVO.setTotalDefects(totalDefects);
		
				log.info("schedularDefectsVO.getStatusSeverity() : " + schedularDefectsVO.getStatusSeverity().toString() +
						"schedularDefectsVO.getUrgent() : " + schedularDefectsVO.getUrgent().toString() +
						"schedularDefectsVO.getHigh() : " + schedularDefectsVO.getHigh().toString() +
						"schedularDefectsVO.getMedium() : " + schedularDefectsVO.getMedium().toString() +
						"schedularDefectsVO.getLow() : " + schedularDefectsVO.getLow().toString() +
						"schedularDefectsVO.getTotalDefects() : " + schedularDefectsVO.getTotalDefects().toString());
		
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			return schedularDefectsVO;
	}

	static String getAlmDefects(RestConnectorUtility connection, Map<String, String> requestHeaders, String defectsUrl, 
									String defectStatus, String defectSeverity, String releaseId) throws Exception {

		StringBuilder queryAlmDefects = new StringBuilder();
		try{
			queryAlmDefects.append("query={status[");
			queryAlmDefects.append(defectStatus.replace(" ", "%20"));
			queryAlmDefects.append("]");
			queryAlmDefects.append(";severity[\"");
			queryAlmDefects.append(defectSeverity.replace(" ", "%20"));
			queryAlmDefects.append("\"];");
			queryAlmDefects.append("detected-in-rel[");
			queryAlmDefects.append(releaseId);
			queryAlmDefects.append("]}");
			queryAlmDefects.append("&fields=id");
		
			log.info("AlmDefects Query : " + queryAlmDefects);

			String listFromDefectCollectionAsXml = connection.httpGet(defectsUrl, queryAlmDefects.toString(), requestHeaders).toString();
			log.info("listFromDefectCollectionAsXml : " + listFromDefectCollectionAsXml);

			EntitiesUtility defectEntities = MarshallingUtility.marshal(EntitiesUtility.class, listFromDefectCollectionAsXml);
			log.info("Defects Entities : " + defectEntities);
			log.info(defectStatus + "#" + defectSeverity + "#" + "Count : " + defectEntities.getTotalResults());

			return defectEntities.getTotalResults();
		} catch (Exception e) {
			log.error("Error in getting defects count : " + e.getMessage());
			throw e;
		}
	}
}
