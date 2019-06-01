<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
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
        <h1 id="homeTitle">
            <spring:message code="dashboard.numberOfComputers"
                            arguments="${numberOfComputers}"/>
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" onsubmit="return goSearch()"
                      class="form-inline">
                    <spring:message code="dashboard.search.placeholder"
                                    var="searchPlaceholder"/>
                    <input type="search" id="searchbox" name="search"
                           class="form-control" placeholder="${searchPlaceholder}"/>

                    <spring:message code="dashboard.search.button"
                                    var="searchButton"/>
                    <input type="submit" id="searchsubmit" value="${searchButton}"
                           class="btn btn-primary"/>
                </form>
            </div>
            <div class="pull-right">
                <c:url var="addComputerUrl" value="/computers/add"/>
                <spring:message code="dashboard.add.button" var="addButton"/>
                <a class="btn btn-success" id="addComputer"
                   href="${addComputerUrl}">${addButton}</a>
                <spring:message code="dashboard.edit.button" var="editButton"/>
                <spring:message code="dashboard.view.button" var="viewButton"/>
                <a class="btn btn-default" id="editComputer" href="#"
                   onclick="$.fn.toggleEditMode('${editButton}','${viewButton}');">${editButton}</a>
            </div>
        </div>
    </div>
    <c:url var="dashboard" value="/dashboard"/>
    <form id="deleteForm" action="${dashboard}" method="POST">
        <input type="hidden" name=index value="${index}"/> <input
            type="hidden" name="size" value="${size}"/> <input type="hidden"
                                                               name="search" value="${search}"/> <input type="hidden"
                                                                                                        name="field"
                                                                                                        value="${order_by}"/>
        <input type="hidden"
               name="direction" value="${direction}"/>
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->
                <spring:message code="dashboard.delete.warning"
                                var="warningMessage"/>
                <th class="editMode" style="width: 60px; height: 22px;"><input
                        type="checkbox" id="selectall"/> <span
                        style="vertical-align: top;"> - <a href="#"
                                                           id="deleteSelected"
                                                           onclick='$.fn.deleteSelected("${warningMessage}");'> <i
                        class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
                <th><spring:message code="computer.name"/>
                    <c:set var="name" value="name"/> <c:set
                            var="direction" value="${order_utils.apply(name)}"/> <c:set
                            var="arrow"
                            value="${direction.equalsIgnoreCase('desc')?'down':'up'}"/>
                    <button class="float-right fa fa-arrow-${arrow}"
                            onclick="orderBy('${name}','${direction}')"></button>
                </th>
                <th><spring:message code="computer.introduced"/><c:set var="name" value="introduced"/>
                    <c:set var="direction" value="${order_utils.apply(name)}"/> <c:set
                            var="arrow"
                            value="${direction.equalsIgnoreCase('desc')?'down':'up'}"/>
                    <button class="float-right fa fa-arrow-${arrow}"
                            onclick="orderBy('${name}','${direction}')"></button>
                </th>
                <!-- Table header for Discontinued Date -->
                <th><spring:message code="computer.discontinued"/><c:set var="name" value="discontinued"/>
                    <c:set var="direction" value="${order_utils.apply(name)}"/> <c:set
                            var="arrow"
                            value="${direction.equalsIgnoreCase('desc')?'down':'up'}"/>
                    <button class="float-right fa fa-arrow-${arrow}"
                            onclick="orderBy('${name}','${direction}')"></button>
                </th>
                <!-- Table header for Company -->
                <th><spring:message code="computer.manufacturer"/><c:set var="name" value="company"/> <c:set
                        var="direction" value="${order_utils.apply(name)}"/> <c:set
                        var="arrow"
                        value="${direction.equalsIgnoreCase('desc')?'down':'up'}"/>
                    <button class="float-right fa fa-arrow-${arrow}"
                            onclick="orderBy('${name}','${direction}')"></button>
                </th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <c:forEach var="computer" items="${computers}">
                <c:url var="edit" value="/computers/edit/${computer.id}"/>
                <tr>
                    <td class="editMode"><input type="checkbox" name="cb"
                                                class="cb" value="${computer.id}"></td>
                    <td><a href="${edit}">${computer.name}</a></td>
                    <td>${computer.introduced}</td>
                    <td>${computer.discontinued}</td>
                    <td>${computer.manufacturer}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <ul class="pagination">
            <c:if test="${not empty previous}">
                <li><a aria-label="Previous" onclick="goPage(${previous})">
                    <span aria-hidden="true">&laquo;</span>
                </a></li>
            </c:if>

            <c:forEach var="page" items="${pages}">
                <li><a onclick="goPage(${page})">${page}</a></li>
            </c:forEach>

            <c:if test="${not empty next}">
                <li><a aria-label="Next" onclick="goPage(${next})"> <span
                        aria-hidden="true">&raquo;</span>
                </a></li>
            </c:if>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group">
            <button type="button" class="btn btn-default" onclick="goSize(10)">10
            </button>
            <button type="button" class="btn btn-default" onclick="goSize(50)">50
            </button>
            <button type="button" class="btn btn-default" onclick="goSize(100)">100
            </button>
        </div>
    </div>
</footer>
<script
        src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script
        src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>
<form id="parameters" hidden>
    <input name="index" value="${index}"> <input name="size"
                                                 value="${size}"> <input name="search" value="${search}">
    <input name="field" value="${order_by}"> <input
        name="direction" value="${direction}">
</form>
</body>
</html>