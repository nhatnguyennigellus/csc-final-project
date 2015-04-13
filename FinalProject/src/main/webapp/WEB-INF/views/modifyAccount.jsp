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
	});

	function validate(name) {
		var control = $("#" + name);
		if (control.val() == "") {
			if (control.attr("id") == "accountNumber") {
				$("#check" + control.attr("id")).html(
						"<font color='red'> NULL</font>");
			}
		} else {
			$("#check" + control.attr("id")).html(
					"<font color='green'> Valid</font>");
		}
	}
</script>
</head>
<body>
	<div id="customerDiv" style="border: 1px solid black; width: 100%">
		<form action="updateCustomer" method="post">
			Customer ID: <input class="form-control" style="width: 10%; display: inline; margin-right: 5%;" type="text" id="customerID" name="customerID" value="${customer.customerId }" readonly="readonly"/><br>
			First Name: <input class="form-control" style="width: 20%; display: inline; margin-right: 5%; margin-top: 5px;" type="text" id="customerFirstName" name="customerFirstName" value="${customer.firstName }" onblur="validate('customerFirstName')" /><span id="checkcustomerFirstName"></span>
			Middle Name: <input class="form-control" style="width: 20%; display: inline; margin-right: 5%;" type="text" id="customerMiddleName" name="customerMiddleName" value="${customer.middleName }" onblur="validate('customerMiddleName')" /><span id="checkcustomerMiddleName"></span>
			Last Name: <input class="form-control" style="width: 20%; display: inline; margin-right: 5%;" type="text" id="customerLastName" name="customerLastName" value="${customer.lastName }" onblur="validate('customerLastName')" /><span id="checkcustomerLastName"></span><br>
			Address 1: <input class="form-control" style="width: 30%; display: inline; margin-right: 5%; margin-top: 5px;" type="text" id="accountNumber" name="accountNumber" value="${account.accountNumber }" onblur="validate('accountNumber')" /><span id="checkaccountNumber"></span>
			Address 2: <input
				class="form-control"
				style="width: 30%; display: inline; margin-right: 5%;" type="text"
				id="accountNumber" name="accountNumber"
				value="${account.accountNumber }" onblur="validate('accountNumber')" /><span
				id="checkaccountNumber"></span><br>
				Phone 1: <input
				class="form-control"
				style="width: 15%; display: inline; margin-right: 5%; margin-top: 5px;" type="text"
				id="accountNumber" name="accountNumber"
				value="${account.accountNumber }" onblur="validate('accountNumber')" /><span
				id="checkaccountNumber"></span> Phone 2: <input
				class="form-control"
				style="width: 15%; display: inline; margin-right: 5%;" type="text"
				id="accountNumber" name="accountNumber"
				value="${account.accountNumber }" onblur="validate('accountNumber')" /><span
				id="checkaccountNumber"></span>
				Email: <input
				class="form-control"
				style="width: 15%; display: inline; margin-right: 5%; margin-top: 5px;" type="text"
				id="accountNumber" name="accountNumber"
				value="${account.accountNumber }" onblur="validate('accountNumber')" /><span
				id="checkaccountNumber"></span> ID Card Number: <input
				class="form-control"
				style="width: 15%; display: inline; margin-right: 5%;" type="text"
				id="accountNumber" name="accountNumber"
				value="${account.accountNumber }" onblur="validate('accountNumber')" /><span
				id="checkaccountNumber"></span>
		</form>
	</div>
	<div>
		<form action="updateAccount" method="post">
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
						class="form-control" style="width: 20%; display: inline;"
						type="text" id="startDate" name="startDate"
						value="${account.startDate }" /><br> Repeatable<br> <select
						id="repeatable" name="repeatable" class="form-control"
						style="width: 20%;">
						<option value="true">True</option>
						<option value="false">False</option>
					</select> Customer ID<br> <input class="form-control"
						style="width: 10%; display: inline;" type="text" id="customerId"
						name="customerId" value="${account.customer.customerId }" /><br>
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
						style="width: 20%; display: inline;" type="text" id="dueDate"
						name="dueDate" value="${account.dueDate }" /><br> State<br>
					<select id="state" name="state" class="form-control"
						style="width: 20%;">
						<option value="active">Active</option>
						<option value="hold">Hold</option>
						<option value="done">Done</option>
					</select> Interest ID<br> <input class="form-control"
						style="width: 10%; display: inline;" type="text" id="interestId"
						name="interestId" value="${account.interestRate.id }" /><br>
					<br>
				</div>
				</tr>
				</table>
				<input type="submit" value="Save Changes">
			</div>
		</form>
	</div>
</body>
</html>