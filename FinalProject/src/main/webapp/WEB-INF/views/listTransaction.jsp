<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h1>Perform Transaction</h1>

				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>

					<li class="active"><i class="fa fa-file"></i> Transaction List</li>
				</ol>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-info">
				<div class="panel-body">
					<c:if test="${apprError != null }">
						<div class="alert alert-danger" role="alert">${apprError }</div>
					</c:if>
					<c:if test="${apprSuccess != null }">
						<div class="alert alert-success" role="alert">${apprSuccess }
						</div>
					</c:if>
					<table class="table table-striped" id="tableCustomer">
						<thead>
							<tr>
								<th>ID</th>
								<th>Account Number</th>
								<th>Amount</th>
								<th>Date</th>
								<th>Type</th>
								<th>State</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="transaction" items="${listTransaction }">
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
									<td><sec:authorize access="hasRole('Admin')">
											<c:if test="${transaction.state == 'Pending' }">
												<a
													href="approveTransaction?transactionId=${transaction.id }">
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
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tableCustomer').dataTable({
			"bFilter" : false,
			"aoColumnDefs" : [ {
				'bSortable' : false,
				'aTargets' : [ 6 ]
			} ]
		});
	});
</script>
</html>