<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/main.css" media="screen" rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<section id="main">
    <div class="container">
        <div class="alert alert-danger">
            Error 500: An error has occured!
            <br/>
            <!-- stacktrace -->
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>

</body>
</html>