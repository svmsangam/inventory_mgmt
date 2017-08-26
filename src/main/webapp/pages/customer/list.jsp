<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

    <section class="content">
        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${message}</strong>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${error}</strong>
            </div>
        </c:if>

        <div class="row">
            <div class="col-xs-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Customer List</h3>
                        <div class="box-tools">
                            <a href="${pageContext.request.contextPath}/customer/add" class="btn btn-info btn-sm btn-flat pull-right"><span class="glyphicon glyphicon-plus-sign"></span> Add
                            </a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                            <tr>
                                <th>SN</th>
                                <th>Name</th>
                                <th>Contact</th>
                                <th>mobile</th>
                                <th>email</th>
                                <th>city</th>
                                <th>street</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="customer" items="${customerList}" varStatus="i">
                                <tr>
                                    <td>${i.index + 1}</td>
                                    <td>${customer.name}</td>
                                    <td>${customer.contact}</td>
                                    <td>${customer.mobileNumber}</td>
                                    <td>${customer.email}</td>
                                    <td>${customer.cityInfoDTO.cityName}</td>
                                    <td>${customer.street}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->

                </div>
                <!-- /.box -->
                <c:if test="${fn:length(pagelist) gt 1}">

                    <div class="col-md-10  pull-left">
                        <ul	class="pagination no-margin col-md-10">
                            <li>
                                <c:if test="${currentpage > 1}">
                                    <a href="${pageContext.request.contextPath}/customer/list?pageNo=${currentpage-1}" class="btn btn-info btn-sm btn-flat">Prev</a>
                                </c:if></li>

                            <c:forEach   var="pagelist" items="${pagelist}" >
                                <li> <c:choose>

                                    <c:when test="${pagelist == currentpage}">

                                        <span class="active" class="btn btn-info btn-sm btn-flat">${pagelist}</span>

                                    </c:when>
                                    <c:otherwise>

                                        <a href="${pageContext.request.contextPath}/customer/list?pageNo=${pagelist}" class="btn btn-info btn-sm btn-flat">${pagelist}</a>

                                    </c:otherwise>

                                </c:choose> </li>
                            </c:forEach>
                            <li>
                                <c:if test="${currentpage + 1 <= lastpage}">
                                    <a href="${pageContext.request.contextPath}/customer/list?pageNo=${currentpage+1}" class="btn btn-info btn-sm btn-flat">Next</a>
                                </c:if></li>
                        </ul>
                    </div>

                </c:if>
            </div>
        </div>
    </section>


</div>

<%@include file="/pages/parts/footer.jsp" %>
