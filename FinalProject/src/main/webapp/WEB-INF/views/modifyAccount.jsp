<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">
function validate(name){
	var control = $("#" + name);
	if(control.val() == ""){
		if(control.attr("id") == "accountNumber"){
			$("#check" + control.attr("id")).html("<font color='red'> NULL</font>");
		}
	}
	else{
		$("#check" + control.attr("id")).html("<font color='green'> Valid</font>");
	}
}
</script>
</head>
<body>
	<form>
		<div class="form-group">
		Account Number<br>
		<input class="form-control" style="width: 15%; display: inline;" type="text" id="accountNumber" name="accountNumber" value="${account.accountNumber }" onblur="validate('accountNumber')"/><span id="checkaccountNumber"></span><br>
		Account Owner<br>
		<input class="form-control" style="width: 25%; display: inline;" type="text" id="accountOwner" name="accountOwner" value="${account.accountOwner }" onblur="validate('accountOwner')"/><span id="checkaccountOwner"></span><br>
		Balance Amount<br>
		<input class="form-control" style="width: 15%; display: inline;" type="text" id="balanceAmount" name="balanceAmount" value="${account.balanceAmount }" onblur="validate('balanceAmount')"/><span id="checkbalanceAmount"></span><br>
		Interest<br>
		<input class="form-control" style="width: 5%; display: inline;" type="text" id="interest" name="interest" value="${account.interest }" onblur="validate('interest')"/><span id="checkinterest"></span><br>
		Start Date<br>
		<input class="form-control" style="width: 10%; display: inline;" type="text" id="startDate" name="startDate" value="${account.startDate }"/><br>
		Due Date<br>
		<input class="form-control" style="width: 10%; display: inline;" type="text" id="dueDate" name="dueDate" value="${account.dueDate }"/><br>
		Repeatable<br>
		<input class="form-control" style="width: 5%; display: inline;" type="text" id="repeatable" name="repeatable" value="${account.repeatable }"/><br>
		State<br>
		<input class="form-control" style="width: 5%; display: inline;" type="text" id="state" name="state" value="${account.state }"/><br>
		Customer ID<br>
		<input class="form-control" style="width: 5%; display: inline;" type="text" id="customerId" name="customerId" value="${account.customer.customerId }"/><br>
		Interest ID<br>
		<input class="form-control" style="width: 5%; display: inline;" type="text" id="interestId" name="interestId" value="${account.interestRate.id }"/><br>
		<br>
		<input type="submit" value="Save Changes">
		</div>
	</form>
</body>
</html>