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
				<h1>Perform Transaction</h1>

				<ol class="breadcrumb">
					<li><i class="glyphicon glyphicon-home"></i> <a href="home">Dashboard</a></li>

					<li class="active"><i class="fa fa-file"></i> Perform
						Transaction</li>
				</ol>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-info">
					<div class="panel-body">
						<form action="performTransaction" method="post">
							<c:if test="${accTransError != null }">
								<div class="alert alert-danger" role="alert">${accTransError }</div>
							</c:if>
							<div class="form-group col-md-6">
								<label class="control-label" for="accountNumber">Account
									Number</label> <font color="red">* <input id="accountNumber"
									class="form-control input-sm" type="text" name="accountNumber" />
								</font>
							</div>
							<div class="form-group col-md-8">
								<input type="submit" value="Submit" name="subRegister"
									class="btn btn-success" /> 
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>