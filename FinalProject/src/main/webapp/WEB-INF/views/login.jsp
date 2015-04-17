<%@page import="csc.fresher.finalproject.mycookies.SessionName"%>
<%@page import="csc.fresher.finalproject.mycookies.CookieUtilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

</head>
<body>

	<div class="col-md-4 col-md-offset-3">
		<h1>Login</h1>
		<div class="panel panel-info">

			<div class="panel-body">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="alert alert-success" role="alert">${msg}</div>
				</c:if>

				<form name='loginForm'
					action="<c:url value='/j_spring_security_check' />" method='POST'>

					<div class="form-group">
						<label for="username">Username</label> <font color="red"><input
							class="form-control" type="text" name="username"
							class="textBox"
							value="<%=CookieUtilities.getCookieValue(request,
					SessionName.USERNAME, "")%>" /></font>
					</div>
					<div class="form-group">
						<label for="password">Password</label> <font color="red"><input
							class="form-control" type="password" name="password"
							value="<%=CookieUtilities.getCookieValue(request,
					SessionName.PASSWORD, "")%>" /></font>
					</div>
					<div class="checkbox">
						<label> <input type="checkbox" value="yes"
							name="cbRemember" /> Remember me
						</label>
					</div>
					<input class="btn btn-success" type="submit" name="btLogin"
						value="Login" class="button" /> <input type="hidden"
						name="${_csrf.parameterName}" value="${_csrf.token}" />

				</form>
			</div>
		</div>
	</div>
</body>
</html>