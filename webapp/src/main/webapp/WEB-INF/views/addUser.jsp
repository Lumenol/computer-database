<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath}/static/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/static/css/main.css"
	rel="stylesheet" media="screen">
</head>
<body>
	<%@include file="header.jsp"%>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="addUser.title" />
					</h1>
					<c:if test="${(not empty success) && success}">
						<div class="alert alert-success" role="alert">
							<strong><spring:message code="form.success" /></strong>
							<spring:message code="addUser.success" />
						</div>
					</c:if>
					<c:if test="${(not empty success) && !success}">
						<div class="alert alert-danger" role="alert">
							<strong><spring:message code="form.faillure" /></strong>
							<spring:message code="addUser.faillure" />
						</div>
					</c:if>

					<c:url value="/users" var="users" />
					<form:form action="${users}" method="POST" modelAttribute="user">
						<fieldset>
							<spring:bind path="login">
								<div class='form-group ${status.error?"has-error":""}'>
									<spring:message code="user.login" var="login" />
									<form:label path="login">${login}</form:label>
									<form:input type="text" class="form-control" path="login"
										id="login" placeholder="${login}" required="required" />
									<form:errors path="login" cssClass="help-block" />
								</div>
							</spring:bind>
							<spring:bind path="password">
								<div class='form-group ${status.error?"has-error":""}'>
									<spring:message code="user.password" var="password" />
									<form:label path="password">${password}</form:label>
									<form:input type="text" class="form-control" path="password"
										id="password" placeholder="${password}" required="required" />
									<form:errors path="password" cssClass="help-block" />
								</div>
							</spring:bind>
							<spring:bind path="passwordCheck">
								<div class='form-group ${status.error?"has-error":""}'>
									<spring:message code="user.passwordCheck" var="passwordCheck" />
									<form:label path="passwordCheck">${passwordCheck}</form:label>
									<form:input type="text" class="form-control" path="passwordCheck"
										id="passwordCheck" placeholder="${passwordCheck}" required="required" />
									<form:errors path="passwordCheck" cssClass="help-block" />
								</div>
							</spring:bind>
						</fieldset>
						<div class="actions pull-right">
							<spring:message code="form.add" var="addButton" />
							<input type="submit" value="${addButton}" class="btn btn-primary">
							or
							<c:url var="dashboard" value="/dashboard" />
							<a href="${dashboard}" class="btn btn-default"><spring:message
									code="form.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script
		src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
</body>
</html>
