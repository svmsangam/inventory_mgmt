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

                        <div class="col-md-4"><h3 class="box-title">Customer List</h3></div>

                        <div class="col-md-4">

                            <!-- search form (Optional) -->
                            <form action="#" method="GET">
                                <div class="input-group">
                                    <input type="text" name="q" class="form-control" placeholder="Search...">
                                    <span class="input-group-btn">
                                        <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
                                    </span>
                                </div>
                            </form>
                            <!-- /.search form -->

                        </div>

                        <div class="col-md-4">

                            <div class="box-tools">
                                <a href="${pageContext.request.contextPath}/customer/add"
                                   class="btn btn-info btn-sm btn-flat pull-right"><span
                                        class="glyphicon glyphicon-plus-sign"></span> Add
                                </a>
                            </div>

                        </div>

                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="table-responsive">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                            <tr>
                                <th>SN</th>
                                <th>AccountNo</th>
                                <th>Name</th>
                                <th>Company</th>
                                <th>Contact</th>
                                <th>Mobile</th>
                                <th>Email</th>
                                <th>City</th>
                                <th>Street</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="customer" items="${customerList}" varStatus="i">
                                <tr>
                                    <td>${i.index + 1}</td>
                                    <td>${customer.accountNo}</td>
                                    <td>${customer.name}</td>
                                    <td>${customer.companyName}</td>
                                    <td>${customer.contact}</td>
                                    <td>${customer.mobileNumber}</td>
                                    <td>${customer.email}</td>
                                    <td>${customer.cityInfoDTO.cityName}</td>
                                    <td>${customer.street}</td>
                                    <td>
                                        <a class="btn btn-success" href="${pageContext.request.contextPath}/customer/incoice?clientId=${customer.clientId}"><i class="fa fa-calendar-check-o"></i>&nbsp;invoice</a>
                                        <a class="btn btn-primary" href="#"><i class="fa fa-line-chart"></i>&nbsp;order</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </div>
                    </div>
                    <!-- /.box-body -->
                    <c:if test="${fn:length(pagelist) gt 1}">

                        <div class="col-xs-12">
                            <nav class="pull-right">
                                <ul class="pagination">

                                    <c:if test="${currentpage > 1}">
                                        <li class="page-item">

                                            <a href="${pageContext.request.contextPath}/customer/list?pageNo=${currentpage-1}"
                                               class="page-link">Prev</a>
                                        </li>
                                    </c:if>

                                    <c:forEach var="pagelist" items="${pagelist}">
                                        <c:choose>
                                            <c:when test="${pagelist == currentpage}">

                                                <li class="page-item active">
                                                  <span class="page-link">
                                                    ${pagelist}
                                                    <span class="sr-only">(current)</span>
                                                  </span>
                                                </li>

                                            </c:when>
                                            <c:otherwise>

                                                <li class="page-item"><a class="page-link"
                                                                         href="${pageContext.request.contextPath}/customer/list?pageNo=${pagelist}">${pagelist}</a>
                                                </li>

                                            </c:otherwise>

                                        </c:choose>
                                    </c:forEach>

                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/customer/list?pageNo=${currentpage+1}">Next</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>

                    </c:if>
                </div>
                <!-- /.box -->
            </div>
        </div>
    </section>


</div>

<%@include file="/pages/parts/footer.jsp" %>
