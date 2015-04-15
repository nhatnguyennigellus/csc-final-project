<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#repeatable").val("${account.repeatable}");
		$("#state").val("${account.state}");
		
		$("#customerForm").submit(function(){
			if($("#customerFirstName").val() == "" || $("#customerLastName").val() == "" || $("#address1").val() == "" || $("#address2").val() == "" || $("#phone1").val() == "" || $("#phone2").val() == "" || $("#email").val() == "" || $("#idCard").val() == ""){
				alert("Please input valid value for all customer fields!")
				return false;
			}
			
			return true;
		});
		
		$("#accountForm").submit(function(){
			if($("#balanceAmount").val() == "" || $("#accountOwner").val() == "" || $("#interest").val() == ""){
				alert("Please input valid value for all account fields!")
				return false;
			}
			
			return true;
		});
	});

	
	
	function validate(name) {
		var control = $("#" + name);
		var checkSpan = $("#check" + control.attr("id"));
		checkSpan.text("");

		if (control.val() == "" && control.attr("id") != "customerMiddleName") {
			checkSpan.removeClass("label label-success");
			checkSpan.addClass("label label-danger");
			checkSpan.text("Value Require");
		} else if(control.val() != "") {
			checkSpan.removeClass("label label-danger");
			checkSpan.addClass("label label-success");
			checkSpan.text("Valid");
		}
	}
</script>
</head>
<body>
	<div id="customerDiv" class="panel panel-primary" style="width: 100%">
	<div class="panel-heading">Customer</div>
	<div class="panel-body">
		<form id="customerForm" action="updateCustomer" method="post">
			Customer ID: <input class="form-control" style="width: 10%; display: inline; margin-right: 2%;" type="text" id="customerID" name="customerID" value="${customer.customerId }" readonly="readonly"/><br>
			First Name: <input class="form-control" style="width: 15%; display: inline; margin-right: 2%; margin-top: 5px; margin-left: 2%;" type="text" id="customerFirstName" name="customerFirstName" value="${customer.firstName }" onblur="validate('customerFirstName')" /><span id="checkcustomerFirstName" style="width: 100px; height: 34px;"></span>
			Middle Name: <input class="form-control" style="width: 15%; display: inline; margin-right: 2%; margin-left: 2%;" type="text" id="customerMiddleName" name="customerMiddleName" value="${customer.middleName }" onblur="validate('customerMiddleName')" /><span id="checkcustomerMiddleName" style="width: 100px; height: 34px;"></span>
			Last Name: <input class="form-control" style="width: 15%; display: inline; margin-right: 2%; margin-left: 2%;" type="text" id="customerLastName" name="customerLastName" value="${customer.lastName }" onblur="validate('customerLastName')" /><span id="checkcustomerLastName" style="width: 100px; height: 34px;"></span><br>
			Address 1: <input class="form-control" style="width: 30%; display: inline; margin-right: 2%; margin-top: 5px; margin-left: 2%;" type="text" id="address1" name="address1" value="${customer.address1 }" onblur="validate('address1')" /><span id="checkaddress1" style="width: 100px; height: 34px;"></span>
			Address 2: <input class="form-control" style="width: 30%; display: inline; margin-right: 2%; margin-left: 2%;" type="text" id="address2" name="address2" value="${customer.address2 }" onblur="validate('address2')" /><span id="checkaddress2" style="width: 100px; height: 34px;"></span><br>
			Phone 1: <input class="form-control" style="width: 15%; display: inline; margin-right: 2%; margin-top: 5px; margin-left: 2%;" type="text" id="phone1" name="phone1" value="${customer.phone1 }" onblur="validate('phone1')" /><span id="checkphone1" style="width: 100px; height: 34px;"></span>
			Phone 2: <input class="form-control" style="width: 15%; display: inline; margin-right: 2%; margin-left: 2%;" type="text" id="phone2" name="phone2" value="${customer.phone2 }" onblur="validate('phone2')" /><span id="checkphone2" style="width: 100px; height: 34px;"></span><br>
			Email: <input class="form-control" style="width: 15%; display: inline; margin-right: 2%; margin-top: 5px; margin-left: 2%;" type="text" id="email" name="email" value="${customer.email }" onblur="validate('email')" /><span id="checkemail" style="width: 100px; height: 34px;"></span>
			ID Card Number: <input class="form-control" style="width: 15%; display: inline; margin-right: 2%; margin-left: 2%;" type="text" id="idCard" name="idCard" value="${customer.idCardNumber }" onblur="validate('idCard')" /><span id="checkidCard" style="width: 100px; height: 34px;"></span><br>
			<input name="currentAccount" style="display: none;" value="${account.accountNumber}">
			<input class="btn btn-primary" style="margin-top: 20px;" type="submit" value="Save Changes">
		</form>
		</div>
	</div>
	<div class="panel panel-primary" style="margin-top: 30px;">
	<div class="panel-heading">Saving Account</div>
	<div class="panel-body">
		<form id="accountForm" action="updateAccount" method="post">
			<div class="form-group">
				<div style="width: 50%; float: left;">
					Account Number<br> <input class="form-control"
						style="width: 30%;" type="text" id="accountNumber"
						name="accountNumber" value="${account.accountNumber }"
						readonly="readonly" /> Balance Amount<br> <input
						class="form-control" style="width: 30%; display: inline;"
						type="text" id="balanceAmount" name="balanceAmount"
						value="${account.balanceAmount }"
						onblur="validate('balanceAmount')" /><span
						id="checkbalanceAmount"></span><br> Start Date<br> <input
						class="form-control" style="width: 40%; display: inline;"
						type="text" id="startDate" name="startDate"
						value="${account.startDate }" readonly="readonly"/><br> Repeatable<br> <select
						id="repeatable" name="repeatable" class="form-control"
						style="width: 20%;">
						<option value="true">True</option>
						<option value="false">False</option>
					</select> Customer ID<br> <input class="form-control"
						style="width: 10%; display: inline;" type="text" id="customerId"
						name="customerId" value="${account.customer.customerId }" readonly="readonly"/><br>
				</div>
				<div style="width: 50%; float: left;">
					Account Owner<br> <input class="form-control"
						style="width: 50%; display: inline;" type="text" id="accountOwner"
						name="accountOwner" value="${account.accountOwner }"
						onblur="validate('accountOwner')" /><span id="checkaccountOwner"></span><br>
					Interest<br> <input class="form-control"
						style="width: 10%; display: inline;" type="text" id="interest"
						name="interest" value="${account.interest }"
						onblur="validate('interest')" /><span id="checkinterest"></span><br>
					Due Date<br> <input class="form-control"
						style="width: 40%; display: inline;" type="text" id="dueDate"
						name="dueDate" value="${account.dueDate }" readonly="readonly"/><br> State<br>
					<select id="state" name="state" class="form-control"
						style="width: 20%;">
						<option value="active">Active</option>
						<option value="hold">Hold</option>
						<option value="done">Done</option>
					</select> Interest ID<br> <input class="form-control"
						style="width: 10%; display: inline;" type="text" id="interestId"
						name="interestId" value="${account.interestRate.id }" readonly="readonly"/><br>
					<br>
				</div>
				<input class="btn btn-primary" type="submit" value="Save Changes">
				${notify }
			</div>
		</form>
		</div>
	</div>
</body>
</html>