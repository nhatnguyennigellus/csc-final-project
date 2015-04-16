<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h1>Search Transaction</h1>

				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>
					<li class="active"><i class="fa fa-file"></i> Search
						Transaction</li>
				</ol>
			</div>
		</div>
		<form action="submitSearchTransaction" class="form-inline">
			<div class="panel panel-info">
				<div class="panel panel-heading">
					Search By Transaction Details
				</div>
				<div class="panel panel-body">
					<div class="form-group">
						<label for="accountNumber">Account number</label> <input
							class="form-control input-sm" type="text" name="accountNumber"
							id="accountNumber" />
					</div>
					<div class="form-group">
						<label for="transactionState">State</label> <select
							name="transactionState" id="transactionState"
							class="form-control input-sm">
							<option value="all">All</option>
							<option value="Pending">Pending</option>
							<option value="Approved">Approved</option>
							<option value="Rejected">Rejected</option>
						</select>
					</div>
					<div class="form-group">
						<label for="transactionType">Type</label> <select
							name="transactionType" id="transactionType"
							class="form-control input-sm">
							<option value="all">All</option>
							<option value="Deposit">Deposit</option>
							<option value="Withdraw Interest">Withdraw Interest</option>
							<option value="Withdraw Balance">Withdraw Balance</option>
						</select> <input type="submit" name="submitAction"
							class="form-control input-sm" value="Search by details" />
					</div>
				</div>
			</div>
			<hr />
			<div class="panel panel-info">
				<div class="panel panel-heading">Search By Transaction ID</div>
				<div class="panel panel-body">
					<div class="form-group">
						<label for="transactionId">Transaction ID</label> <input
							type="text" class="form-control input-sm" name="transactionId"
							id="transactionId" /><br>
					</div>
					<input type="submit" class="form-control input-sm"
						name="submitAction" value="Search by ID" />
				</div>
			</div>
		</form>
		<hr />
		<div class="row">
			<div class="col-md-12">
				<c:if test="${apprError != null }">
					<div class="alert alert-danger" role="alert">${apprError }</div>
				</c:if>
				<c:if test="${apprSuccess != null }">
					<div class="alert alert-success" role="alert">${apprSuccess }
					</div>
				</c:if>
				<table class="table table-striped" id="tableTransactions">
					<thead>
						<tr>
							<th>ID</th>
							<th>Account Number</th>
							<th>Amount</th>
							<th>Date</th>
							<th>Type</th>
							<th>State</th>
							<th>User</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="transaction" items="${transactionList }">
							<tr>
								<td>${transaction.id }</td>
								<td>${transaction.savingAccount.accountNumber}</td>
								<td><fmt:formatNumber groupingUsed="false" type="number"
										value="${transaction.amount}" /></td>
								<td>${transaction.date }</td>
								<td>${transaction.type}</td>
								<td><h4>
										<span
											class="
									<c:if test="${transaction.state == 'Pending'}">label label-info</c:if>
									<c:if test="${transaction.state == 'Approved'}">label label-success</c:if>
									<c:if test="${transaction.state == 'Rejected'}">label label-danger</c:if>
									">
											${transaction.state}</span>
									</h4></td>
								<td><c:forEach var="user" items="${transaction.users }">
										<p>
											<c:if test="${user.role.role == 'Admin'}">
									 Approved By 
									 </c:if>
											<c:if test="${user.role.role == 'Support'}">
									 Performed By 
									 </c:if>
											<strong>${user.username }</strong>
										</p>
									</c:forEach></td>
								<td><sec:authorize access="hasRole('Admin')">
										<c:if test="${transaction.state == 'Pending' }">
											<a href="approveTransaction?transactionId=${transaction.id }">
												<button type="button" class="btn btn-success btn-sm">
													<span class="glyphicon glyphicon-ok"></span>
												</button>
											</a>
											<a href="rejectTransaction?transactionId=${transaction.id }">
												<button type="button" class="btn btn-danger btn-sm">
													<span class="glyphicon glyphicon-remove"></span>
												</button>
											</a>
										</c:if>
									</sec:authorize></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<%-- <table class="table table-striped" id="tableTransactions">
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
								<td><fmt:formatNumber value="${transaction.amount }"
										type="number" /></td>
								<td><fmt:formatDate value="${transaction.date }"
										type="date" /></td>
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
 --%>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tableTransactions').dataTable()
	});
</script>
</html>