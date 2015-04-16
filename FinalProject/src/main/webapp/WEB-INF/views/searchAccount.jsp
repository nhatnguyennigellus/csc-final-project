<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<h1>Search Account</h1>

				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>
					<li class="active"><i class="fa fa-file"></i> Search Account</li>
				</ol>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel panel-body">
						<form action="searchAccount" class="form-inline">

							<div class="form-group">
								<label for="idCardValue">Customer ID Card Number</label> <input
									class="form-control input-sm" type="text" id="idCardValue"
									name="idCardValue" />
							</div>

							<div class="form-group">
								<label for="accNumberValue"> Account Number</label> <input
									class="form-control input-sm" type="text" name="accNumberValue"
									id="accNumberValue" />
							</div>

							<input type="submit" value="Search" class="btn btn-success" />

						</form>
					</div>
				</div>
			</div>
		</div>
		<hr />
		<div class="row">
			<div class="col-lg-12">
				<table class="table table-striped" id="tableAccounts">
					<thead>
						<tr>
							<th>Account Number</th>
							<th>Customer</th>
							<th>Account Owner</th>

							<th>Repeatable</th>
							<th>State</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="account" items="${accountList }">
							<tr>
								<td><a href="#" id="accDetail" data-toggle="modal"
									data-target="#modalAccount"
									data-number="${account.accountNumber }"
									data-customer="${account.customer.lastName }&nbsp;${account.customer.middleName }&nbsp;${account.customer.firstName }"
									data-owner="${account.accountOwner }"
									data-balance="<fmt:formatNumber value="${account.balanceAmount }"
										type="number" />"
									data-interest="<fmt:formatNumber value="${account.interest }" type="number"/>"
									data-startdate="<fmt:formatDate value="${account.startDate }"
													type="date" />"
									data-duedate="<fmt:formatDate value="${account.dueDate }"
													type="date" />">${account.accountNumber }</a></td>
								<td>${account.customer.lastName }&nbsp;${account.customer.middleName }&nbsp;${account.customer.firstName }
								<td>${account.accountOwner }</td>

								<td><h4>
										<c:if
											test="${account.repeatable == true and account.interestRate.period != 0}">
											<span class="label label-info"> ${account.repeatable}</span>
										</c:if>
										<c:if
											test="${account.repeatable == false and account.interestRate.period != 0}">
											<span class="label label-warning">
												${account.repeatable}</span>
										</c:if>
									</h4></td>
								<td>
									<h4>
										<span
											class="
									<c:if test="${account.state == 'new'}">label label-info</c:if>
									<c:if test="${account.state == 'active'}">label label-success</c:if>
									<c:if test="${account.state == 'hold'}">label label-warning</c:if>
									<c:if test="${account.state == 'done'}">label label-danger</c:if>
									">
											${account.state}</span>
									</h4>
								</td>
								<td><sec:authorize access="hasRole('Admin')">
										<a href="modifyAccount?accNumber=${account.accountNumber }"><button
												class="btn btn-primary btn-sm">
												<span class="glyphicon glyphicon-pencil"></span>
											</button></a>
										<c:if test="${account.state == 'new' }">
											<a
												href="approveAccount?accountNumber=${account.accountNumber }"><button
													class="btn btn-success btn-sm">
													<span class="glyphicon glyphicon-ok"></span>
												</button></a>
										</c:if>
									</sec:authorize>
									<div class="modal fade" id="modalAccount" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel"
										aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">
														<span aria-hidden="true">&times;</span><span
															class="sr-only">Close</span>
													</button>
													<h4 class="modal-title" id="myModalLabel">DETAILS</h4>
												</div>
												<div class="modal-body">
													<dl class="dl-horizontal">
														<dt>Account Number:</dt>
														<dd id="accNo"></dd>
													</dl>
													<dl class="dl-horizontal">
														<dt>Customer name:</dt>
														<dd id="cus"></dd>
													</dl>
													<dl class="dl-horizontal">
														<dt>Account owner:</dt>
														<dd id="own"></dd>
													</dl>
													<dl class="dl-horizontal">
														<dt>Balance:</dt>
														<dd id="bal"></dd>
													</dl>
													<dl class="dl-horizontal">
														<dt>Interest:</dt>
														<dd id="inter"></dd>
													</dl>
													<dl class="dl-horizontal">
														<dt>Start Date:</dt>
														<dd id="start"></dd>
													</dl>
													<dl class="dl-horizontal">
														<dt>Due Date:</dt>
														<dd id="due"></dd>
													</dl>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-danger"
														data-dismiss="modal">Close</button>
												</div>
											</div>
										</div>
									</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tableAccounts').dataTable({
			"bFilter" : false,
			"aoColumnDefs" : [ {
				'bSortable' : false,
				'aTargets' : [ 5 ]
			} ]
		});
	});

	$(document).on("click", "#accDetail", function() {
		$("dd#accNo").html($(this).data('number'));
		$("dd#cus").html($(this).data('customer'));
		$("dd#own").html($(this).data('owner'));
		$("dd#bal").html($(this).data('balance') + ' VND');
		$("dd#inter").html($(this).data('interest') + '');
		$("dd#start").html($(this).data('startdate'));
		$("dd#due").html($(this).data('duedate'));
	});
</script>
</html>