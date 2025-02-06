<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>My messages</title>
</head>
<body style="font-family: Segoe Print">
	<jsp:include page="MenuManagement.jsp"></jsp:include>
	<br>	
	<h2>Messages list</h2>
	<%Iterator itr;
		List data = (List) request.getSession().getAttribute("messageData");
		if(data == null || data.isEmpty()){%>
			<h4 style="color:tomato">Messages not found.</h4>
		<%}else{%>
		
			<table border="1" style="width: 70%; background-color: orange; margin-left: 100px; margin-right: 200px;">
				<tr>
					<td width="50"><b>ID</b></td>
					<td width="119"><b>Title</b></td>
					<td width="119"><b>Body</b></td>
					<!-- <td width="119"><b>UserId</b></td>
					<td><b>Delete</b></td> -->
				</tr>
				
				<%
				for (itr = data.iterator(); itr.hasNext();)
				{
				%>
				<tr>
					<form method="post" action="register/controller/MessageServlet">
						<td required="required" width="50"><%=itr.next()%></td>
						<td required="required" width="119"><%=itr.next()%></td>
						<td required="required" width="119"><%=itr.next()%></td>
						<!--<td required="required" width="119"><%=itr.next()%></td>
						 <td><button type=submit name="delete" id="delete" value="  X  "></button></td> -->
					</form>
				</tr>
				<%}%>
			</table>
		<%}%>
</body>
</html>