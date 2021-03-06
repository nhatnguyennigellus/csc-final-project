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

					<li class="active"><i class="glyphicon glyphicon-usd"></i>
						Perform Transaction</li>
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
								<div class="alert alert-danger" role="alert">
									<button type="button" class="close" data-dismiss="alert"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>${transError }
								</div>

							</c:if>
							<c:if test="${transSuccess != null }">

								<div class="alert alert-success" role="alert">${transSuccess }
									<strong><a href="searchTransaction" class="alert-link">Back
											to Transaction List</a></strong>
								</div>
							</c:if>
							<div class="form-group col-md-4" style="display: none;">
								<label class="control-label" for="accountId">Account
									Number</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-user"></span>
									</span>
									<form:input readonly="true" id="accountId"
										class="form-control input-sm" type="text" name="accountId"
										path="savingAccount.accountId" />
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="accountNumber">Account
									Number</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-asterisk"></span>
									</span>
									<form:input readonly="true" id="accountNumber"
										class="form-control input-sm" type="text" name="accountNumber"
										path="savingAccount.accountNumber" />
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="accountOwner">Account
									Owner</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-user"></span>
									</span><input readonly id="accountOwner" class="form-control input-sm"
										type="text" name="accountOwner"
										value="${account.accountOwner}" />
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="balance">Current
									Balance</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-usd"></span>
									</span><input readonly id="balance" class="form-control input-sm"
										type="text" name="balance"
										value="<fmt:formatNumber groupingUsed="false" maxFractionDigits="0"
							type="number" value="${account.balanceAmount}" />" />
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="interest">Monthly
									Interest</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-usd"></span>
									</span><input readonly id="interest" class="form-control input-sm"
										type="text" name="interest"
										value="<fmt:formatNumber groupingUsed="false" maxFractionDigits="0"
							type="number" value="${account.interest}" />" />

								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="interest">Withdrawable
									Total </label> <font color="red"> </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-usd"></span>
									</span><input readonly id="dueDateAmount"
										class="form-control input-sm" type="text" name="dueDateAmount"
										value="<fmt:formatNumber groupingUsed="false" maxFractionDigits="0"
							type="number" value="${DueDateTotal}" />" />

								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="interest">Before
									Due Total<font color="red"> (For Period)</font></label> <font color="red"></font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-usd"></span>
									</span><input readonly id="beforeDueAmount"
										class="form-control input-sm" type="text"
										name="beforeDueAmount"
										value="<fmt:formatNumber groupingUsed="false"  maxFractionDigits="0"
							type="number" value="${BeforeDueTotal}" />" />

								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="type">Transaction Type</label>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-tags"></span>
									</span>
									<form:select class="form-control input-sm" id="type"
										name="type" path="type">
										<c:if
											test="${account.balanceAmount != 0 and account.interest != 0 }">
											<option value="Withdraw All">Withdraw All</option>
											<c:if test="${account.interestRate.period == 0 }">
												<option value="Withdraw Balance">Withdraw Balance</option>
											</c:if>

											<option value="Withdraw Interest">Withdraw Interest</option>
										</c:if>
										<option value="Deposit">Deposit</option>
									</form:select>
								</div>
							</div>

							<div class="form-group col-md-4">
								<label class="control-label" for="accountNumber">Transaction
									Amount </label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-usd"></span>
									</span><input readonly="readonly" id="amount"
										class="form-control input-sm" type="text" name="amount"
										value="<fmt:formatNumber groupingUsed="false"  maxFractionDigits="0"
							type="number" value="${Transaction.amount}" />" />
								</div>
							</div>
							<div class="form-group col-md-10">
								<button type="submit" class="btn btn-success">
									<span class="glyphicon glyphicon-check">&nbsp;</span> Submit
								</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$('#type').click(
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