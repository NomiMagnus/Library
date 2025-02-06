<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Message box</title>
</head>
<body style="font-family: Segoe Print">
	<jsp:include page="MenuManagement.jsp"></jsp:include>
	<br><br><br><br>
	<div style="background-color:orange;">
	<form method="post" action="register/controller/MessageServlet" style="margin-left:20%; margin-right: 20%;">
	<h2>Send a message</h2>
	<div style="background-color: white; padding-top:5px;"></div><br>
		<label>Subject:</label><br>
		<input id="title" name="title" maxlength="80" >
		<br>
		<label>Body:</label><br>
		<textarea required="required" id="body" name="body" cols="100" rows="15" maxlength="1000"></textarea>
		<br>
		<button type="submit" name="Send" value="Send">Send</button>
	</form>
	</div>
</body>
</html>