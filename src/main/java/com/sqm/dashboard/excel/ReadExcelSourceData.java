package com.sqm.dashboard.excel;

public class ReadExcelSourceData {

	public static void main(String[] args) throws Exception {
		
		ExcelReaderServiceImpl excel = new ExcelReaderServiceImpl();
		excel.readExcelFile();

	}
}
