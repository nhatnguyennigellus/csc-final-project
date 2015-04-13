<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

					<li class="active"><i class="fa fa-file"></i> Perform
						Transaction</li>
				</ol>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-body">
						<form:form action="submitTransaction" method="post" id="frmTrans"
							modelAttribute="Transaction">
							<c:if test="${transError != null }">
								<div class="alert alert-danger" role="alert">${transError }</div>
							</c:if>
							<c:if test="${transSuccess != null }">

								<div class="alert alert-success" role="alert">${transSuccess }
									<strong><a href="searchTransaction" class="alert-link">Back
											to Transaction List</a></strong>
								</div>
							</c:if>
							<div class="form-group col-md-4">
								<label class="control-label" for="accountNumber">Account
									Number</label> <font color="red">* <form:input readonly="true"
										id="accountNumber" class="form-control input-sm" type="text"
										name="accountNumber" path="savingAccount.accountNumber" />
								</font>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="accountNumber">Account
									Owner</label> <font color="red">* <input readonly
									id="accountOwner" class="form-control input-sm" type="text"
									name="accountOwner" value="${account.accountOwner}" />
								</font>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="accountNumber">Current
									Balance</label> <font color="red">* <input readonly id="balance"
									class="form-control input-sm" type="text" name="balance"
									value="<fmt:formatNumber groupingUsed="false" maxFractionDigits="0"
							type="number" value="${account.balanceAmount}" />" />
								</font>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="interest">Monthly
									Interest</label> <font color="red">* <input readonly
									id="interest" class="form-control input-sm" type="text"
									name="interest"
									value="<fmt:formatNumber groupingUsed="false" maxFractionDigits="0"
							type="number" value="${account.interest}" />" />

								</font>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="interest">Withdrawable
									Total </label> <font color="red"><input readonly
									id="dueDateAmount" class="form-control input-sm" type="text"
									name="dueDateAmount" 
									value="<fmt:formatNumber groupingUsed="false" maxFractionDigits="0"
							type="number" value="${DueDateTotal}" />" />

								</font>
							</div>
								<div class="form-group col-md-3">
									<label class="control-label" for="interest">Total
										Before Due (Period)</label> <font color="red"><input readonly
										id="beforeDueAmount" class="form-control input-sm" type="text"
										name="beforeDueAmount"
										value="<fmt:formatNumber groupingUsed="false"  maxFractionDigits="0"
							type="number" value="${BeforeDueTotal}" />" />

									</font>
								</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="type">Transaction Type</label>
								<form:select class="form-control input-sm" id="type" name="type"
									path="type">
									<option value="Withdraw All">Withdraw All</option>
									<c:if test="${account.interestRate.id == 1 }">
										<option value="Withdraw Balance">Withdraw Balance</option>
									</c:if>

									<option value="Withdraw Interest">Withdraw Interest</option>
									<option value="Deposit">Deposit</option>
								</form:select>
							</div>

							<div class="form-group col-md-3">
								<label class="control-label" for="accountNumber">Transaction
									Amount </label> <font color="red">* <input readonly="readonly"
									id="amount" class="form-control input-sm" type="text"
									name="amount"
									value="<fmt:formatNumber groupingUsed="false"  maxFractionDigits="0"
							type="number" value="${Transaction.amount}" />" />
								</font>
							</div>
							<div class="form-group col-md-10">
								<input type="submit" value="Submit" name="subRegister"
									class="btn btn-success" />
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$('#type').change(
			function() {

				if ($(this).val() == 'Withdraw Balance'
						|| $(this).val() == 'Deposit') {
					$("#amount").attr("readonly", false);
				} else if ($(this).val() == 'Withdraw All'
						|| $(this).val() == 'Withdraw Interest') {
					$("#amount").attr("readonly", true);
				}

			});
</script>
</html>