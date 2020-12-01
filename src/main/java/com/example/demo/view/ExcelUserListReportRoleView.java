package com.example.demo.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.example.demo.modal.User_Roles;

public class ExcelUserListReportRoleView extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition","attachment; filename=\"user_list_Role.xls\"");

		
		@SuppressWarnings("unchecked") //just for remove warring :3 
		List<User_Roles> list = (List<User_Roles>) model.get("userList");
		
		Sheet sheet = workbook.createSheet("User List");
		
		//here i create header row and set cells:3
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("USERNAME");
		header.createCell(2).setCellValue("Role");
		header.createCell(3).setCellValue("Permission");
		header.createCell(4).setCellValue("Phone");
		header.createCell(5).setCellValue("version");
		int rowNum = 1;
		
		//Here I creat Row and Set Elements :3
		for(User_Roles userRole : list) {
			
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(userRole.getId());
			row.createCell(1).setCellValue(userRole.getUsername());
			row.createCell(2).setCellValue(userRole.getRoles());
			row.createCell(3).setCellValue(userRole.getPermissions());
			row.createCell(4).setCellValue(userRole.getPhone());
			row.createCell(5).setCellValue(userRole.getVersion());

		}
	
	}

}
