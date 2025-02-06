<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Insert title here</title>
<style>
	.flex-container {
	  display: flex;
	  background-color: DodgerBlue;
	  margin-right: 100%;
	  margin-left: 30%;
	  background-color: #47c8ff;
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
</style>
</head>
<body>
	<div class="flex-container">
		<a href="HistoryLoansList.jsp">my history list</a>
		<a href="SendMessage.jsp">send messages</a>
		<a href="BookList.jsp">books list</a>
	</div>
</body>
</html>