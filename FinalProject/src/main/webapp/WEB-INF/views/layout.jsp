<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html5/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link rel="shortcut icon"
	href="<c:url value='/resources/images/logo.png'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/css/jquery-ui.css'/> ">
<link rel="stylesheet"
	href="<c:url value='/resources/css/bootstrap.css'/> ">
<link rel="stylesheet"
	href="<c:url value='/resources/css/jquery.dataTables.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/css/dataTables.bootstrap.css' />">

<link rel="stylesheet"
	href="<c:url value='/resources/css/sb-admin.css'/> ">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/font-awesome/css/font-awesome.min.css'/> ">



<script type="text/javascript"
	src="<c:url value='/resources/js/jquery.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-ui.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-1.10.2.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/bootstrap.js'/> "></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery.dataTables.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/dataTables.bootstrap.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery.validate.js' /> "></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/additional-methods.js' /> "></script>
</head>
<body>
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-ex1-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="home"> <img
				src='<c:url value="/resources/images/logo.png" />' height="50"
				alt="" />
			</a>
		</div>
		<!-- Top Menu Items --> <c:if test="${sessionScope.USER != null }">
			<ul class="nav navbar-right top-nav">

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i>
						${sessionScope.USER.username} <b class="caret"></b></a>
					<ul class="dropdown-menu">

						<li><a href="logout"><i class="fa fa-fw fa-power-off"></i>
								Log Out</a></li>
					</ul></li>
			</ul>
		</c:if> <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
		<c:if test="${sessionScope.USER != null }">
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li class="active"><a href="home"><i
							class="glyphicon glyphicon-home"></i> &nbsp;Dashboard</a></li>
					<li><a href="javascript:;" data-toggle="collapse"
						data-target="#customer"><i class="glyphicon glyphicon-user"></i>
							&nbsp;Customer <i class="glyphicon glyphicon-chevron-down"></i></a>
						<ul id="customer" class="collapse">
							<li><a href="viewCustomer"><i
									class="glyphicon glyphicon-th-list"></i> Customer List</a></li>
							<li><a href="toAddCustomer"><i
									class="glyphicon glyphicon-plus"></i> Add Customer</a></li>
						</ul></li>
					<li><a href="searchAccount"><i
							class="glyphicon glyphicon-search"></i> &nbsp;Account</a></li>
					<li><a href="viewInterestRate"><i
							class="glyphicon glyphicon-search"></i> &nbsp;Interest Rate</a></li>
					<li><a href="javascript:;" data-toggle="collapse"
						data-target="#trans"><i class="glyphicon glyphicon-briefcase"></i>
							&nbsp;Transaction <i class="glyphicon glyphicon-chevron-down"></i></a>
						<ul id="trans" class="collapse">
							<li><a href="listTransaction"><i
									class="glyphicon glyphicon-time"></i> Search Transaction</a></li>
							<li><a href="accountTransaction"><i
									class="glyphicon glyphicon-usd"></i> Perform Transaction</a></li>
						</ul></li>

				</ul>
			</div>
		</c:if> <!-- /.navbar-collapse --> </nav>

		<div id="page-wrapper">

			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">

						<tiles:insertAttribute name="body" />

					</div>

				</div>

			</div>
			<!-- /.container-fluid -->

		</div>
		<div class="col-md-12">
			<font color="white">CSC Java Fresher Mar 2015 - Final Project</font>
		</div>
		<!-- /#page-wrapper -->

	</div>
</body>
</html>