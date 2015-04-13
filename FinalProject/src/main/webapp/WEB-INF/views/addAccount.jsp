<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<h1>Add Account</h1>

				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>
					<li><i class="fa fa-file"></i> <a href="viewCustomer">
							Customer List</a></li>
					<li class="active"><i class="fa fa-file"></i> Add Account</li>
				</ol>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-info">
					<div class="panel-body">
						<form:form action="addAccount" method="post" role="form"
							modelAttribute="savingAccount" id="frmAddAccount">
							<c:if test="${addAccError != null }">

								<div class="alert alert-danger" role="alert">${addAccError }</div>
							</c:if>
							<c:if test="${addAccSuccess != null }">

								<div class="alert alert-success" role="alert">${addAccSuccess }
									<strong><a href="viewCustomer" class="alert-link">Back
											to customer list</a> or <a href="searchAccount"
										class="alert-link">Back to account list</a></strong>
								</div>
							</c:if>
							<div class="form-group col-md-6">
								<label class="control-label" for="accountNumber">Account
									Number</label> <font color="red">* <form:input readonly="true"
										id="accountNumber" class="form-control input-sm" type="text"
										name="accountNumber" path="accountNumber" />
								</font>
							</div>
							<div class="form-group col-md-6">
								<label class="control-label" for="accountOwner">Account
									Owner </label> <font color="red">* <form:input id="accountOwner"
										class="form-control input-sm" type="text" name="accountOwner"
										path="accountOwner" />
								</font>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="balanceAmount">Saving
									Amount</label> <font color="red">* <form:input
										id="balanceAmount" class="form-control input-sm" type="text"
										name="balanceAmount" path="balanceAmount" />
								</font>
							</div>
							<div class="form-group col-md-4" style="display: none;">
								<input id="idCardNumber" readonly="readonly"
									value="${customerId }" class="form-control input-sm"
									type="text" name="customerId" />
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="period">Saving Period</label>
								<font color="red">* <select class="form-control input-sm"
									id="period" name="period">
										<option value="0">No period</option>
										<option value="1">1 month</option>
										<option value="2">2 months</option>
										<option value="3">3 months</option>
										<option value="6">6 months</option>
										<option value="12">12 months</option>
										<option value="24">24 months</option>
								</select>
								</font>
							</div>
							<div class="form-group">
								<div class="col-sm-6">
									<div class="checkbox">
										<label> <input type="checkbox" id="repeatable"
											name="repeatable" /> Repeatable
										</label>
									</div>
								</div>
							</div>


							<div class="form-group col-md-8">
								<input type="submit" value="Submit" name="subRegister"
									class="btn btn-success" /> <input type="reset" value="Reset"
									class="btn btn-danger" />
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$.validator.addMethod('minStrict', function(value, el, param) {
			return value > param;
		});

		$.validator.addMethod("exactlength", function(value, element, param) {
			return this.optional(element) || value.length == param;
		}, jQuery.format("Please enter exactly {0} digits."));

		$("#frmAddAccount").validate({
			rules : {
				accountNumber : {
					required : true,
					digits : true,
					exactlength : 12
				},
				accountOwner : {
					required : true,
				},
				balanceAmount : {
					required : true,
					number : true,
					minStrict : 1000000
				}
			},
			messages : {
				accountNumber : {
					required : "Account Number is required",
					digits : "Please enter digits only",
					rangeLength : "Account Number must contain 12 digits"
				},
				accountOwner : {
					required : "Account Owner is required",

				},
				balanceAmount : {
					required : "Balance Amount is required",
					number : "Please enter a number",
					minStrict : "Balance Amount must be at least 1.000.000 VND"
				}
			},

		})
	});
</script>
</html>