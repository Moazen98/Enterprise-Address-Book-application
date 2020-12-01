<!DOCTYPE html>
<%@ page import="com.example.demo.modal.*"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome To Zeon</title>
<link href="static/css/notfound.css" rel="stylesheet">
</head>
<body>
	<%
		HttpSession s = request.getSession();
		User user = (User) s.getAttribute("myuser");
	%>


	<header id="showcase">

		welcome
		<%
		out.print(user.getUsername());
	%>

		<%
			out.print(user.getLastname());
		%>
		<%
			out.print(user.getEmail());
		%>
		<%
			out.print(user.getPassword());
		%>

	</header>



</body>
</html>




