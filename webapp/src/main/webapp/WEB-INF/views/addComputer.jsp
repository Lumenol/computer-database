<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
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
<%@include file="header.jsp" %>
<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1><spring:message code="addComputer.title"/></h1>
                <c:if test="${(not empty success) && success}">
                    <div class="alert alert-success" role="alert">
                        <strong><spring:message code="form.success"/></strong> <spring:message
                            code="addComputer.success"/></div>
                </c:if>
                <c:if test="${(not empty success) && !success}">
                    <div class="alert alert-danger" role="alert">
                        <strong><spring:message code="form.faillure"/></strong> <spring:message
                            code="addComputer.faillure"/></div>
                </c:if>

                <form:form action="add" method="POST" modelAttribute="computer">
                    <fieldset>
                        <spring:bind path="name">
                            <div class='form-group ${status.error?"has-error":""}'>
                                <spring:message code="computer.name" var="name"/>
                                <form:label path="name">${name}</form:label>
                                <form:input type="text" class="form-control" path="name"
                                            id="computerName" placeholder="${name}" required="required"/>
                                <form:errors path="name" cssClass="help-block"/>
                            </div>
                        </spring:bind>
                        <spring:bind path="introduced">
                            <div class='form-group ${status.error?"has-error":""}'>
                                <spring:message code="computer.introduced" var="introduced"/>
                                <form:label path="introduced">${introduced}</form:label>
                                <form:input type="date" class="form-control" path="introduced"
                                            id="introduced" placeholder="${introduced}" min="1970-01-01"
                                            max="2038-01-19"/>
                                <form:errors path="introduced" cssClass="help-block"/>
                            </div>
                        </spring:bind>
                        <spring:bind path="discontinued">
                            <div class='form-group ${status.error?"has-error":""}'>
                                <spring:message code="computer.discontinued" var="discontinued"/>
                                <form:label path="discontinued">${discontinued}</form:label>
                                <form:input type="date" class="form-control"
                                            path="discontinued" id="discontinued"
                                            placeholder="${discontinued}" min="1970-01-01"
                                            max="2038-01-19"/>
                                <form:errors path="discontinued" cssClass="help-block"/>
                            </div>
                        </spring:bind>
                        <spring:bind path="manufacturerId">
                            <div class='form-group ${status.error?"has-error":""}'>
                                <form:label path="manufacturerId"><spring:message
                                        code="computer.manufacturer"/></form:label>
                                <form:select class="form-control" path="manufacturerId"
                                             id="manufacturerId">
                                    <form:option value="">--</form:option>
                                    <form:options items="${companies}" itemLabel="name"
                                                  itemValue="id"/>
                                </form:select>
                                <form:errors path="manufacturerId" cssClass="help-block"/>
                            </div>
                        </spring:bind>
                    </fieldset>
                    <div class="actions pull-right">
                        <spring:message code="form.add" var="addButton"/>
                        <input type="submit" value="${addButton}" class="btn btn-primary">
                        <spring:message code="or"/>
                        <c:url var="dashboard" value="/dashboard"/>
                        <a href="${dashboard}" class="btn btn-default"><spring:message code="form.cancel"/></a>
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
