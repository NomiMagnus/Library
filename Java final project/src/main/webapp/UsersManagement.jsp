<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>User management</title>
</head>
<body style="font-family: Segoe Print">
	<jsp:include page="MenuManagement.jsp"></jsp:include>
	<br><br><br>
	<form method="post" action="register/controller/UsersManagementServlet">
		<table
			style="width: 70%; background-color: skyblue; margin-left: 20%; margin-right: 20%;">

			<tr>
				<td>
					<h3 style="color: brown">Add user</h3>
				</td>
			</tr>
			<tr>
				<td width="150">Choose a user type:</td>
				<td width="200">
				<input type="radio" value="worker" id="userType" name="userType" required="required" />worker
				<input type="radio" value="teacher" id="userType" name="userType" required="required" />Teacher
					<input type="radio" value="student" id="userType" name="userType" required="required" />Student
				</td>
			</tr>
			<tr>
				<td width="150">Enter User Name:</td>
				<td width="200"><input type="text" name="userName" required="required"></td>
			</tr>
			<tr>
				<td width="150">Enter Password:</td>
				<td width="200"><input type="password" name="password" required="required"></td>
			</tr>
			<tr>
				<td width="150">Enter Address:</td>
				<td width="200"><input type="text" name="address" required="required"></td>
			</tr>
			<tr>
				<td width="150">Enter Phone Number:</td>
				<td width="200"><input type="text" name="phoneNum" required="required"></td>
			</tr>
			<tr>
				<td width="150"></td>
				<td width="200"><input type="submit" name="Login" value="Submit"></td>

			</tr>
		</table>
	</form>
		<br>

		<form method="Get" action="register/controller/UsersManagementServlet">
			<table
				style="width: 70%; background-color: #ebbb87; margin-left: 20%; margin-right: 20%;">
				<tr>
					<td>
						<h3 style="color: brown">Delete user</h3>
					</td>
				</tr>
				<tr>
					<td width="150"><bold>Enter ID number: </bold></td>
					<td width="200">
						<input type="text" required="required" id="idUser" name="idUser"/>
					</td>
				</tr>
				<tr>
					<td width="150"></td>
					<td width="200"><input type="submit" name="Delete" value="Delete"></td>
				</tr>
			</table>
		</form>
</body>
</html>