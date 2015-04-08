<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
				<h1>Customer List</h1>
				
				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>
					<li class="active"><i class="fa fa-file"></i> Customer List</li>
				</ol>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">

				<table class="table table-striped" id="tableCustomer">
					<thead>
						<tr>
							<th>ID</th>
							<th>Full Name</th>
							<th>ID Card Number</th>
							<th>Addresses</th>
							<th>Phone</th>
							<th>Email</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="customer" items="${listCustomer }">
							<tr>
								<td>${customer.customerId }</td>
								<td>${customer.lastName }${customer.middleName }
									${customer.firstName }</td>
								<td>${customer.idCardNumber }</td>
								<td>${customer.address1 }
									<p>${customer.address2 }</p>
								</td>
								<td>${customer.phone1}
									<p>${customer.phone2}</p>
								</td>
								<td>${customer.email}</td>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<a href="toAddAccount">
					<button type="button" class="btn btn-primary btn-sm">Add Customer</button>
				</a>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tableCustomer').dataTable({
			"bFilter" : false
		});
	});
</script>
</html>