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
				<h1>Add Customer</h1>

				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>
					<li><i class="fa fa-file"></i> <a href="viewCustomer">
							Customer List</a></li>
					<li class="active"><i class="glyphicon glyphicon-plus"></i>
						Add Customer</li>
				</ol>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-body">
						<form:form action="addCustomer" method="post" role="form"
							modelAttribute="customer" id="frmAddCustomer">
							<c:if test="${addCusError != null }">

								<div class="alert alert-danger" role="alert">${addCusError }</div>
							</c:if>
							<c:if test="${addCusSuccess!= null }">

								<div class="alert alert-success" role="alert">${addCusSuccess }
									<strong><a href="viewCustomer" class="alert-link">Back
											to customer list</a></strong>
								</div>
							</c:if>
							<div class="form-group col-md-3">
								<label class="control-label" for="lastName">Last name</label> <font
									color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-user"></span>
									</span>
									<form:input id="lastName" class="form-control input-sm"
										type="text" name="lastName" path="lastName" />
								</div>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="middleName">Middle
									name (optional) </label>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-user"></span>
									</span>
									<form:input id="middleName" class="form-control input-sm"
										type="text" name="middleName" path="middleName" />
								</div>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="firstName">First name</label>
								<font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-user"></span>
									</span>
									<form:input id="firstName" class="form-control input-sm"
										type="text" name="firstName" path="firstName" />
								</div>
							</div>
							<div class="form-group col-md-2">
								<label class="control-label" for="idCardNumber">ID Card
									Number</label> <font color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-credit-card"></span>
									</span>
									<form:input id="idCardNumber" class="form-control input-sm"
										type="text" name="idCardNumber" path="idCardNumber" />
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="address1">Address 1</label> <font
									color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-home"></span>
									</span>
									<form:input id="address1" class="form-control input-sm"
										type="text" name="address1" path="address1" />
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="address2">Address 2
									(optional)</label>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-home"></span>
									</span>
									<form:input class="form-control input-sm" type="text"
										id="address2" name="address2" path="address2" />
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label" for="email">Email</label> <font
									color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-envelope"></span>
									</span>
									<form:input id="email" class="form-control input-sm"
										type="text" name="email" path="email" />
								</div>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="phone1">Phone 1</label><font
									color="red">* </font>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-phone"></span>
									</span>
									<form:input id="phone1" class="form-control input-sm"
										type="text" name="phone1" path="phone1" />
								</div>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" for="phone2">Phone 2
									(optional)</label>
								<div class="input-group ">
									<span class="input-group-addon"> <span
										class=" glyphicon glyphicon-phone"></span>
									</span>
									<form:input class="form-control input-sm" type="text"
										id="phone2" name="phone2" path="phone2" />
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
		$("#frmAddCustomer").validate({
			rules : {

				firstName : {
					required : true,
				},
				lastName : {
					required : true,
				},
				idCardNumber : {
					required : true,
					digits : true
				},
				address1 : {
					required : true,
				},
				email : {
					required : true,
					email : true
				},
				phone1 : {
					required : true,
					digits : true,
					rangelength : [ 10, 15 ]
				},
				phone2 : {
					digits : true,
					rangelength : [ 10, 15 ],
				}
			},
			messages : {
				firstName : {
					required : "First name is required!",
				},
				lastName : {
					required : "Last name is required!",
				},
				idCardNumber : {
					required : "ID Card Number is required!",
					digits : "Please enter digits only!"
				},
				address1 : {
					required : "Address 1 is required!",
				},
				email : {
					required : "Email is required!",
					email : "Please enter valid email address!"
				},
				phone1 : {
					required : "Phone 1 is required!",
					digits : "Please enter digits only!",
					rangelength : "Length must be between 10 and 15 digits",

				},
				phone2 : {
					digits : "Please enter digits only!",
					rangelength : "Length must be between 10 and 15 digits",
				}
			},
		})
	});
</script>
</html>