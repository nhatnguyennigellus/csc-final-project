<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%int i = 1; %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>Account Number</th>
			<th>Account Owner</th>
			<th>Balance Amount</th>
			<th>Start Date</th>
			<th>Due Date</th>
			<th>Repeatable</th>
			<th>State</th>
		</tr>
		
		<c:forEach items="${accountList}" var="account">
		<tr>
			<td><input type="text" value="${account.accountNumber}" id="accountNumber<%=i%>" name="accountNumber<%=i%>"/></td>
			<td><input type="text" value="${account.accountOwner}" id="accountOwner<%=i%>" name="accountOwner<%=i%>"/></td>
			<td><input type="text" value="${account.balanceAmount}" id="balanceAmount<%=i%>" name="balanceAmount<%=i%>"/></td>
			<td><input type="text" value="${account.startDate}" id="startDate<%=i%>" name="startDate<%=i%>"/></td>
			<td><input type="text" value="${account.dueDate}" id="dueDater<%=i%>" name="dueDate<%=i%>"/></td>
			<td><input type="text" value="${account.repeatable}" id="repeatable<%=i%>" name="repeatable<%=i%>"/></td>
			<td><input type="text" value="${account.state}" id="state<%=i%>" name="state<%=i%>"/></td>
			<td><div id="checkDiv<%=i%>" style="width: 100px; height: 25px;"><span id="checkSpan<%=i%>"></span></div></td>
		</tr>
		<%i ++; %>
	</c:forEach>
	</table>
</body>
</html>