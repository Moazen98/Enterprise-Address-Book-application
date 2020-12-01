package com.example.demo.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.demo.modal.User;
import com.example.demo.modal.User_Roles;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class PDFUserListReportRoleView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition","attachment; filename=\"user_list_Role.pdf\"");
		
	
		@SuppressWarnings("unchecked")
		List<User_Roles> list = (List<User_Roles>) model.get("userList");
		
		Table table = new Table(10);

		table.addCell("ID");
		table.addCell("USERNAME");
		table.addCell("Role");
		table.addCell("Permission");
		table.addCell("Phone");
		table.addCell("Version");

		
		
		for(User_Roles userRoel : list) {
			
			table.addCell(String.valueOf(userRoel.getId())); //Integer to string
			table.addCell(userRoel.getUsername()); //String
			table.addCell(userRoel.getRoles());
			table.addCell(userRoel.getPermissions());
			table.addCell(String.valueOf(userRoel.getPhone()));
			table.addCell(String.valueOf(userRoel.getVersion()));
			
		}
		
		document.add(table);  //to add the above table to the documt
		
	}



}
