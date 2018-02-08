<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/29/18
  Time: 4:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                        <h3 class="box-title">Order Return List</h3>
                        <%--<div class="box-tools">
                            <a href="${pageContext.request.contextPath}/order/sale/add" class="btn btn-info btn-sm btn-flat pull-right"><span class="glyphicon glyphicon-plus-sign"></span> New Order
                            </a>
                        </div>--%>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>S.N.</th>
                                    <th>OrderNo</th>
                                    <th>Return Date</th>
                                    <th>Client</th>
                                    <th>Total Cost</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${orderReturnList}" var="orderReturn" varStatus="i">

                                    <tr>
                                        <td>${i.index + 1}</td>
                                        <td><a href="${pageContext.request.contextPath}/orderreturn/show/${orderReturn.orderReturnId}">${orderReturn.orderInfoOrderNo}</a></td>
                                        <td><fmt:formatDate pattern="MMM dd, yyyy" value="${orderReturn.returnDate}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${orderReturn.clientInfoCompanyName ne null and not empty orderReturn.clientInfoCompanyName}">
                                                    ${orderReturn.clientInfoCompanyName}
                                                </c:when>
                                                <c:otherwise>
                                                    ${orderReturn.clientInfoName}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderReturn.totalAmount}"/></td>
                                    </tr>

                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>

                    <c:if test="${fn:length(pagelist) gt 1}">

                        <div class="col-xs-12">
                            <nav class="pull-right">
                                <ul class="pagination">

                                    <c:if test="${currentpage > 1}">
                                        <li class="page-item">
                                            <a href="${pageContext.request.contextPath}/orderreturn/list?pageNo=${currentpage-1}" class="page-link">Prev</a>
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
                                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/orderreturn/list?pageNo=${pagelist}">${pagelist}</a></li>
                                            </c:otherwise>

                                        </c:choose>
                                    </c:forEach>

                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <li class="page-item">
                                            <a class="page-link" href="${pageContext.request.contextPath}/orderreturn/list?pageNo=${currentpage+1}">Next</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>

                    </c:if>

                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="/pages/parts/footer.jsp" %>

