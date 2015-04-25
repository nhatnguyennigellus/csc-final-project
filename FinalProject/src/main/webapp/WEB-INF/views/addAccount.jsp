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
					<li class="active"><i class="glyphicon glyphicon-plus"></i>
						Add Account</li>
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

								<div class="alert alert-danger" role="alert">
									<button type="button" class="close" data-dismiss="alert"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>${addAccError }</div>
							</c:if>
							<c:if test="${addAccSuccess != null }">

								<div class="alert alert-success" role="alert">${addAccSuccess }
									<button type="button" class="close" data-dismiss="alert"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<strong><a href="viewCustomer" class="alert-link">Back
											to customer list</a> or <a href="searchAccount"
										class="alert-link">Back to account list</a></strong>
								</div>
							</c:if>
							<div class="form-group col-md-6">
								<label class="control-label" for="accountNumber">Account
									Number</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-asterisk"></span>
									</span>
									<form:input readonly="true" id="accountNumber"
										class="form-control input-sm" type="text" name="accountNumber"
										path="accountNumber" />

								</div>
							</div>
							<div class="form-group col-md-6">
								<label class="control-label" for="accountOwner">Account
									Owner </label><font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-user"></span>
									</span>
									<form:input id="accountOwner" class="form-control input-sm"
										type="text" name="accountOwner" path="accountOwner" />
								</div>
							</div>
							<div class="form-group col-md-6" style="display: none;">
								<label class="control-label" for="balanceAmount">Saving
									Amount</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-usd"></span>
									</span>
									<form:input id="balanceAmount" class="form-control input-sm"
										readonly="true" type="text" name="balanceAmount"
										path="balanceAmount" />
								</div>
							</div>
							<div class="form-group col-md-4" style="display: none;">
								<input id="idCardNumber" readonly="readonly"
									value="${customerId }" class="form-control input-sm"
									type="text" name="customerId" />
							</div>
							<div class="form-group col-md-8">
								<label class="control-label" for="period">Saving Period</label>
								<font color="red">* <select class="form-control input-sm"
									id="period" name="period">
										<c:forEach items="${rateList }" var="rate">
											<c:choose>
												<c:when test="${rate.period == 0}">
													<option value="0">No period</option>
												</c:when>
												<c:otherwise>
													<option value="${rate.id}">${rate.period}
														months</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
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
								<button type="submit" class="btn btn-success">
									<span class="glyphicon glyphicon-check">&nbsp;</span> Submit
								</button>
								<button type="reset" class="btn btn-danger">
									<span class="glyphicon glyphicon-refresh">&nbsp;</span> Reset
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
	$(function() {
		$.validator.addMethod('minStrict', function(value, el, param) {
			return value >= param;
		});

		$.validator.addMethod("exactlength", function(value, element, param) {
			return this.optional(element) || value.length == param;
		}, jQuery.format("Please enter exactly {0} digits."));

		$.validator.setDefaults({
			highlight : function(element) {
				$(element).closest('.form-group').addClass('has-error');
			},
			unhighlight : function(element) {
				$(element).closest('.form-group').removeClass('has-error');
			},
			errorElement : 'span',
			errorClass : 'help-block',
			errorPlacement : function(error, element) {
				if (element.parent('.input-group').length) {
					error.insertAfter(element.parent());
				} else {
					error.insertAfter(element);
				}
			}
		});

		$("#frmAddAccount").validate({
			rules : {
				accountNumber : {
					required : true,
					digits : true,
					exactlength : 12
				},
				accountOwner : {
					required : true,
				},/* 
				balanceAmount : {
					required : true,
					number : true,
					minStrict : 1000000
				} */
			},
			messages : {
				accountNumber : {
					required : "Account Number is required",
					digits : "Please enter digits only",
					rangeLength : "Account Number must contain 12 digits"
				},
				accountOwner : {
					required : "Account Owner is required",

				}/* ,
				balanceAmount : {
					required : "Balance Amount is required",
					number : "Please enter a number",
					minStrict : "Balance Amount must be at least 1.000.000 VND"
				} */
			},

		})
	});
</script>
</html>