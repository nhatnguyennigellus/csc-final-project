<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">
	.valueRequireIcon{
		background-image: url(./resources/images/valueRequireIcon.ico) right no-repeat;
		padding-right: 100px;
		background-size: 20px; 100px;
	}
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#repeatable").val("${account.repeatable}");
						$("#state").val("${account.state}");

						$("#customerForm")
								.submit(
										function() {
											if ($("#customerFirstName").val() == ""
													|| $("#customerLastName")
															.val() == ""
													|| $("#address1").val() == ""
													|| $("#phone1").val() == ""
													|| $("#email").val() == ""
													|| $("#idCard").val() == "") {
												alert("Please input valid value for all customer fields!")
												return false;
											}

											return true;
										});

						$("#accountForm")
								.submit(
										function() {
											if ($("#balanceAmount").val() == ""
													|| $("#accountOwner").val() == ""
													|| $("#interest").val() == "") {
												alert("Please input valid value for all account fields!")
												return false;
											}

											return true;
										});
					});

	var filterPrototype = /^[a-zA-Z0-9]+[a-zA-Z0-9_.-]+[a-zA-Z0-9_-]+@[a-zA-Z0-9]+[a-zA-Z0-9.-]+[a-zA-Z0-9]+.[a-z]{2,4}$/;

	function validate(name) {
		var control = $("#" + name);
		var checkSpan = $("#check" + control.attr("id")); 
		checkSpan.text("");
		checkSpan.removeClass();

		//Phone contrains(10 - 15 number)
		if (control.val() == ""
				&& (control.attr("id") != "customerMiddleName" && control.attr("id") != "customerPhone2" && control.attr("id") != "customerAddress2")) {
			if (control.attr("id") == "customerPhone1" || control.attr("id") == "customerPhone2") {
				if (control.val().length < 7 || control.val().length > 15) {
					checkSpan.addClass("label label-danger");
					checkSpan.text("Invalid");
				}
			} else{
				checkSpan.addClass("label label-danger");
				checkSpan.text("Value Require");
			}
		} else{
			checkSpan.addClass("label label-success");
			checkSpan.text("Valid");
		}
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h1>Modify Account</h1>

				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>
					<li class="active"><i class="glyphicon glyphicon-search"></i>
						<a href="searchAccount">Search Account</a></li>
					<li class="active"><i class="glyphicon glyphicon-edit"></i>
						Modify Account</li>
				</ol>
			</div>
		</div>
		<div class="row">

			<div class="col-lg-12">
				<c:if test="${updateSuccess != null }">
					<div class="alert alert-success" role="alert">${updateSuccess }
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
				<c:if test="${updateError != null }">
					<div class="alert alert-danger" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>${updateError }
					</div>
				</c:if>
				<div id="customerDiv" class="panel panel-primary">
					<div class="panel-heading">Customer</div>
					<div class="panel-body">
						<form id="customerForm" action="updateCustomer">
							
							<!-- ------------------------------------------------------------------------- -->
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Customer ID</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="customerID" class="form-control input-sm"
											type="text" name="customerID"
											value="${customer.customerId }" readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							
							
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Last name</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="customerLastName" class="form-control input-sm"
											type="text" name="customerLastName" onkeypress="return (event.charCode >= 65 && event.charCode <= 90) || (event.charCode >= 97 && event.charCode <= 122) || (event.charCode == 32)"
											value="${customer.lastName }"
											onblur="validate('customerLastName')"
										/>
										<div id="checkcustomerLastName" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="middleName">Middle
										name (optional) </label>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="customerMiddleName" class="form-control input-sm"
											type="text" name="customerMiddleName" onkeypress="return (event.charCode >= 65 && event.charCode <= 90) || (event.charCode >= 97 && event.charCode <= 122) || (event.charCode == 32)"
											value="${customer.middleName }"
											onblur="validate('customerMiddleName')"
										/>
										<div id="checkcustomerMiddleName" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="firstName">First name</label>
									<font color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="customerFirstName" class="form-control input-sm"
											type="text" name="customerFirstName" onkeypress="return (event.charCode >= 65 && event.charCode <= 90) || (event.charCode >= 97 && event.charCode <= 122) || (event.charCode == 32)"
											value="${customer.firstName }"
											onblur="validate('customerFirstName')"
										/>
										<div id="checkcustomerFirstName" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="idCardNumber">ID Card
										Number</label> <font color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-credit-card"></span>
										</span>
										<input id="customerIDCardNumber" class="form-control input-sm"
											type="text" name="customerIDCardNumber" onkeypress="return event.charCode >= 48 && event.charCode <= 57"
											value="${customer.idCardNumber }"
											onblur="validate('customerIDCardNumber')"
										/>
										<div id="checkcustomerIDCardNumber" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="email">Email</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-home"></span>
										</span>
										<input id="customerEmail" class="form-control input-sm"
											type="text" name="customerEmail"
											value="${customer.email }"
											onblur="validate('customerEmail')"
										/>
										<div id="checkcustomerEmail" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="address1">Address 1</label><font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-home"></span>
										</span>
										<input id="customerAddress1" class="form-control input-sm"
											type="text" name="customerAddress1"
											value="${customer.address1 }"
											onblur="validate('customerAddress1')"
										/>
										<div id="checkcustomerAddress1" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="address2">Address 2
										(optional)</label>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-home"></span>
										</span>
										<input id="customerAddress2" class="form-control input-sm"
											type="text" name="customerAddress2"
											value="${customer.address2 }"
											onblur="validate('customerAddress2')"
										/>
										<div id="checkcustomerAddress2" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="phone1">Phone 1</label><font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-phone"></span>
										</span>
										<input id="customerPhone1" class="form-control input-sm"
											type="text" name="customerPhone1"
											onkeypress="return event.charCode >= 48 && event.charCode <= 57"
											value="${customer.phone1 }"
											onblur="validate('customerPhone1')"
										/>
										<div id="checkcustomerPhone1" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="phone2">Phone 2
										(optional)</label>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-phone"></span>
										</span>
										<input id="customerPhone2" class="form-control input-sm" type="text"
											type="text" name="customerPhone2"
											onkeypress="return event.charCode >= 48 && event.charCode <= 57"
											value="${customer.phone2 }"
											onblur="validate('customerPhone2')"
										/>
										<div id="checkcustomerPhone2" style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							<input
								name="currentAccount" style="display: none;"
								value="${account.accountNumber}"/>
					<!-- -------------------------------------------------------------------------- -->
							<button type="submit" class="btn btn-primary"
								style="margin-top: 15px">
								<span class="glyphicon glyphicon-check">&nbsp;</span> Save
								changes
							</button>
						</form>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">Saving Account</div>
					<div class="panel-body">
						<form id="accountForm" action="updateAccount">

						<!-- ---------------------------------------------------------- -->
						
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Acocunt Number</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="accountNumber" class="form-control input-sm"
											type="text" name="accountNumber"
											value="${account.accountNumber }" readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Account Owner</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="accountOwner" class="form-control input-sm"
											type="text" name="accountOwner"
											value="${account.accountOwner }" readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							
							
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Balance Amount</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="balanceAmount" class="form-control input-sm"
											type="text" name="balanceAmount"
											value="${account.balanceAmount }"
											readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="middleName">Interest</label>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="interest" class="form-control input-sm"
											type="text" name="interest"
											value="${account.interest }"
											readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Start Date</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="startDate" class="form-control input-sm"
											type="text" name="startDate"
											value="${account.startDate }"
											readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="middleName">Due Date</label>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="dueDate" class="form-control input-sm"
											type="text" name="dueDate"
											value="${account.dueDate }"
											readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Repeatable</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<select id="repeatable" name="repeatable"
										class="form-control">
											<option value="true">True</option>
											<option value="false">False</option>
										</select>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="middleName">State</label>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<select id="state" name="state"
										class="form-control">
										<c:if test="${account.state == 'hold'}">
											<option value="active">Active</option>
										</c:if>
										<c:if test="${account.state != 'new'}">
											<option value="active">Active</option>
											<option value="hold">Hold</option>

										</c:if>
										<c:if
											test="${account.balanceAmount == 0 && account.interest == 0 || account.state == 'done'
											 }">
											<option value="done">Done</option>
										</c:if>
										<c:if test="${account.state == 'new'}">
											<option value="new">New</option>
										</c:if>
										</select>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="form-group col-md-4">
									<label class="control-label" for="lastName">Customer ID</label> <font
										color="red">* </font>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="customerId" class="form-control input-sm"
											type="text" name="customerId"
											value="${account.customer.customerId }"
											readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label" for="middleName">Interest ID</label>
									<div class="input-group ">
										<span class="input-group-addon"> <span
											class=" glyphicon glyphicon-user"></span>
										</span>
										<input id="interestId" class="form-control input-sm"
											type="text" name="interestId"
											value="${account.interestRate.id}"
											readonly="readonly"
										/>
										<div style="display: table-cell; width: 82px; height: 20px; vertical-align: middle;"></div>
									</div>
								</div>
							</div>
							
							<div style="width: 100%; float: left;">
									<button type="submit" class="btn btn-primary">
										<span class="glyphicon glyphicon-check">&nbsp;</span> Save
										changes
									</button>
							</div>
							<!-- ------------------------------------------------ -->
								
							</form>
							</div>
						
					</div>
				</div>
			</div>
		</div>
</body>
</html>