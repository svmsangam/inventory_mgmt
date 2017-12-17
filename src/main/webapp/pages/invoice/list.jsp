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
                        <h3 class="box-title">Invoice List</h3>
                       <%-- <div class="box-tools">
                            <a href="${pageContext.request.contextPath}/order/sale/add" class="btn btn-info btn-sm btn-flat pull-right"><span class="glyphicon glyphicon-plus-sign"></span> New Order
                            </a>
                        </div>--%>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <div class="row">
                            <div class="col-md-12">
                                <form action="${pageContext.request.contextPath}/invoice/filter" method="GET">
                                    <div class="well well-sm">

                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>From Date:</label>
                                                <div class='input-group date'>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="text" class="form-control datepicker"
                                                           onkeypress="return false;" onkeyup="return false;"
                                                           name="from" placeholder="From Date"/>
                                                </div>
                                                <p class="form-error"></p>
                                            </div>
                                        </div>

                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>To Date:</label>
                                                <div class='input-group date'>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="text" class="form-control datepicker"
                                                           onkeypress="return false;" onkeyup="return false;"
                                                           name="to" placeholder="To Date"/>
                                                </div>
                                                <p class="form-error"></p>
                                            </div>
                                        </div>
                                        <div class="margin" style="margin-top: 25px;">
                                            <button type="submit" class="btn btn-info btn-flat">Filter!</button>
                                        </div>

                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Invoice No</th>
                                            <th>Customer Name</th>
                                            <th>Total Amount</th>
                                            <th>Amount Recievable</th>
                                            <th>Invoice Date</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${invoiceList}" var="invoice">

                                            <tr>
                                                <td><a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a></td>
                                                <td>${invoice.orderInfo.clientInfo.name}</td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.totalAmount}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.receivableAmount}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy" value="${invoice.invoiceDate}"/></td>
                                            </tr>

                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>

                    <c:if test="${fn:length(pagelist) gt 1}">

                        <div class="col-xs-12">
                            <nav class="pull-right">
                                <ul class="pagination">

                                    <c:if test="${currentpage > 1}">
                                        <li class="page-item">

                                            <a href="${pageContext.request.contextPath}/invoice/list?pageNo=${currentpage-1}"
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
                                                                         href="${pageContext.request.contextPath}/invoice/list?pageNo=${pagelist}">${pagelist}</a>
                                                </li>

                                            </c:otherwise>

                                        </c:choose>
                                    </c:forEach>

                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/invoice/list?pageNo=${currentpage+1}">Next</a>
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
