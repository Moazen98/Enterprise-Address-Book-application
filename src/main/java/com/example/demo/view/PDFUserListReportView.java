package com.example.demo.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.demo.modal.User;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class PDFUserListReportView extends AbstractPdfView{

	
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition","attachment; filename=\"user_list.pdf\"");
		
	
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) model.get("userList");
		
		Table table = new Table(10);

		table.addCell("ID");
		table.addCell("USERNAME");
		table.addCell("FIRSTNAME");
		table.addCell("LASTNAME");
		table.addCell("AGE");
		table.addCell("PASSWORD");
		table.addCell("Role User Id");
		table.addCell("PERMISSIONS");
		table.addCell("DEPARTMENT");
		table.addCell("Phone");
		
		
		for(User user : list) {
			
			table.addCell(String.valueOf(user.getId())); //Integer to string
			table.addCell(user.getUsername()); //String
			table.addCell(user.getFirstname());
			table.addCell(user.getLastname());
			table.addCell(String.valueOf(user.getAge()));
			table.addCell(user.getPassword());
			table.addCell(""+user.isEnabled());
			table.addCell(String.valueOf(user.getUser_role_id()));
			table.addCell(user.getDepartment());
			table.addCell(user.getPhone());
		}
		
		document.add(table);  //to add the above table to the documt
		
	}



}
