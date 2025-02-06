<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>users list</title>
</head>
<body style="font-family: Segoe Print">
	<jsp:include page="MenuManagement.jsp"></jsp:include>
	<br>
	<h2>Users list</h2>
	<%Iterator itr;
		List data = (List) request.getSession().getAttribute("userData");
		if(data == null || data.isEmpty()){%>
			<h4 style="color:tomato">Users not found.</h4>
		<%}else{%>
		
			<table style="width: 70%; background-color: skyblue; margin-left: 100px; margin-right: 200px;">
				<tr>
					<td width="50"><b>ID</b></td>
					<td width="119"><b>UserName</b></td>
					<td width="119"><b>Password</b></td>
					<td width="119"><b>User type</b></td>
				</tr>
		
				
				<%
				for (itr = data.iterator(); itr.hasNext();)
				{
				%>
				<tr>
					<td width="50"><%=itr.next()%></td>
					<td width="119"><%=itr.next()%></td>
					<td width="119"><%=itr.next()%></td>
					<td width="119"><%=itr.next()%></td>
				</tr>
				<%}%>
			</table>
		<%}%>
	
</body>
</html>