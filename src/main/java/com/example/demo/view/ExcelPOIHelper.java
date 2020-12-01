//package com.example.demo.view;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//
//
///*
//public class ExcelPOIHelper {
//	
//	Cell cell;
//	
//	HSSFCellStyle cellStyle = (HSSFCellStyle) cell.getCellStyle();
//	 
//	MyCell myCell = new MyCell();
//	
//	Workbook workbook;
//
//	public ExcelPOIHelper() {
//		
//	}
//	
//	
//	public Map<Integer, List<MyCell>> readExcel(String fileLocation) {
//		
//		
//		 
//		HSSFColor bgColor = cellStyle.getFillForegroundColorColor();
//		if (bgColor != null) {
//		    short[] rgbColor = bgColor.getTriplet();
//		    myCell.setBgColor("rgb(" + rgbColor[0] + ","
//		      + rgbColor[1] + "," + rgbColor[2] + ")");
//		    }
//	//	XSSFFont font = ((XSSFCellStyle) cell.getCellStyle()).setFont((Font) workbook);
//		return null;
//		
//		
//		
//		//obtain the font size, weight, and color:
//		
//	//	myCell.setTextSize(font.getFontHeightInPoints() + "");
//	//	if (font.getBold()) {
//	//	    myCell.setTextWeight("bold");
//	//	}
//	//	HSSFColor textColor = ((HSSFFont) font).getHSSFColor((HSSFWorkbook) workbook);
//		//if (textColor != null) {
//		//    short[] rgbColor = textColor.getTriplet();
//		//    myCell.setTextColor("rgb(" + rgbColor[0] + ","
//		//      + rgbColor[1] + "," + rgbColor[2] + ")");
//		}
//		
//		
//		
//		XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
//		 
//		MyCell myCell = new MyCell();
//		 bgColor = cellStyle.getFillForegroundColorColor();
//		if (bgColor != null) {
//		    byte[] rgbColor = ((XSSFColor) bgColor).getRGB();
//		    myCell.setBgColor("rgb("
//		      + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + ","
//		      + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + ","
//		      + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
//		}
//		 font = cellStyle.getFont();
//		
//		
//		
//	}
//
//}
//*/