<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="float-left">
            <c:url var="dashboard" value="/"/>
            <a class="navbar-brand" href="${dashboard}"><spring:message
                    code="header.title"/></a>
        </div>
        <div class="float-right">
            <a class="navbar-brand" href="?lang=fr">Français</a>
            <a class="navbar-brand" href="?lang=en">English</a>
            <sec:authorize access="!isAuthenticated()">
                <c:url value="/login" var="login"/>
                <a class="navbar-brand" href="${login}"><spring:message code="header.login"/></a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <c:url value="/logout" var="logout"/>
                <a class="navbar-brand" href="${logout}"><spring:message code="header.logout"/></a>
            </sec:authorize>
        </div>
    </div>
</header>