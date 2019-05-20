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
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<c:url var="dashboard" value="/" />
			<a class="navbar-brand" href="${dashboard}"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<c:if test="${(not empty success) && success}">
						<div class="alert alert-success" role="alert">
							<strong>Succès</strong> L'ordinateur est crée.
						</div>
					</c:if>
					<c:if test="${(not empty success) && !success}">
						<div class="alert alert-danger" role="alert">
							<strong>Echec</strong> L'ordinateur n'est pas crée.
						</div>
					</c:if>
					
					<form:form action="add" method="POST" modelAttribute="computer">
						<fieldset>
							<spring:bind path="name">
								<div class='form-group ${status.error?"has-error":""}'>
									<form:label path="name">Computer name</form:label>
									<form:input type="text" class="form-control" path="name"
										id="computerName" placeholder="Computer name" required="required"/>
									<form:errors path="name" cssClass="help-block" />
								</div>
							</spring:bind>
							<spring:bind path="introduced">
								<div class='form-group ${status.error?"has-error":""}'>
									<form:label path="introduced">Introduced date</form:label>
									<form:input type="date" class="form-control" path="introduced"
										id="introduced" placeholder="Introduced date" min="1970-01-01"
										max="2038-01-19" />
									<form:errors path="introduced" cssClass="help-block" />
								</div>
							</spring:bind>
							<spring:bind path="discontinued">
								<div class='form-group'>
									<form:label path="discontinued">Discontinued date</form:label>
									<form:input type="date" class="form-control"
										path="discontinued" id="discontinued"
										placeholder="Discontinued date" min="1970-01-01"
										max="2038-01-19" />
									<form:errors path="discontinued" cssClass="help-block" />
								</div>
							</spring:bind>
							<spring:bind path="mannufacturerId">
								<div class='form-group'>
									<form:label path="mannufacturerId">Company</form:label>
									<form:select class="form-control" path="mannufacturerId"
										id="mannufacturerId">
										<form:option value="">--</form:option>
										<form:options items="${companies}" itemLabel="name"
											itemValue="id" />
									</form:select>
									<form:errors path="mannufacturerId" cssClass="help-block" />
								</div>
							</spring:bind>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or
							<c:url var="dashboard" value="/dashboard" />
							<a href="${dashboard}" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script
		src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/computer.js"></script>
</body>
</html>
