<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <c:url var="dashboard" value="/"/>
        <a class="navbar-brand" href="${dashboard}"> Application - Computer Database </a>
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
                <form action="add" method="POST">
                    <fieldset>
                        <div class='form-group ${not empty errors["name"]?"has-error":""}'>
                            <label for="computerName">Computer name</label>
                            <input type="text" class="form-control" name="name" id="computerName"
                                   placeholder="Computer name" value="${computer.name}" required>
                            <c:if test="${not empty errors['name']}">
                                <div class="help-block">${errors['name']}</div>
                            </c:if>
                        </div>
                        <div class='form-group ${not empty errors["introduced"]?"has-error":""}'>
                            <label for="introduced">Introduced date</label>
                            <input type="date" class="form-control" name="introduced" id="introduced"
                                   placeholder="Introduced date" value="${computer.introduced}" min="1970-01-01">
                            <c:if test="${not empty errors['introduced']}">
                                <div class="help-block">${errors['introduced']}</div>
                            </c:if>
                        </div>
                        <div class='form-group ${not empty errors["discontinued"]?"has-error":""}'>
                            <label for="discontinued">Discontinued date</label>
                            <input type="date" class="form-control" name="discontinued" id="discontinued"
                                   placeholder="Discontinued date" value="${computer.discontinued}" min="1970-01-01">
                            <c:if test="${not empty errors['discontinued']}">
                                <div class="help-block">${errors['discontinued']}</div>
                            </c:if>
                        </div>
                        <div class='form-group ${not empty errors["mannufacturerId"]?"has-error":""}'>
                            <label for="mannufacturerID">Company</label>
                            <select class="form-control" name="mannufacturerId" id="mannufacturerId">
                                <option value="">--</option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.id}"
                                        ${computer.mannufacturerId==company.id?"selected='selected'":""}>${company.name}</option>
                                </c:forEach>
                            </select>
                            <c:if test="${not empty errors['mannufacturerId']}">
                                <div class="help-block">${errors['mannufacturerId']}</div>
                            </c:if>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Add" class="btn btn-primary">
                        or
                        <c:url var="dashboard" value="/dashboard"/>
                        <a href="${dashboard}" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/computer.js"></script>
</body>
</html>
