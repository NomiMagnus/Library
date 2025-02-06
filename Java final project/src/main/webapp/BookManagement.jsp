<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Book management</title>
</head>
<body style="font-family: Segoe Print">
	<jsp:include page="MenuManagement.jsp"></jsp:include>
	<br><br><br>
	<form method="post" action="register/controller/BookServlet">
		<table
			style="width: 70%; background-color: skyblue; margin-left: 20%; margin-right: 20%; ">

			<tr>
				<td>
					<h3 style="color: brown">Add book</h3>
				</td>
			</tr>
			<tr>
				<td>Enter a book Name:</td>
				<td><input type="text" name="name" required="required"></td>
			</tr>
			<tr>
				<td>Enter an author:</td>
				<td><input type="text" name="author" required="required"></td>
			</tr>
			<tr>
				<td>Enter a description:</td>
				<td><input type="text" name="description" required="required"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="Add" value="Add"></td>

			</tr>
		</table>
	</form>
		<br>

		<form method="post" action="register/controller/BookServlet">
			<table
				style="width: 70%; background-color: #ebbb87; margin-left: 20%; margin-right: 20%;">
				<tr>
					<td>
						<h3 style="color: brown">Delete book</h3>
					</td>
				</tr>
				<tr>
					<td><bold>Enter a book id:</bold></td>
					<td><input type="text" required="required" name="idBook" id="idBook"/></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="delete" value="delete"></td>
				</tr>
			</table>
		</form>
</body>
</html>