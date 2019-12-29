<%--
  Created by IntelliJ IDEA.
  User: bidhee
  Date: 2/18/18
  Time: 11:17 AM
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
                        <h3 class="box-title">Order filter</h3>
                        <small style="color: #f47342;">total results : &nbsp;${totalResult}</small>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <div class="row" id="filter" >
                            <div class="col-md-12">
                                <%@include file="/pages/order/filterForm.jsp" %>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Order No</th>
                                            <th>Customer Name</th>
                                            <th>Total Cost</th>
                                            <th>Order Date</th>
                                            <th>Delivery Date</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${orderList}" var="order">

                                            <tr>
                                                <td><a href="${pageContext.request.contextPath}/order/sale/${order.orderId}">#${order.orderNo}</a></td>
                                                <td>${order.clientInfo.name}</td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.grandTotal}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy" value="${order.orderDate}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy" value="${order.deliveryDate}"/></td>
                                                <td>
                                                    <c:if test="${order.saleTrack eq 'PENDDING'}">
                                                        <span class="label label-primary">Pending</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'ACCEPTED'}">
                                                        <span class="label label-default">Accepted</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'PACKED'}">
                                                        <span class="label label-warning">Packed</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'SHIPPED'}">
                                                        <span class="label label-info">Shipped</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'DELIVERED'}">
                                                        <span class="label label-success">Delivered</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'CANCEL'}">
                                                        <span class="label label-danger">Canceled</span>
                                                    </c:if>

                                                </td>
                                            </tr>

                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <c:if test="${fn:length(pagelist) gt 1}">

                        <div class="row">
                            <div class="col-xs-12">
                                <nav class="pull-right">
                                    <ul class="pagination">

                                        <c:if test="${currentpage > 1}">
                                            <li class="page-item">
                                                <a href="${pageContext.request.contextPath}/order/sale/filter?pageNo=${currentpage-1}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&track=${filterDTO.track}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&dlfrom=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlfrom}"/>&dlto=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlto}"/>" class="page-link">Prev</a>
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
                                                                             href="${pageContext.request.contextPath}/order/sale/filter?pageNo=${pagelist}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&track=${filterDTO.track}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&dlfrom=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlfrom}"/>&dlto=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlto}"/>">${pagelist}</a>
                                                    </li>

                                                </c:otherwise>

                                            </c:choose>
                                        </c:forEach>

                                        <c:if test="${currentpage + 1 <= lastpage}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="${pageContext.request.contextPath}/order/sale/filter?pageNo=${currentpage+1}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&track=${filterDTO.track}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&dlfrom=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlfrom}"/>&dlto=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlto}"/>">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
                        </div>

                    </c:if>

                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="/pages/parts/footer.jsp" %>


<script>
    $(document).ready(function () {

        $(".choose1").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/client/customer/search',
                dataType: 'json',
                headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term, // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    var arr = []
                    $.each(data.detail, function (index, value) {

                        if (value.companyName === null || "" === value.companyName) {

                            arr.push({
                                id: value.clientId,
                                text: value.name + ' - ' + value.mobileNumber
                            })
                        } else {
                            arr.push({
                                id: value.clientId,
                                text: value.companyName + ' - ' + value.mobileNumber
                            })
                        }
                    })


                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            placeholder: "Search Customer by Name or Mobile No"
        });
    });
</script>

