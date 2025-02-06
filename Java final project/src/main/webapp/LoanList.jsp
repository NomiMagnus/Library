<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="windows-1255">
<title>Loans list</title>
</head>
<body style="font-family: Segoe Print">
	<jsp:include page="MenuManagement.jsp"></jsp:include>
	<br>
	<h2>History loans list</h2>
	<table style="text-align: center; width: 70%; background-color: lightyellow; margin-left: 100px; margin-right: 200px;">
    <tr>
        <th>Load Date</th>
        <th>Return Date</th>
        <th>User ID</th>
        <th>Book ID</th>
        <th>Is Returned</th>
        <th>Days Left</th>
    </tr>
    <%-- For each record in 'data', create a new row in the table --%>
    <%  Iterator itr;
        List data = (List) request.getSession().getAttribute("loanData");
        if (data != null && !data.isEmpty()) {
            for (itr = data.iterator(); itr.hasNext();) {
            	// Retrieve data from the list
                Object loadDate = itr.next();
                Object returnDate = itr.next();
                Object userId = itr.next();
                Object bookId = itr.next();
                boolean isReturned = (boolean) itr.next();
                Object extendPeriodTimes = itr.next();
                
                // Calculate the days left until return date
                long daysLeft = 0;
                if (!isReturned) {
                    long currentTime = System.currentTimeMillis();
                    long returnTime = ((java.sql.Date)returnDate).getTime();
                    daysLeft = (returnTime - currentTime) / (1000 * 60 * 60 * 24);
                }
                
                // Set the background color based on the days left
                String bgColor = (daysLeft <= 0 && !isReturned) ? "tomato" : (isReturned) ? "lightyellow" : "cornflowerblue";
                
                %>
                <tr>
                    <td><%= loadDate %></td>
                    <td><%= returnDate %></td>
                    <td><%= userId %></td>
                    <td><%= bookId %></td>
                    <td style="background-color: <%= isReturned ? "yellowgreen" : "tomato" %>;"><%= isReturned %></td>
                    <td style="background-color: <%= bgColor %>;"><%= isReturned ? '-' : daysLeft %></td>
                </tr>
                <%-- Place relevant data from 'data' in each column here --%>
                <%
            }
        } else {
            %>
            <tr>
                <td style="color:tomato" colspan="6">No data available</td>
            </tr>
            <%-- Print a message row in case there is no data in 'data' --%>
        <% } %>
</table>

</body>
</html>