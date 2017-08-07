<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="/pages/common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Service</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">Services</div>
                <div class="panel-body">
                    <a href="${pageContext.request.contextPath}/app/addservice">
                        <button class="btn btn-primary btn-sm pull-right">Add New Service</button>
                    </a>
                    <table class="table table-responsive table-striped table-bordered">
                        <thead class="thead-inverse">
                        <tr>
                            <th>serviceName</th>
                            <th>serviceType</th>
                            <th>servicePrice</th>
                            <th>description</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="service" oldItems="${serviceList}">
                            <tr>
                                <td>${service.serviceName}</td>
                                <td>${service.serviceType}</td>
                                <td>${service.servicePrice}</td>
                                <td>${service.description}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/app/editservice?serviceId=${service.serviceId}"
                                       class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                                        Edit </a> <a
                                        href="${pageContext.request.contextPath}/app/deleteservice?serviceId=${service.serviceId}"
                                        class="btn btn-danger btn-xs"
                                        onclick="return confirm('Are you sure, you want to DELETE?')"> Delete </a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>