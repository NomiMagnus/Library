<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=windows-1255" pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Book list</title>
</head>
<body style="font-family: Segoe Print" >
    <jsp:include page="MenuManagement.jsp"></jsp:include>
    <br>
    <h2>Books list</h2>
    <%
        Iterator itr;
    %>
    <%
        List data = (List) request.getSession().getAttribute("bookData");
        if (data == null || data.isEmpty()) {
    %>
    		<h4 style="color: tomato">Books not found.</h4>
    <% } else { %>
    <table style="width: 70%; background-color: skyblue; margin-left: 100px; margin-right: 200px;">
        <tr>
            <td width="50">ID</td>
            <td width="119"><b>Name</b></td>
            <td width="119"><b>Author</b></td>
            <td width="500"><b>Description</b></td>
            <td>Loan</td>
            <td>Extend</td>
        </tr>
        <%
            for (itr = data.iterator(); itr.hasNext();) {
                Object idBook = itr.next();
                String name = (String) itr.next();
                String author = (String) itr.next();
                String description = (String) itr.next();
                Integer loanUserId = (int) itr.next();
                loanUserId = loanUserId == null ? -1 : loanUserId;
                boolean isLoan = (boolean) itr.next();
                boolean ITookIt = loanUserId == (int) request.getSession().getAttribute("id");
        %>
        <tr>
            <form method="post" action="register/controller/LoanBook">
            	<input type="hidden" id="idBook" name="idBook" value="<%= idBook %>"/>
                <td name="idBook" id="idBook" width="119"><%= idBook %></td>
                <td name="name" id="name" width="168"><%= name %></td>
                <td name="name" name="name" width="168"><%= author %></td>
                <td name="author" id="author" width="168"><%= description %></td>
                <td name="description" id="description" width="168">
                    <% if (isLoan==true && ITookIt) { %>
                        <input type="submit" name="return" value="RETURN">
                    <% } else if (isLoan == false && !ITookIt) { %>
                        <input type="submit" name="take" value="TAKE">
                    <% } %>
                </td>
             </form>
             <% if (isLoan==true && ITookIt) { %>
             <form method="post" action="register/controller/LoanBook">
             	<input type="hidden" id="bookId" name="bookId" value="<%= idBook %>"/>
                <td name="extend" id="extend" width="168">
                	<input type="submit" name="extend" value="EXTEND">
                </td>
            </form>
            <%} %>
        </tr>
        <% } %>
    </table>
    <% } %>
    </div>
</body>
</html>
