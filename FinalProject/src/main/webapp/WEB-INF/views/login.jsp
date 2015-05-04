<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

</head>
<body onload="document.getElementById('username').focus();">

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
					action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />"
					method='POST'>

					<div class="form-group">
						<label for="username">Username</label>
						<div class="input-group ">
							<span class="input-group-addon"> <span
								class=" glyphicon glyphicon-user"></span>
							</span> <input class="form-control" type="text" name="username"
								id="username" placeholder="Username">
						</div>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<div class="input-group ">
							<span class="input-group-addon"> <span
								class=" glyphicon glyphicon-lock"></span>
							</span><input class="form-control" type="password" name="password"
								placeholder="Password" />
						</div>
					</div>
					<div class="checkbox">
						<label> <input type="checkbox" value="yes"
							name="remember-me" /> Remember me
						</label>
					</div>
					<button class="btn btn-success" type="submit" value="Login">
						<span class="glyphicon glyphicon-log-in">&nbsp;</span> Login
					</button>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

				</form>
			</div>
		</div>
	</div>
</body>
</html>