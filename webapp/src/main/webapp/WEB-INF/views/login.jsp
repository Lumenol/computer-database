<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"
	media="screen" rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/static/css/font-awesome.css"
	media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/main.css"
	media="screen" rel="stylesheet">
</head>
<body>
	<%@include file="header.jsp"%>
	<section id="main">
		<div class="container">
			<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
			<c:if test="${param.error!=null}">
					<div class="alert alert-danger" role="alert">
						<spring:message code="login.faillure" />
					</div>
				</c:if>
				<c:if test="${param.logout!=null}">
					<div class="alert alert-success" role="alert">
						<spring:message code="login.logout" />
					</div>
				</c:if>
				<c:url value="/login" var="login"/>
				<form action="${login}" method="POST">
					<fieldset>
						<div class='form-group'>
							<spring:message code="login.username" var="username" />
							<label for="username">${username}</label> <input type="text"
								class="form-control" id="username" name="username"
								required="required" />
						</div>
						<div class='form-group'>
							<spring:message code="login.password" var="password" />
							<label for="password">${username}</label> <input type="password"
								class="form-control" id="password" name="password"
								required="required" />
						</div>
					</fieldset>
					<div class="actions pull-right">
						<spring:message code="header.login" var="connection" />
						<input type="submit" name="submit" value="${connection}" class="btn btn-primary">
					</div>
				</form>
			</div>
			</div>
		</div>
	</section>

	<script
		src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>

</body>
</html>