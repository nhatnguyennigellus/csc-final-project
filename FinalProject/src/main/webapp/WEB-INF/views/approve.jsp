<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%int i = 1; %>
</head>
<body>
	<table>
		<tr>
			<td>Customer ID</td>
			<td>Status</td>
			<td>Action</td>
		</tr>
		<c:forEach var="account" items="${accountList}">
			<tr>
				<td><input type="text" name="accountNumber" id="accountNumber"
					value="${account.accountNumber }"></td>
				<td>${account.state }</td>
				<td><c:if test="${account.state == 'new' }">
						<a href="approveAccount?accountNumber=${account.accountNumber }"><input type="button" value="Approve"></a>
					</c:if></td>
			</tr>
			<%i ++; %>
		</c:forEach>

	</table>
</body>
</html>