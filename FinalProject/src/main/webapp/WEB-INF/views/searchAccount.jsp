<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>ENTER ACCOUNT INFORMATION</h3>
	<form action="searchAccount" method="POST">
		<fieldset>
			<legend>Information</legend>
			Customer ID Card Number: <input type="text" name="idCardValue"/>
			Account Number: <input type="text" name="accNumberValue" />
			<br>
			<input type="submit" value="Search" />
		</fieldset>
	</form>
	
	<div class="row">
			<div class="col-md-12">

				<table class="table table-striped" id="tableAccounts">
					<thead>
						<tr>
							<th>Account Number</th>
							<th>Owner</th>
							<th>Account Name</th>
							<th>Balance</th>
							<th>Period</th>
							<th>Interest</th>
							<th>Start Date</th>
							<th>Due Date</th>
							<th>Repeatable</th>
							<th>State</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="account" items="${accountList }">
							<tr>
								<td>${account.accountNumber }</td>
								<td>${account.customer.lastName }&nbsp;${account.customer.firstName }
								<td>${account.accountOwner }</td>
								<td><fmt:formatNumber value="${account.balanceAmount }" type="number"/></td>
								<!-- fix if period ==0 => nolimit-->
								<td>${account.interestRate.period }</td>
								<td>${account.interest }</td>
								<td><fmt:formatDate value="${account.startDate }" type="date"/></td>
								<td><fmt:formatDate value="${account.dueDate }" type="date"/></td>
								<td>${account.repeatable }</td>
								<td>${account.state }</td>
								<td><a href="modifyAccount?accNumber="><button>Edit Account</button></a></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tableAccounts').dataTable()
	});
</script>
</html>