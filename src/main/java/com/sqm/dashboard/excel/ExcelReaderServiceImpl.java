package com.sqm.dashboard.excel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.VO.SchedularDefectsVO;
import com.sqm.dashboard.VO.SchedularTCExecStatusVO;
import com.sqm.dashboard.dao.impl.AlmSchedularDAOImpl;

public class ExcelReaderServiceImpl {/*

	AlmSchedularDAOImpl dbObj=new AlmSchedularDAOImpl();
	SchedularDefectsVO defectsVO = new SchedularDefectsVO();
	
	private List<String> statusSeverity = new ArrayList<String>();
	private List<String> urgent = new ArrayList<String>();
	private List<String> high = new ArrayList<String>();
	private List<String> medium = new ArrayList<String>();
	private List<String> low = new ArrayList<String>();
	private List<String> totalDefects = new ArrayList<String>();

	SchedularTCExecStatusVO testcaseVO = new SchedularTCExecStatusVO();

	AlmVO almVO = new AlmVO();
	
	private List<String> status = new ArrayList<String>();
	private List<String> count = new ArrayList<String>();
	private List<String> percentage = new ArrayList<String>();
	
	//DefectsStatus objects
	private String dOpenNewReopenedAssignedUrgent = null;
	private String dFixedReadyforretestUrgent = null;
	private String dClosedUrgent = null;
	private String dDuplicateRejectedUrgent = null;
	private String dDeferredUrgent = null;

	private String dOpenNewReopenedAssignedHigh = null;
	private String dFixedReadyforretestHigh = null;
	private String dClosedHigh = null;
	private String dDuplicateRejectedHigh = null;
	private String dDeferredHigh = null;

	private String dOpenNewReopenedAssignedMedium = null;
	private String dFixedReadyforretestMedium = null;
	private String dClosedMedium = null;
	private String dDuplicateRejectedMedium = null;
	private String dDeferredMedium = null;

	private String dOpenNewReopenedAssignedLow = null;
	private String dFixedReadyforretestLow = null;
	private String dClosedLow = null;
	private String dDuplicateRejectedLow = null;
	private String dDeferredLow = null;
	
	private Double totDefectsOpenNewReopenedAssigned = null;
	private Double totDefectsFixedReadyforretest = null;
	private Double totDefectsClosed = null;
	private Double totDefectsDuplicateRejected = null;
	private Double totDefectsDeferred = null;

	private Double totDefectsUrgent = null;
	private Double totDefectsHigh = null;
	private Double totDefectsMedium = null;
	private Double totDefectsLow = null;
	private Double totDefects = null;
	
    //TestExecutionStatus objects
	private String tcPassed = null;
	private String tcFailed = null;
	private String tcNotRunAndNotCompleted = null;
	private String tcNA = null;
	private String tcDeferred = null;
	private String tcBlocked = null;
		
	private Double tcTotal = null;
	
	public void readExcelFile() throws Exception{
		try {
			InputStream input = new BufferedInputStream(new FileInputStream("D:\\Anil\\UpdatedWorkspace\\SQMDashboard\\src\\main\\java\\com\\sqm\\dashboard\\excel\\alm_data.xls"));
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("ALM");

			Iterator rows = sheet.rowIterator();
			
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				
				if(row.getRowNum() >=2 && row.getRowNum()<=6){     
					Iterator cells = row.cellIterator();
				
					while (cells.hasNext()) {

						HSSFCell cell = (HSSFCell) cells.next();
						cell.setCellType(Cell.CELL_TYPE_STRING);
					
						if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
							if (((HSSFCell) cell).getColumnIndex() == 0) {
								statusSeverity.add(cell.getStringCellValue());
							} else if (((HSSFCell) cell).getColumnIndex() == 1) {
								urgent.add(cell.getStringCellValue());
							} else if (((HSSFCell) cell).getColumnIndex() == 2) {
								high.add(cell.getStringCellValue());
							} else if (((HSSFCell) cell).getColumnIndex() == 3) {
								medium.add(cell.getStringCellValue());
							} else if (((HSSFCell) cell).getColumnIndex() == 4) {
								low.add(cell.getStringCellValue());
							}else {
								System.out.print("Unknown cell type");
							}
						}
					}
				} else if(row.getRowNum() >=10 && row.getRowNum() <= 15){
					Iterator cells = row.cellIterator();
						while (cells.hasNext()) {
							HSSFCell cell = (HSSFCell) cells.next();
							cell.setCellType(Cell.CELL_TYPE_STRING);
						
							if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
								if (((HSSFCell) cell).getColumnIndex() == 0) {
									status.add(cell.getStringCellValue());
								} else if (((HSSFCell) cell).getColumnIndex() == 1) {
									count.add(cell.getStringCellValue());
								}
							} else {
								System.out.print("Unknown cell type");
							}
						}
				}
			}

			//urgent column objects
			for (int i = 0; i < urgent.size(); i++) {

				if(urgent.get(i).equals(urgent.get(0))){
					dOpenNewReopenedAssignedUrgent = urgent.get(0);
				} else if(urgent.get(i) == urgent.get(1)){
					dFixedReadyforretestUrgent = urgent.get(1);
				} else if(urgent.get(i) == urgent.get(2)){
					dClosedUrgent = urgent.get(2);
				} else if(urgent.get(i) == urgent.get(3)){
					dDuplicateRejectedUrgent = urgent.get(3);
				} else if(urgent.get(i) == urgent.get(4)){
					dDeferredUrgent = urgent.get(4);
				} 
			}
			
			//high column objects
			for (int i = 0; i < high.size(); i++) {
			
				if(high.get(i) == high.get(0)){
					dOpenNewReopenedAssignedHigh =high.get(0);
				} else if(high.get(i) == high.get(1)){
					dFixedReadyforretestHigh = high.get(1);
				} else if(high.get(i) == high.get(2)){
					dClosedHigh = high.get(2);
				} else if(high.get(i) == high.get(3)){
					dDuplicateRejectedHigh = high.get(3);
				} else if(high.get(i) == high.get(4)){
					dDeferredHigh = high.get(4);
				}
			}
			
			//medium column objects
			for (int i = 0; i < medium.size(); i++) {
			
				if(medium.get(i) == medium.get(0)){
					dOpenNewReopenedAssignedMedium =medium.get(0);
				} else if(medium.get(i) == medium.get(1)){
					dFixedReadyforretestMedium = medium.get(1);
				} else if(medium.get(i) == medium.get(2)){
					dClosedMedium = medium.get(2);
				} else if(medium.get(i) == medium.get(3)){
					dDuplicateRejectedMedium = medium.get(3);								
				} else if(medium.get(i) == medium.get(4)) {
					dDeferredMedium = medium.get(4);
				}
			}
			
			//low column objects
			for (int i = 0; i < low.size(); i++) {
			
				if(low.get(i) == low.get(0)){
					dOpenNewReopenedAssignedLow =low.get(0);
				} else if(low.get(i) == low.get(1)){
					dFixedReadyforretestLow = low.get(1);
				} else if(low.get(i) == low.get(2)){
					dClosedLow = low.get(2);
				} else if(low.get(i) == low.get(3)){
					dDuplicateRejectedLow = low.get(3);								
				} else if (low.get(i) == low.get(4)){
					dDeferredLow = low.get(4);
				}
			}
				
			//totaldefects column value
			
			totDefectsUrgent = Double.valueOf(dOpenNewReopenedAssignedUrgent) + Double.valueOf(dFixedReadyforretestUrgent) + 
									Double.valueOf(dClosedUrgent) + Double.valueOf(dDuplicateRejectedUrgent) + Double.valueOf(dDeferredUrgent);

            totDefectsHigh = Double.valueOf(dOpenNewReopenedAssignedHigh) + Double.valueOf(dFixedReadyforretestHigh) + 
            						Double.valueOf(dClosedHigh) + Double.valueOf(dDuplicateRejectedHigh) + Double.valueOf(dDeferredHigh);

            totDefectsMedium = Double.valueOf(dOpenNewReopenedAssignedMedium) + Double.valueOf(dFixedReadyforretestMedium) + 
            						Double.valueOf(dClosedMedium) + Double.valueOf(dDuplicateRejectedMedium) + Double.valueOf(dDeferredMedium);

            totDefectsLow = Double.valueOf(dOpenNewReopenedAssignedLow) + Double.valueOf(dFixedReadyforretestLow) + 
            						Double.valueOf(dClosedLow) + Double.valueOf(dDuplicateRejectedLow) + Double.valueOf(dDeferredLow);
                
            urgent.add(5, totDefectsUrgent.toString());
			high.add(5, totDefectsHigh.toString());
			medium.add(5, totDefectsMedium.toString());
			low.add(5, totDefectsLow.toString());
            
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
			
            totalDefects.add(0, totDefectsOpenNewReopenedAssigned.toString());
			totalDefects.add(1, totDefectsFixedReadyforretest.toString());
			totalDefects.add(2, totDefectsClosed.toString());
			totalDefects.add(3, totDefectsDuplicateRejected.toString());
			totalDefects.add(4, totDefectsDeferred.toString());
			totalDefects.add(5, totDefects.toString());
				
			BigDecimal percentUrgent = new BigDecimal((totDefectsUrgent/totDefects) * 100);
			BigDecimal percentHigh = new BigDecimal((totDefectsHigh/totDefects) * 100);
			BigDecimal percentMedium = new BigDecimal((totDefectsMedium/totDefects) * 100);
			BigDecimal percentLow = new BigDecimal((totDefectsLow/totDefects) * 100);

			String percentageUrgent = percentUrgent.setScale(2, RoundingMode.CEILING).toString();
			String percentageHigh = percentHigh.setScale(2, RoundingMode.CEILING).toString();
			String percentageMedium = percentMedium.setScale(2, RoundingMode.CEILING).toString();
			String percentageLow = percentLow.setScale(2, RoundingMode.CEILING).toString();
			
			statusSeverity.add(5, "Total");
			statusSeverity.add(6, "Percentage");
			
			urgent.add(6, percentageUrgent);
			high.add(6, percentageHigh);
			medium.add(6, percentageMedium);
			low.add(6, percentageLow);
			totalDefects.add(6, "100.00");

			// testcasesExecution count column objects
			for (int i=0; i<count.size(); i++) {

				if(count.get(i) == count.get(0)){
					tcPassed = count.get(0);
				} else if(count.get(i) == count.get(1)){
					tcFailed = count.get(1);
				} else if(count.get(i) == count.get(2)){
					tcNotRunAndNotCompleted = count.get(2);
				} else if(count.get(i) == count.get(3)){
					tcNA = count.get(3);
				} else if(count.get(i) == count.get(4)){
					tcDeferred = count.get(4);
				} else if((count.get(5)).equals(count.get(5))) {
					tcBlocked = count.get(5);
				} 
			}
			
			tcBlocked = count.get(5);
			status.add(6, "Total");
			
			tcTotal = Double.valueOf(tcPassed) + Double.valueOf(tcFailed) + Double.valueOf(tcNotRunAndNotCompleted) +
							Double.valueOf(tcNA) + Double.valueOf(tcDeferred) + Double.valueOf(tcBlocked);
				   
			count.add(6, tcTotal.toString());
			
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
			
			defectsVO.setStatusSeverity(statusSeverity);
			defectsVO.setUrgent(urgent);
			defectsVO.setHigh(high);
			defectsVO.setMedium(medium);
			defectsVO.setLow(low);
			defectsVO.setTotalDefects(totalDefects);
			
			String passed = tcPassed;
			String failed = tcFailed;
			String noRun = tcNotRunAndNotCompleted;
			String blocked = tcBlocked;
			String deferred = tcDeferred;
			
			testcaseVO.setPassed(passed);
			testcaseVO.setFailed(failed);
			testcaseVO.setNoRun(noRun);
			testcaseVO.setBlocked(blocked);
			testcaseVO.setDeferred(deferred);
			
			testcaseVO.setStatus(status);
			testcaseVO.setCount(count);
			testcaseVO.setPercentage(percentage);
			almVO.setDomain("CIIIB");
			almVO.setProject("Quantam");
			almVO.setRelease("Feb2015Release");
			almVO.setDefectsVO(defectsVO);
			almVO.setTestcaseVO(testcaseVO);
			
			System.out.println(almVO.getDefectsVO().toString());
			System.out.println(almVO.getTestcaseVO().toString());
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		dbObj.insertAlmToDb(almVO);
		//return almVO;
	}
*/}