<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="‏‏favicon.ico" type="image/x-icon">
<meta charset="windows-1255">
<title>Insert title here</title>
<style>
	.flex-container {
	  display: flex;
	  background-color: DodgerBlue;
	  margin-right: 10%;
	  margin-left: 30%;
	  background-color: #47ff9b;
	  min-width: max-content;
	}

	.flex-container > a {
	  background-color: #8eecb9;
	  margin: 10px;
	  padding: 2px;
	  font-size: 15px;
	  font-family: Segoe Print;
	  margin: 5px;
	  min-width: fit-content;
	}
	
	#menu{
		 position: fixed;
	}
</style>
</head>
<body>
<div id="menu">
<%
String userType="";
if(request.getSession().getAttribute("userType") != null){ 
    userType = (String) request.getSession().getAttribute("userType");
    if(userType.equals("worker")){
%>
    <jsp:include page="MenuBar.jsp"></jsp:include>
<%
    } 
    else {
%>
    <jsp:include page="MenuBarSimpleUser.jsp"></jsp:include>
<%
    }
}else{
%>
<div class="flex-container">
		<a href="Login.jsp">login</a>
		<a href="SendMessage.jsp" style="margin: 5px">send messages</a>
	</div>
<%} %>
<br><br><br>
</div>
</body>
</html>
