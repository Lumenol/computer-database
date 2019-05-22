<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div style="float: left">
            <c:url var="dashboard" value="/"/>
            <a class="navbar-brand" href="${dashboard}"><spring:message
                    code="header.title"/></a>
        </div>
        <div style="float: right">
            <a class="navbar-brand" href="?lang=fr">Français</a> <a
                class="navbar-brand" href="?lang=en">English</a>
        </div>
    </div>
</header>