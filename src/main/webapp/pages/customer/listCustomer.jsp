<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Customers</li>
        </ol>
    </div><!--/.row-->
    <div class="col-lg-4">
        <c:if test="${not empty message}">
            <h3 class="bg-success"><c:out value="${message}"></c:out></h3>
        </c:if>
    </div>

    <div class="container-wrapper">
        <a href="${pageContext.request.contextPath}/app/addcustomer">
            <button class="btn btn-primary btn-sm pull-right">Add New Customer</button>
        </a>

        <table class="table table-responsive table-striped table-bordered">
            <thead class="thead-inverse">
            <tr>
                <th>First Name</th>
                <th>Middle Name</th>
                <th>Last Name</th>
                <th>Unique Name</th>
                <th>Landline</th>
                <th>MobileNo</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="clientInfo" items="${customerList}">
                <tr>

                    <td>${clientInfo.firstName}</td>
                    <td>${clientInfo.middleName}</td>
                    <td>${clientInfo.lastName}</td>
                    <td>${clientInfo.uniqueName}</td>
                    <td>${clientInfo.landline}</td>
                    <td>${clientInfo.mobileNo}</td>

                    <td>
                        <a href="${pageContext.request.contextPath}/app/editcustomer?customerId=${clientInfo.customerId}"
                           class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                            Edit </a> <a
                            href="${pageContext.request.contextPath}/app/deletecustomer?customerId=${clientInfo.customerId}"
                            class="btn btn-danger btn-xs" onclick="return confirm('Are you sure, you want to DELETE?')">
                        Delete </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%-- </c:when> --%>
        <%-- </c:choose> --%>
    </div>
</div>