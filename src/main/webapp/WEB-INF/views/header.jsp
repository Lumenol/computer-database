<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<c:url var="dashboard" value="/" />
		<a class="navbar-brand" href="${dashboard}"><spring:message code="header.title"/></a>
	</div>
</header>