<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>FILL IN TRANSACTION INFORMATION</h3>
	<form action="searchTransaction" method="POST" >
		<fieldset>
			<legend>Search By Transaction Details:</legend>
			Account number: <input type="text" name="accountNumber" /><br>
			State:	
			<select name="transactionState">
				<option value="all" >All</option>
				<option value="pending">Pending</option>
				<option value="approved">Approved</option>
				<option value="rejected">Rejected</option>
			</select> <br>
			Type: 
			<select name="transactionType">
				<option value="all">All</option>
				<option value="deposit">Deposit</option>
				<option value="withdrawInterest">Withdraw Interest</option>
				<option value="withdrawBalance">Withdraw Balance</option>
			</select>
			<input type="submit" name="submitAction" value="Search by details" />
		</fieldset>
		<fieldset>
			<legend>Search By Transaction ID:</legend>
			Transaction ID: <input type="text" name="transactionId" /><br>
			<input type="submit" name="submitAction" value="Search by ID" />
		</fieldset>
	</form>
	
	<div class="row">
			<div class="col-md-12">

				<table class="table table-striped" id="tableTransactions">
					<thead>
						<tr>
							<th>Transaction ID</th>
							<th>Transaction Amount</th>
							<th>Transaction Date</th>
							<th>Transaction Owner</th>
							<th>Transaction State</th>
							<th>Transaction Type</th>
							<th>Transaction User</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="transaction" items="${transactionList }">
							<tr>
								<td>${transaction.id }</td>
								<td><fmt:formatNumber value="${transaction.amount }" type="number"/></td>
								<td><fmt:formatDate value="${transaction.date }" type="date"/></td>
								<td>${transation.savingAccount.accountNumber }</td>
								<td>${transaction.state }</td>
								<td>${transaction.type }</td>
								<td><c:forEach var="user" items="${transaction.users }">
									<p>
									<c:if test="${user.role.role == 'admin'}">
									 Approved By 
									 </c:if>
									<c:if test="${user.role.role == 'support'}">
									 Performed By 
									 </c:if>
									 <strong>${user.username }</strong>
									 </p>
								</c:forEach></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tableTransactions').dataTable()
	});
</script>
</html>