<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid">
		<h1>Dashboard</h1>
		<div class="row">
			<ol class="breadcrumb">
				<li><i class="glyphicon glyphicon-home"></i> Dashboard</li>
			</ol>
			<div class="col-lg-3">
				<div class="panel panel-red">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="glyphicon glyphicon-user fa-4x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${CustomerNo }</div>
								<div>Customer</div>
							</div>
						</div>
					</div>

					<div class="panel-body">
						<a href="viewCustomer"> <span class="pull-left">View
								Customer</span> <span class="pull-right"> <i
								class="glyphicon glyphicon-circle-arrow-right"></i>
						</span></a>
					</div>
					<sec:authorize access="hasRole('Support')">
						<div class="panel-body">
							<a href="toAddCustomer"> <span class="pull-left">Add
									Customer</span> <span class="pull-right"> <i
									class="glyphicon glyphicon-circle-arrow-right"></i>
							</span></a>
						</div>
					</sec:authorize>
				</div>
			</div>

			<div class="col-lg-3">
				<div class="panel panel-green">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="glyphicon glyphicon-book fa-4x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${AccountNo }</div>
								<div>Account</div>
							</div>
						</div>
					</div>

					<div class="panel-body">
						<a href="searchAccount"> <span class="pull-left">View
								Accounts</span> <span class="pull-right"> <i
								class="glyphicon glyphicon-circle-arrow-right"></i>
						</span></a>
					</div>

				</div>
			</div>

			<div class="col-lg-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<i class="glyphicon glyphicon-briefcase fa-4x"></i>
							</div>
							<div class="col-xs-9 text-right">
								<div class="huge">${TransactionNo }</div>
								<div>Transaction</div>
							</div>
						</div>
					</div>

					<div class="panel-body">
						<a href="searchTransaction"> <span class="pull-left">Search
								Transaction </span> <span class="pull-right"> <i
								class="glyphicon glyphicon-circle-arrow-right"></i>
						</span></a>
					</div>
					<sec:authorize access="hasRole('Support')">
						<div class="panel-body">
							<a href="accountTransaction"> <span class="pull-left">Perform
									Transaction </span> <span class="pull-right"> <i
									class="glyphicon glyphicon-circle-arrow-right"></i>
							</span></a>
						</div>
					</sec:authorize>
				</div>
			</div>
			<sec:authorize access="hasRole('Admin')">
				<div class="col-lg-3" >
					<div class="panel panel-yellow">
						<div class="panel-heading" style="height: 89px;">
							<div class="row">
								<div class="col-xs-3">
									<i class="glyphicon glyphicon-usd fa-4x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div><h4>INTEREST RATE</h4>
									</div>
								</div>
							</div>
						</div>

						<div class="panel-body">
							<a href="viewInterestRate"> <span class="pull-left">View
									Interest Rate</span> <span class="pull-right"> <i
									class="glyphicon glyphicon-circle-arrow-right"></i>
							</span></a>
						</div>

					</div>
				</div>
			</sec:authorize>
		</div>
	</div>
</body>
</html>