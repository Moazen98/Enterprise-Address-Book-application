package com.example.demo.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.example.demo.modal.User;

public class ExcelUserListReportView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition","attachment; filename=\"user_list.xls\"");

		
		@SuppressWarnings("unchecked") //just for remove warring :3 
		List<User> list = (List<User>) model.get("userList");
		
		Sheet sheet = workbook.createSheet("User List");
		
		//here i create header row and set cells:3
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("USERNAME");
		header.createCell(2).setCellValue("FIRSTNAME");
		header.createCell(3).setCellValue("LASTNAME");
		header.createCell(4).setCellValue("AGE");
		header.createCell(5).setCellValue("PASSWORD");
		header.createCell(6).setCellValue("ENABLE");
		header.createCell(7).setCellValue("User role id");
		header.createCell(9).setCellValue("DEPARTMENT");
		header.createCell(10).setCellValue("Phone");
	
		int rowNum = 1;
		
		//Here I creat Row and Set Elements :3
		for(User user : list) {
			
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(user.getId());
			row.createCell(1).setCellValue(user.getUsername());
			row.createCell(2).setCellValue(user.getFirstname());
			row.createCell(3).setCellValue(user.getLastname());
			row.createCell(4).setCellValue(user.getAge());
			row.createCell(5).setCellValue(user.getPassword());
			row.createCell(6).setCellValue(user.isEnabled());
			row.createCell(7).setCellValue(user.getUser_role_id());
			row.createCell(9).setCellValue(user.getDepartment());
			row.createCell(10).setCellValue(user.getPhone());
			
		}
	
	}

}
