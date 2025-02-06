<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Login</title>
<style>
	.worning{
		color: tomato;
		text-align: center;
	}
</style>
</head>
<body style="font-family: Segoe Print">
<%
    int loginTryCount = 0;
    if(request.getSession().getAttribute("loginTryCount") != null) {
        loginTryCount = (int) request.getSession().getAttribute("loginTryCount");
    }
    if(loginTryCount == 4) {
%>
    <h2 class="worning">You have no permissions to enter the library.</h2>
<%
    } else {
%>
    <form method="post" action="register/controller/UsersManagementServlet">
        <table style="width: 70%; background-color: skyblue; margin:10%">
            <tr>
                <td>
                    <h3 style="color: brown">Login</h3>
                </td>
            </tr>
            <tr>
                <td>Enter User Name:</td>
                <td><input type="text" name="userName" required="required"></td>
            </tr>
            <tr>
                <td>Enter Password:</td>
                <td><input type="password" name="password" required="required"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="Login" value="Login"></td>
            </tr>
            <tr>
                <td>Don't have an account? Let us know!</td>
                <td><a href="SendMessage.jsp">Send a message</a></td>
            </tr>
            <%
            String message="";
            if(request.getSession().getAttribute("message") != null) {
            	message = (String) request.getSession().getAttribute("message");
            }
            if(message.equals("User not found")){
             %>
            <tr style="background-color: bisque;">
            	<td><h2 class="worning">User not found</h2></td>
	            <%String triesLeft =loginTryCount==3? ""+ (4-loginTryCount) + " try left." :  ""+ (4-loginTryCount) + " tries left."; %>
	            <td><h4 style="text-align: center;"><%=triesLeft%></h4></td>
            </tr>
            <%} %>
        </table>
    </form>
<%
    }
%>
</body>
</html>
